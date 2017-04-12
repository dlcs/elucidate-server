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
            createAnnotationBodies(w3cAnnotation);
            createAnnotationTargets(w3cAnnotation);
            createAnnotationCreators(w3cAnnotation);
        } catch (IOException e) {
            LOGGER.error(String.format("An error occurred processing `target`'s for W3CAnnotation [%s]", w3cAnnotation), e);
        }
    }

    @Override
    public void processAnnotationUpdate(W3CAnnotation w3cAnnotation) {
        processAnnotationDelete(w3cAnnotation);
        processAnnotationCreate(w3cAnnotation);
    }

    @Override
    public void processAnnotationDelete(W3CAnnotation w3cAnnotation) {

        deleteAnnotationCreators(w3cAnnotation);

        List<AnnotationBody> annotationBodies = deleteBodies(w3cAnnotation);
        for (AnnotationBody annotationBody : annotationBodies) {
            int bodyPK = annotationBody.getId();
            deleteAnnotationSelectors(bodyPK, null);
            deleteAnnotationCreators(null, bodyPK, null);
        }

        List<AnnotationTarget> annotationTargets = deleteTargets(w3cAnnotation);
        for (AnnotationTarget annotationTarget : annotationTargets) {
            int targetPK = annotationTarget.getId();
            deleteAnnotationSelectors(null, targetPK);
            deleteAnnotationCreators(null, null, targetPK);
        }
    }

    private void createAnnotationBodies(W3CAnnotation w3cAnnotation) throws IOException {
        int annotationPK = w3cAnnotation.getId();
        Map<String, Object> jsonMap = w3cAnnotation.getJsonMap();

        List<AnnotationBody> annotationBodies = new AnnotationBodyExtractor().extractBodies(jsonMap);
        for (AnnotationBody annotationBody : annotationBodies) {

            String bodyIri = annotationBody.getBodyIri();
            String sourceIri = annotationBody.getSourceIri();
            String bodyJson = JsonUtils.toPrettyString(annotationBody.getJsonMap());
            annotationBody = annotationBodyStoreRepository.createAnnotationBody(annotationPK, bodyIri, sourceIri, bodyJson);

            int bodyPK = annotationBody.getId();
            Map<String, Object> bodyJsonMap = annotationBody.getJsonMap();
            createAnnotationSelectors(bodyPK, null, bodyJsonMap);
            createAnnotationCreators(null, bodyPK, null, bodyJsonMap);
        }
    }

    private void createAnnotationTargets(W3CAnnotation w3cAnnotation) throws IOException {
        int annotationPK = w3cAnnotation.getId();
        Map<String, Object> jsonMap = w3cAnnotation.getJsonMap();

        List<AnnotationTarget> annotationTargets = new AnnotationTargetExtractor().extractTargets(jsonMap);
        for (AnnotationTarget annotationTarget : annotationTargets) {

            String targetIri = annotationTarget.getTargetIri();
            String sourceIri = annotationTarget.getSourceIri();
            String targetJson = JsonUtils.toPrettyString(annotationTarget.getJsonMap());
            annotationTarget = annotationTargetStoreRepository.createAnnotationTarget(annotationPK, targetIri, sourceIri, targetJson);

            int targetPK = annotationTarget.getId();
            Map<String, Object> targetJsonMap = annotationTarget.getJsonMap();
            createAnnotationSelectors(null, targetPK, targetJsonMap);
            createAnnotationCreators(null, null, targetPK, targetJsonMap);
        }
    }

    private void createAnnotationSelectors(Integer bodyPK, Integer targetPK, Map<String, Object> jsonMap) throws IOException {
        createAnnotationCssSelectors(bodyPK, targetPK, jsonMap);
        createAnnotationDataPositionSelectors(bodyPK, targetPK, jsonMap);
        createAnnotationInlineFragmentSelector(bodyPK, targetPK, jsonMap);
        createAnnotationFragmentSelectors(bodyPK, targetPK, jsonMap);
        createAnnotationSvgSelectors(bodyPK, targetPK, jsonMap);
        createAnnotationTextPositionSelectors(bodyPK, targetPK, jsonMap);
        createAnnotationTextQuoteSelectors(bodyPK, targetPK, jsonMap);
        createAnnotationXPathSelectors(bodyPK, targetPK, jsonMap);
    }

    private void createAnnotationCssSelectors(Integer bodyPK, Integer targetPK, Map<String, Object> jsonMap) throws IOException {
        List<AnnotationCSSSelector> annotationCssSelectors = new AnnotationCSSSelectorExtractor().extractSelectors(jsonMap);
        for (AnnotationCSSSelector annotationCssSelector : annotationCssSelectors) {

            String selectorIri = annotationCssSelector.getSelectorIri();
            String value = annotationCssSelector.getValue();
            String selectorJson = JsonUtils.toPrettyString(annotationCssSelector.getJsonMap());
            annotationSelectorStoreRepository.createAnnotationCssSelector(bodyPK, targetPK, selectorIri, value, selectorJson);
        }
    }

    private void createAnnotationDataPositionSelectors(Integer bodyPK, Integer targetPK, Map<String, Object> jsonMap) throws IOException {
        List<AnnotationDataPositionSelector> annotationDataPositionSelectors = new AnnotationDataPositionSelectorExtractor().extractSelectors(jsonMap);
        for (AnnotationDataPositionSelector annotationDataPositionSelector : annotationDataPositionSelectors) {

            String selectorIri = annotationDataPositionSelector.getSelectorIri();
            Integer start = annotationDataPositionSelector.getStart();
            Integer end = annotationDataPositionSelector.getEnd();
            String selectorJson = JsonUtils.toPrettyString(annotationDataPositionSelector.getJsonMap());
            annotationSelectorStoreRepository.createAnnotationDataPositionSelector(bodyPK, targetPK, selectorIri, start, end, selectorJson);
        }
    }

    private void createAnnotationInlineFragmentSelector(Integer bodyPK, Integer targetPK, Map<String, Object> jsonMap) throws IOException {
        AnnotationFragmentSelector annotationFragmentSelector = new AnnotationInlineFragmentSelectorExtractor().extractAnnotationInlineFragmentSelector(jsonMap);
        if (annotationFragmentSelector != null) {
            createAnnotationFragmentSelector(bodyPK, targetPK, annotationFragmentSelector);
        }
    }

    private void createAnnotationFragmentSelectors(Integer bodyPK, Integer targetPK, Map<String, Object> jsonMap) throws IOException {
        List<AnnotationFragmentSelector> annotationFragmentSelectors = new AnnotationFragmentSelectorExtractor().extractSelectors(jsonMap);
        for (AnnotationFragmentSelector annotationFragmentSelector : annotationFragmentSelectors) {
            createAnnotationFragmentSelector(bodyPK, targetPK, annotationFragmentSelector);
        }
    }

    private void createAnnotationFragmentSelector(Integer bodyPK, Integer targetPK, AnnotationFragmentSelector annotationFragmentSelector) throws IOException {

        String selectorIri = annotationFragmentSelector.getSelectorIri();
        String conformsTo = annotationFragmentSelector.getConformsTo();
        String value = annotationFragmentSelector.getValue();
        Integer x = annotationFragmentSelector.getX();
        Integer y = annotationFragmentSelector.getY();
        Integer w = annotationFragmentSelector.getW();
        Integer h = annotationFragmentSelector.getH();
        Integer start = annotationFragmentSelector.getStart();
        Integer end = annotationFragmentSelector.getEnd();
        String selectorJson = JsonUtils.toPrettyString(annotationFragmentSelector.getJsonMap());
        annotationSelectorStoreRepository.createAnnotationFragmentSelector(bodyPK, targetPK, selectorIri, conformsTo, value, x, y, w, h, start, end, selectorJson);
    }

    private void createAnnotationSvgSelectors(Integer bodyPK, Integer targetPK, Map<String, Object> jsonMap) throws IOException {
        List<AnnotationSVGSelector> annotationSvgSelectors = new AnnotationSVGSelectorExtractor().extractSelectors(jsonMap);
        for (AnnotationSVGSelector annotationSvgSelector : annotationSvgSelectors) {

            String selectorIri = annotationSvgSelector.getSelectorIri();
            String value = annotationSvgSelector.getValue();
            String selectorJson = JsonUtils.toPrettyString(annotationSvgSelector.getJsonMap());
            annotationSelectorStoreRepository.createAnnotationSvgSelector(bodyPK, targetPK, selectorIri, value, selectorJson);
        }
    }

    private void createAnnotationTextPositionSelectors(Integer bodyPK, Integer targetPK, Map<String, Object> jsonMap) throws IOException {
        List<AnnotationTextPositionSelector> annotationTextPositionSelectors = new AnnotationTextPositionSelectorExtractor().extractSelectors(jsonMap);
        for (AnnotationTextPositionSelector annotationTextPositionSelector : annotationTextPositionSelectors) {

            String selectorIri = annotationTextPositionSelector.getSelectorIri();
            Integer start = annotationTextPositionSelector.getStart();
            Integer end = annotationTextPositionSelector.getEnd();
            String selectorJson = JsonUtils.toPrettyString(annotationTextPositionSelector.getJsonMap());
            annotationSelectorStoreRepository.createAnnotationTextPositionSelector(bodyPK, targetPK, selectorIri, start, end, selectorJson);
        }
    }

    private void createAnnotationTextQuoteSelectors(Integer bodyPK, Integer targetPK, Map<String, Object> jsonMap) throws IOException {
        List<AnnotationTextQuoteSelector> annotationTextQuoteSelectors = new AnnotationTextQuoteSelectorExtractor().extractSelectors(jsonMap);
        for (AnnotationTextQuoteSelector annotationTextQuoteSelector : annotationTextQuoteSelectors) {

            String selectorIri = annotationTextQuoteSelector.getSelectorIri();
            String exact = annotationTextQuoteSelector.getExact();
            String prefix = annotationTextQuoteSelector.getPrefix();
            String suffix = annotationTextQuoteSelector.getSuffix();
            String selectorJson = JsonUtils.toPrettyString(annotationTextQuoteSelector.getJsonMap());
            annotationSelectorStoreRepository.createAnnotationTextQuoteSelector(bodyPK, targetPK, selectorIri, exact, prefix, suffix, selectorJson);
        }
    }

    private void createAnnotationXPathSelectors(Integer bodyPK, Integer targetPK, Map<String, Object> jsonMap) throws IOException {
        List<AnnotationXPathSelector> annotationXPathSelectors = new AnnotationXPathSelectorExtractor().extractSelectors(jsonMap);
        for (AnnotationXPathSelector annotationXPathSelector : annotationXPathSelectors) {

            String selectorIri = annotationXPathSelector.getSelectorIri();
            String value = annotationXPathSelector.getValue();
            String selectorJson = JsonUtils.toPrettyString(annotationXPathSelector.getJsonMap());
            annotationSelectorStoreRepository.createAnnotationXPathSelector(bodyPK, targetPK, selectorIri, value, selectorJson);
        }
    }

    private void createAnnotationCreators(W3CAnnotation w3cAnnotation) throws IOException {
        int annotationPK = w3cAnnotation.getId();
        Map<String, Object> jsonMap = w3cAnnotation.getJsonMap();
        createAnnotationCreators(annotationPK, null, null, jsonMap);
    }

    private void createAnnotationCreators(Integer annotationPK, Integer bodyPK, Integer targetPK, Map<String, Object> jsonMap) throws IOException {
        List<AnnotationAgent> annotationCreators = new AnnotationCreatorExtractor().extractCreators(jsonMap);
        for (AnnotationAgent annotationCreator : annotationCreators) {

            String creatorIri = annotationCreator.getAgentIri();
            String creatorJson = JsonUtils.toPrettyString(annotationCreator.getJsonMap());

            String[] types = annotationCreator.getTypes() != null ? annotationCreator.getTypes().stream().toArray(String[]::new) : new String[] {};
            String[] typesJson = new String[types.length];
            if (types.length > 0) {
                for (int i = 0; i < typesJson.length; i++) {
                    typesJson[i] = JsonUtils.toPrettyString(annotationCreator.getTypesJsonList());
                }
            }

            String[] names = annotationCreator.getNames() != null ? annotationCreator.getNames().stream().toArray(String[]::new) : new String[] {};
            String[] namesJson = new String[names.length];
            if (names.length > 0) {
                for (int i = 0; i < namesJson.length; i++) {
                    namesJson[i] = JsonUtils.toPrettyString(annotationCreator.getNameJsonMaps().get(i));
                }
            }

            String nickname = annotationCreator.getNickname();

            String[] emails = annotationCreator.getEmails() != null ? annotationCreator.getEmails().stream().toArray(String[]::new) : new String[] {};
            String[] emailsJson = new String[emails.length];
            if (emails.length > 0) {
                for (int i = 0; i < emailsJson.length; i++) {
                    emailsJson[i] = JsonUtils.toPrettyString(annotationCreator.getEmailJsonMaps().get(i));
                }
            }

            String[] emailSha1s = annotationCreator.getEmailSha1s() != null ? annotationCreator.getEmailSha1s().stream().toArray(String[]::new) : new String[] {};
            String[] emailSha1sJson = new String[emailSha1s.length];
            if (emailSha1s.length > 0) {
                for (int i = 0; i < emailSha1sJson.length; i++) {
                    emailSha1sJson[i] = JsonUtils.toPrettyString(annotationCreator.getEmailSha1JsonMaps().get(i));
                }
            }

            String[] homepages = annotationCreator.getHomepages() != null ? annotationCreator.getHomepages().stream().toArray(String[]::new) : new String[] {};
            String[] homepagesJson = new String[homepages.length];
            if (homepages.length > 0) {
                for (int i = 0; i < homepagesJson.length; i++) {
                    homepagesJson[i] = JsonUtils.toPrettyString(annotationCreator.getHomepageJsonMaps().get(i));
                }
            }

            annotationAgentStoreRepository.createAnnotationCreator(annotationPK, bodyPK, targetPK, creatorIri, creatorJson, types, typesJson, names, namesJson, nickname, emails, emailsJson, emailSha1s, emailSha1sJson, homepages, homepagesJson);
        }
    }

    private List<AnnotationBody> deleteBodies(W3CAnnotation w3cAnnotation) {
        int annotationPK = w3cAnnotation.getId();
        return annotationBodyStoreRepository.deletedAnnotationBodies(annotationPK);
    }

    private List<AnnotationTarget> deleteTargets(W3CAnnotation w3cAnnotation) {
        int annotationPK = w3cAnnotation.getId();
        return annotationTargetStoreRepository.deleteAnnotationTargets(annotationPK);
    }

    private void deleteAnnotationSelectors(Integer bodyPK, Integer targetPK) {
        deleteAnnotationCssSelectors(bodyPK, targetPK);
        deleteAnnotationDataPositionSelectors(bodyPK, targetPK);
        deleteAnnotationFragmentSelectors(bodyPK, targetPK);
        deleteAnnotationSvgSelectors(bodyPK, targetPK);
        deleteAnnotationTextPositionSelectors(bodyPK, targetPK);
        deleteAnnotationTextQuoteSelectors(bodyPK, targetPK);
        deleteAnnotationXPathSelectors(bodyPK, targetPK);
    }

    private void deleteAnnotationCssSelectors(Integer bodyPK, Integer targetPK) {
        annotationSelectorStoreRepository.deleteAnnotationCssSelectors(bodyPK, targetPK);
    }

    private void deleteAnnotationDataPositionSelectors(Integer bodyPK, Integer targetPK) {
        annotationSelectorStoreRepository.deleteAnnotationDataPositionSelectors(bodyPK, targetPK);
    }

    private void deleteAnnotationFragmentSelectors(Integer bodyPK, Integer targetPK) {
        annotationSelectorStoreRepository.deleteAnnotationFragmentSelectors(bodyPK, targetPK);
    }

    private void deleteAnnotationSvgSelectors(Integer bodyPK, Integer targetPK) {
        annotationSelectorStoreRepository.deleteAnnotationSvgSelectors(bodyPK, targetPK);
    }

    private void deleteAnnotationTextPositionSelectors(Integer bodyPK, Integer targetPK) {
        annotationSelectorStoreRepository.deleteAnnotationTextPositionSelectors(bodyPK, targetPK);
    }

    private void deleteAnnotationTextQuoteSelectors(Integer bodyPK, Integer targetPK) {
        annotationSelectorStoreRepository.deleteAnnotationTextQuoteSelectors(bodyPK, targetPK);
    }

    private void deleteAnnotationXPathSelectors(Integer bodyPK, Integer targetPK) {
        annotationSelectorStoreRepository.deleteAnnotationXPathSelectors(bodyPK, targetPK);
    }

    private void deleteAnnotationCreators(W3CAnnotation w3cAnnotation) {
        int annotationPK = w3cAnnotation.getId();
        deleteAnnotationCreators(annotationPK, null, null);
    }

    private void deleteAnnotationCreators(Integer annotationPK, Integer bodyPK, Integer targetPK) {
        annotationAgentStoreRepository.deleteAnnotationCreators(annotationPK, bodyPK, targetPK);
    }
}
