package com.digirati.elucidate.service.extractor.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.infrastructure.extractor.agent.AnnotationCreatorExtractor;
import com.digirati.elucidate.infrastructure.extractor.body.AnnotationBodyExtractor;
import com.digirati.elucidate.infrastructure.extractor.selector.AnnotationCSSSelectorExtractor;
import com.digirati.elucidate.infrastructure.extractor.selector.AnnotationDataPositionSelectorExtractor;
import com.digirati.elucidate.infrastructure.extractor.selector.AnnotationFragmentSelectorExtractor;
import com.digirati.elucidate.infrastructure.extractor.selector.AnnotationInlineFragmentSelectorExtractor;
import com.digirati.elucidate.infrastructure.extractor.selector.AnnotationSVGSelectorExtractor;
import com.digirati.elucidate.infrastructure.extractor.selector.AnnotationTextPositionSelectorExtractor;
import com.digirati.elucidate.infrastructure.extractor.selector.AnnotationTextQuoteSelectorExtractor;
import com.digirati.elucidate.infrastructure.extractor.selector.AnnotationXPathSelectorExtractor;
import com.digirati.elucidate.infrastructure.extractor.targets.AnnotationTargetExtractor;
import com.digirati.elucidate.model.annotation.agent.AnnotationAgent;
import com.digirati.elucidate.model.annotation.body.AnnotationBody;
import com.digirati.elucidate.model.annotation.selector.css.AnnotationCSSSelector;
import com.digirati.elucidate.model.annotation.selector.dataposition.AnnotationDataPositionSelector;
import com.digirati.elucidate.model.annotation.selector.fragment.AnnotationFragmentSelector;
import com.digirati.elucidate.model.annotation.selector.svg.AnnotationSVGSelector;
import com.digirati.elucidate.model.annotation.selector.textposition.AnnotationTextPositionSelector;
import com.digirati.elucidate.model.annotation.selector.textquote.AnnotationTextQuoteSelector;
import com.digirati.elucidate.model.annotation.selector.xpath.AnnotationXPathSelector;
import com.digirati.elucidate.model.annotation.targets.AnnotationTarget;
import com.digirati.elucidate.repository.AnnotationAgentStoreRepository;
import com.digirati.elucidate.repository.AnnotationBodyStoreRepository;
import com.digirati.elucidate.repository.AnnotationSelectorStoreRepository;
import com.digirati.elucidate.repository.AnnotationTargetStoreRepository;
import com.digirati.elucidate.service.extractor.AnnotationExtractorService;
import com.github.jsonldjava.utils.JsonUtils;

@Service(AnnotationExtractorServiceImpl.SERVICE_NAME)
public class AnnotationExtractorServiceImpl implements AnnotationExtractorService {

    private static final Logger LOGGER = Logger.getLogger(AnnotationExtractorServiceImpl.class);

    public static final String SERVICE_NAME = "annotationExtractorServiceImpl";

    private AnnotationBodyStoreRepository annotationBodyStoreRepository;
    private AnnotationTargetStoreRepository annotationTargetStoreRepository;
    private AnnotationSelectorStoreRepository annotationSelectorStoreRepository;
    private AnnotationAgentStoreRepository annotationAgentStoreRepository;

    @Autowired
    public AnnotationExtractorServiceImpl(AnnotationBodyStoreRepository annotationBodyStoreRepository, AnnotationTargetStoreRepository annotationTargetStoreRepository, AnnotationSelectorStoreRepository annotationSelectorStoreRepository, AnnotationAgentStoreRepository annotationAgentStoreRepository) {
        this.annotationBodyStoreRepository = annotationBodyStoreRepository;
        this.annotationTargetStoreRepository = annotationTargetStoreRepository;
        this.annotationSelectorStoreRepository = annotationSelectorStoreRepository;
        this.annotationAgentStoreRepository = annotationAgentStoreRepository;
    }

    @Override
    public void processAnnotationCreate(W3CAnnotation w3cAnnotation) {
        try {
            LOGGER.info(String.format("Processing CREATE for W3CAnnotation [%s]", w3cAnnotation));
            createAnnotationBodies(w3cAnnotation);
            createAnnotationTargets(w3cAnnotation);
            createAnnotationCreators(w3cAnnotation);
        } catch (IOException e) {
            LOGGER.error(String.format("An error occurred processing `target`'s for W3CAnnotation [%s]", w3cAnnotation), e);
        }
    }

    @Override
    public void processAnnotationUpdate(W3CAnnotation w3cAnnotation) {
        LOGGER.info(String.format("Processing UPDATE for W3CAnnotation [%s]", w3cAnnotation));
        processAnnotationDelete(w3cAnnotation);
        processAnnotationCreate(w3cAnnotation);
    }

    @Override
    public void processAnnotationDelete(W3CAnnotation w3cAnnotation) {
        LOGGER.info(String.format("Processing DELETE for W3CAnnotation", w3cAnnotation));

        deleteAnnotationCreators(w3cAnnotation);

        List<AnnotationBody> annotationBodies = deleteBodies(w3cAnnotation);
        for (AnnotationBody annotationBody : annotationBodies) {
            int bodyPk = annotationBody.getPk();
            deleteAnnotationSelectors(bodyPk, null);
            deleteAnnotationCreators(null, bodyPk, null);
        }

        List<AnnotationTarget> annotationTargets = deleteTargets(w3cAnnotation);
        for (AnnotationTarget annotationTarget : annotationTargets) {
            int targetPk = annotationTarget.getPk();
            deleteAnnotationSelectors(null, targetPk);
            deleteAnnotationCreators(null, null, targetPk);
        }
    }

    private void createAnnotationBodies(W3CAnnotation w3cAnnotation) throws IOException {
        int annotationPk = w3cAnnotation.getPk();
        LOGGER.info(String.format("Creating Annotation Bodies for W3CAnnotation with PK [%s]", annotationPk));
        Map<String, Object> jsonMap = w3cAnnotation.getJsonMap();

        List<AnnotationBody> annotationBodies = new AnnotationBodyExtractor().extractBodies(jsonMap);
        LOGGER.info(String.format("Got [%s] Annotation Bodies for W3CAnnotation with PK [%s]", annotationBodies.size(), annotationPk));
        for (AnnotationBody annotationBody : annotationBodies) {

            String bodyIri = annotationBody.getBodyIri();
            String sourceIri = annotationBody.getSourceIri();
            String bodyJson = JsonUtils.toString(annotationBody.getJsonMap());
            annotationBody = annotationBodyStoreRepository.createAnnotationBody(annotationPk, bodyIri, sourceIri, bodyJson);

            int bodyPk = annotationBody.getPk();
            Map<String, Object> bodyJsonMap = annotationBody.getJsonMap();
            createAnnotationSelectors(bodyPk, null, bodyJsonMap);
            createAnnotationCreators(null, bodyPk, null, bodyJsonMap);
        }
    }

    private void createAnnotationTargets(W3CAnnotation w3cAnnotation) throws IOException {
        int annotationPk = w3cAnnotation.getPk();
        LOGGER.info(String.format("Creating Annotation Targets for W3CAnnotation with PK [%s]", annotationPk));
        Map<String, Object> jsonMap = w3cAnnotation.getJsonMap();

        List<AnnotationTarget> annotationTargets = new AnnotationTargetExtractor().extractTargets(jsonMap);
        LOGGER.info(String.format("Got [%s] Annotation Targets for W3CAnnotation with PK [%s]", annotationTargets.size(), annotationPk));
        for (AnnotationTarget annotationTarget : annotationTargets) {

            String targetIri = annotationTarget.getTargetIri();
            String sourceIri = annotationTarget.getSourceIri();
            String targetJson = JsonUtils.toString(annotationTarget.getJsonMap());
            annotationTarget = annotationTargetStoreRepository.createAnnotationTarget(annotationPk, targetIri, sourceIri, targetJson);

            int targetPk = annotationTarget.getPk();
            Map<String, Object> targetJsonMap = annotationTarget.getJsonMap();
            createAnnotationSelectors(null, targetPk, targetJsonMap);
            createAnnotationCreators(null, null, targetPk, targetJsonMap);
        }
    }

    private void createAnnotationSelectors(Integer bodyPk, Integer targetPk, Map<String, Object> jsonMap) throws IOException {
        LOGGER.info(String.format("Creating Annotation Selectors for JSON [%s] (Body PK = [%s], Target PK = [%s])", jsonMap, bodyPk, targetPk));
        createAnnotationCssSelectors(bodyPk, targetPk, jsonMap);
        createAnnotationDataPositionSelectors(bodyPk, targetPk, jsonMap);
        createAnnotationInlineFragmentSelector(bodyPk, targetPk, jsonMap);
        createAnnotationFragmentSelectors(bodyPk, targetPk, jsonMap);
        createAnnotationSvgSelectors(bodyPk, targetPk, jsonMap);
        createAnnotationTextPositionSelectors(bodyPk, targetPk, jsonMap);
        createAnnotationTextQuoteSelectors(bodyPk, targetPk, jsonMap);
        createAnnotationXPathSelectors(bodyPk, targetPk, jsonMap);
    }

    private void createAnnotationCssSelectors(Integer bodyPk, Integer targetPk, Map<String, Object> jsonMap) throws IOException {
        List<AnnotationCSSSelector> annotationCssSelectors = new AnnotationCSSSelectorExtractor().extractSelectors(jsonMap);
        LOGGER.info(String.format("Got [%s] Annotation CSS Selectors for (Body PK = [%s], Target PK = [%s])", annotationCssSelectors.size(), bodyPk, targetPk));
        for (AnnotationCSSSelector annotationCssSelector : annotationCssSelectors) {

            String selectorIri = annotationCssSelector.getSelectorIri();
            String value = annotationCssSelector.getValue();
            String selectorJson = JsonUtils.toString(annotationCssSelector.getJsonMap());
            annotationSelectorStoreRepository.createAnnotationCssSelector(bodyPk, targetPk, selectorIri, value, selectorJson);
        }
    }

    private void createAnnotationDataPositionSelectors(Integer bodyPk, Integer targetPk, Map<String, Object> jsonMap) throws IOException {
        List<AnnotationDataPositionSelector> annotationDataPositionSelectors = new AnnotationDataPositionSelectorExtractor().extractSelectors(jsonMap);
        LOGGER.info(String.format("Got [%s] Annotation Data Position Selectors for (Body PK = [%s], Target PK = [%s])", annotationDataPositionSelectors.size(), bodyPk, targetPk));
        for (AnnotationDataPositionSelector annotationDataPositionSelector : annotationDataPositionSelectors) {

            String selectorIri = annotationDataPositionSelector.getSelectorIri();
            Integer start = annotationDataPositionSelector.getStart();
            Integer end = annotationDataPositionSelector.getEnd();
            String selectorJson = JsonUtils.toString(annotationDataPositionSelector.getJsonMap());
            annotationSelectorStoreRepository.createAnnotationDataPositionSelector(bodyPk, targetPk, selectorIri, start, end, selectorJson);
        }
    }

    private void createAnnotationInlineFragmentSelector(Integer bodyPk, Integer targetPk, Map<String, Object> jsonMap) throws IOException {
        AnnotationFragmentSelector annotationFragmentSelector = new AnnotationInlineFragmentSelectorExtractor().extractAnnotationInlineFragmentSelector(jsonMap);
        LOGGER.info(String.format("Got inline Annotation Fragment Selector [%s] for (Body PK = [%s], Target PK = [%s])", annotationFragmentSelector, bodyPk, targetPk));
        if (annotationFragmentSelector != null) {
            createAnnotationFragmentSelector(bodyPk, targetPk, annotationFragmentSelector);
        }
    }

    private void createAnnotationFragmentSelectors(Integer bodyPk, Integer targetPk, Map<String, Object> jsonMap) throws IOException {
        List<AnnotationFragmentSelector> annotationFragmentSelectors = new AnnotationFragmentSelectorExtractor().extractSelectors(jsonMap);
        LOGGER.info(String.format("Got [%s] Annotation Fragment Selectors for (Body PK = [%s], Target PK = [%s])", annotationFragmentSelectors.size(), bodyPk, targetPk));
        for (AnnotationFragmentSelector annotationFragmentSelector : annotationFragmentSelectors) {
            createAnnotationFragmentSelector(bodyPk, targetPk, annotationFragmentSelector);
        }
    }

    private void createAnnotationFragmentSelector(Integer bodyPk, Integer targetPk, AnnotationFragmentSelector annotationFragmentSelector) throws IOException {

        String selectorIri = annotationFragmentSelector.getSelectorIri();
        String conformsTo = annotationFragmentSelector.getConformsTo();
        String value = annotationFragmentSelector.getValue();
        Integer x = annotationFragmentSelector.getX();
        Integer y = annotationFragmentSelector.getY();
        Integer w = annotationFragmentSelector.getW();
        Integer h = annotationFragmentSelector.getH();
        Integer start = annotationFragmentSelector.getStart();
        Integer end = annotationFragmentSelector.getEnd();
        String selectorJson = JsonUtils.toString(annotationFragmentSelector.getJsonMap());
        annotationSelectorStoreRepository.createAnnotationFragmentSelector(bodyPk, targetPk, selectorIri, conformsTo, value, x, y, w, h, start, end, selectorJson);
    }

    private void createAnnotationSvgSelectors(Integer bodyPk, Integer targetPk, Map<String, Object> jsonMap) throws IOException {
        List<AnnotationSVGSelector> annotationSvgSelectors = new AnnotationSVGSelectorExtractor().extractSelectors(jsonMap);
        LOGGER.info(String.format("Got [%s] Annotation SVG Selectors for (Body PK = [%s], Target PK = [%s])", annotationSvgSelectors.size(), bodyPk, targetPk));
        for (AnnotationSVGSelector annotationSvgSelector : annotationSvgSelectors) {

            String selectorIri = annotationSvgSelector.getSelectorIri();
            String value = annotationSvgSelector.getValue();
            String selectorJson = JsonUtils.toString(annotationSvgSelector.getJsonMap());
            annotationSelectorStoreRepository.createAnnotationSvgSelector(bodyPk, targetPk, selectorIri, value, selectorJson);
        }
    }

    private void createAnnotationTextPositionSelectors(Integer bodyPk, Integer targetPk, Map<String, Object> jsonMap) throws IOException {
        List<AnnotationTextPositionSelector> annotationTextPositionSelectors = new AnnotationTextPositionSelectorExtractor().extractSelectors(jsonMap);
        LOGGER.info(String.format("Got [%s] Annotation Text Position Selectors for (Body PK = [%s], Target PK = [%s])", annotationTextPositionSelectors.size(), bodyPk, targetPk));
        for (AnnotationTextPositionSelector annotationTextPositionSelector : annotationTextPositionSelectors) {

            String selectorIri = annotationTextPositionSelector.getSelectorIri();
            Integer start = annotationTextPositionSelector.getStart();
            Integer end = annotationTextPositionSelector.getEnd();
            String selectorJson = JsonUtils.toString(annotationTextPositionSelector.getJsonMap());
            annotationSelectorStoreRepository.createAnnotationTextPositionSelector(bodyPk, targetPk, selectorIri, start, end, selectorJson);
        }
    }

    private void createAnnotationTextQuoteSelectors(Integer bodyPk, Integer targetPk, Map<String, Object> jsonMap) throws IOException {
        List<AnnotationTextQuoteSelector> annotationTextQuoteSelectors = new AnnotationTextQuoteSelectorExtractor().extractSelectors(jsonMap);
        LOGGER.info(String.format("Got [%s] Annotation Text Quote Selectors for (Body PK = [%s], Target PK = [%s])", annotationTextQuoteSelectors.size(), bodyPk, targetPk));
        for (AnnotationTextQuoteSelector annotationTextQuoteSelector : annotationTextQuoteSelectors) {

            String selectorIri = annotationTextQuoteSelector.getSelectorIri();
            String exact = annotationTextQuoteSelector.getExact();
            String prefix = annotationTextQuoteSelector.getPrefix();
            String suffix = annotationTextQuoteSelector.getSuffix();
            String selectorJson = JsonUtils.toString(annotationTextQuoteSelector.getJsonMap());
            annotationSelectorStoreRepository.createAnnotationTextQuoteSelector(bodyPk, targetPk, selectorIri, exact, prefix, suffix, selectorJson);
        }
    }

    private void createAnnotationXPathSelectors(Integer bodyPk, Integer targetPk, Map<String, Object> jsonMap) throws IOException {
        List<AnnotationXPathSelector> annotationXPathSelectors = new AnnotationXPathSelectorExtractor().extractSelectors(jsonMap);
        LOGGER.info(String.format("Got [%s] Annotation XPath Selectors for (Body PK = [%s], Target PK = [%s])", annotationXPathSelectors.size(), bodyPk, targetPk));
        for (AnnotationXPathSelector annotationXPathSelector : annotationXPathSelectors) {

            String selectorIri = annotationXPathSelector.getSelectorIri();
            String value = annotationXPathSelector.getValue();
            String selectorJson = JsonUtils.toString(annotationXPathSelector.getJsonMap());
            annotationSelectorStoreRepository.createAnnotationXPathSelector(bodyPk, targetPk, selectorIri, value, selectorJson);
        }
    }

    private void createAnnotationCreators(W3CAnnotation w3cAnnotation) throws IOException {
        int annotationPk = w3cAnnotation.getPk();
        LOGGER.info(String.format("Creating Annotation Creators for W3CAnnotation with PK [%s]", annotationPk));
        Map<String, Object> jsonMap = w3cAnnotation.getJsonMap();
        createAnnotationCreators(annotationPk, null, null, jsonMap);
    }

    private void createAnnotationCreators(Integer annotationPk, Integer bodyPk, Integer targetPk, Map<String, Object> jsonMap) throws IOException {
        List<AnnotationAgent> annotationCreators = new AnnotationCreatorExtractor().extractCreators(jsonMap);
        LOGGER.info(String.format("Got [%s] Annotation Creators for (Annotation PK = [%s], Body PK = [%s], Target PK = [%s])", annotationCreators.size(), annotationPk, bodyPk, targetPk));
        for (AnnotationAgent annotationCreator : annotationCreators) {

            String creatorIri = annotationCreator.getAgentIri();
            String creatorJson = JsonUtils.toString(annotationCreator.getJsonMap());

            String[] types = annotationCreator.getTypes() != null ? annotationCreator.getTypes().stream().toArray(String[]::new) : new String[] {};
            String[] typesJson = new String[types.length];
            if (types.length > 0) {
                for (int i = 0; i < typesJson.length; i++) {
                    typesJson[i] = JsonUtils.toString(annotationCreator.getTypesJsonList());
                }
            }

            String[] names = annotationCreator.getNames() != null ? annotationCreator.getNames().stream().toArray(String[]::new) : new String[] {};
            String[] namesJson = new String[names.length];
            if (names.length > 0) {
                for (int i = 0; i < namesJson.length; i++) {
                    namesJson[i] = JsonUtils.toString(annotationCreator.getNameJsonMaps().get(i));
                }
            }

            String nickname = annotationCreator.getNickname();

            String[] emails = annotationCreator.getEmails() != null ? annotationCreator.getEmails().stream().toArray(String[]::new) : new String[] {};
            String[] emailsJson = new String[emails.length];
            if (emails.length > 0) {
                for (int i = 0; i < emailsJson.length; i++) {
                    emailsJson[i] = JsonUtils.toString(annotationCreator.getEmailJsonMaps().get(i));
                }
            }

            String[] emailSha1s = annotationCreator.getEmailSha1s() != null ? annotationCreator.getEmailSha1s().stream().toArray(String[]::new) : new String[] {};
            String[] emailSha1sJson = new String[emailSha1s.length];
            if (emailSha1s.length > 0) {
                for (int i = 0; i < emailSha1sJson.length; i++) {
                    emailSha1sJson[i] = JsonUtils.toString(annotationCreator.getEmailSha1JsonMaps().get(i));
                }
            }

            String[] homepages = annotationCreator.getHomepages() != null ? annotationCreator.getHomepages().stream().toArray(String[]::new) : new String[] {};
            String[] homepagesJson = new String[homepages.length];
            if (homepages.length > 0) {
                for (int i = 0; i < homepagesJson.length; i++) {
                    homepagesJson[i] = JsonUtils.toString(annotationCreator.getHomepageJsonMaps().get(i));
                }
            }

            annotationAgentStoreRepository.createAnnotationCreator(annotationPk, bodyPk, targetPk, creatorIri, creatorJson, types, typesJson, names, namesJson, nickname, emails, emailsJson, emailSha1s, emailSha1sJson, homepages, homepagesJson);
        }
    }

    private List<AnnotationBody> deleteBodies(W3CAnnotation w3cAnnotation) {
        int annotationPk = w3cAnnotation.getPk();
        return annotationBodyStoreRepository.deletedAnnotationBodies(annotationPk);
    }

    private List<AnnotationTarget> deleteTargets(W3CAnnotation w3cAnnotation) {
        int annotationPk = w3cAnnotation.getPk();
        return annotationTargetStoreRepository.deleteAnnotationTargets(annotationPk);
    }

    private void deleteAnnotationSelectors(Integer bodyPk, Integer targetPk) {
        deleteAnnotationCssSelectors(bodyPk, targetPk);
        deleteAnnotationDataPositionSelectors(bodyPk, targetPk);
        deleteAnnotationFragmentSelectors(bodyPk, targetPk);
        deleteAnnotationSvgSelectors(bodyPk, targetPk);
        deleteAnnotationTextPositionSelectors(bodyPk, targetPk);
        deleteAnnotationTextQuoteSelectors(bodyPk, targetPk);
        deleteAnnotationXPathSelectors(bodyPk, targetPk);
    }

    private void deleteAnnotationCssSelectors(Integer bodyPk, Integer targetPk) {
        annotationSelectorStoreRepository.deleteAnnotationCssSelectors(bodyPk, targetPk);
    }

    private void deleteAnnotationDataPositionSelectors(Integer bodyPk, Integer targetPk) {
        annotationSelectorStoreRepository.deleteAnnotationDataPositionSelectors(bodyPk, targetPk);
    }

    private void deleteAnnotationFragmentSelectors(Integer bodyPk, Integer targetPk) {
        annotationSelectorStoreRepository.deleteAnnotationFragmentSelectors(bodyPk, targetPk);
    }

    private void deleteAnnotationSvgSelectors(Integer bodyPk, Integer targetPk) {
        annotationSelectorStoreRepository.deleteAnnotationSvgSelectors(bodyPk, targetPk);
    }

    private void deleteAnnotationTextPositionSelectors(Integer bodyPk, Integer targetPk) {
        annotationSelectorStoreRepository.deleteAnnotationTextPositionSelectors(bodyPk, targetPk);
    }

    private void deleteAnnotationTextQuoteSelectors(Integer bodyPk, Integer targetPk) {
        annotationSelectorStoreRepository.deleteAnnotationTextQuoteSelectors(bodyPk, targetPk);
    }

    private void deleteAnnotationXPathSelectors(Integer bodyPk, Integer targetPk) {
        annotationSelectorStoreRepository.deleteAnnotationXPathSelectors(bodyPk, targetPk);
    }

    private void deleteAnnotationCreators(W3CAnnotation w3cAnnotation) {
        int annotationPk = w3cAnnotation.getPk();
        deleteAnnotationCreators(annotationPk, null, null);
    }

    private void deleteAnnotationCreators(Integer annotationPk, Integer bodyPk, Integer targetPk) {
        annotationAgentStoreRepository.deleteAnnotationCreators(annotationPk, bodyPk, targetPk);
    }
}
