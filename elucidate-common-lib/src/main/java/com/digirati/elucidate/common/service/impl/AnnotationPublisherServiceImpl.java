package com.digirati.elucidate.common.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.digirati.elucidate.common.infrastructure.constants.JSONLDConstants;
import com.digirati.elucidate.common.infrastructure.exception.AnnotationPublisherException;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.common.model.enumeration.QueueOperation;
import com.digirati.elucidate.common.repository.AnnotationQueueRepository;
import com.digirati.elucidate.common.service.AnnotationPublisherService;
import com.digirati.elucidate.common.service.IRIBuilderService;
import com.github.jsonldjava.core.JsonLdError;
import com.github.jsonldjava.core.JsonLdOptions;
import com.github.jsonldjava.core.JsonLdProcessor;

@Service(AnnotationPublisherServiceImpl.SERVICE_NAME)
public class AnnotationPublisherServiceImpl implements AnnotationPublisherService {

    private static final Logger LOGGER = Logger.getLogger(AnnotationPublisherServiceImpl.class);

    public static final String SERVICE_NAME = "annotationCreateListener";

    private IRIBuilderService iriBuilderService;
    private AnnotationQueueRepository annotationQueueRepo;
    private String[] defaultW3CContexts;
    private String[] defaultOAContexts;

    @Autowired
    public AnnotationPublisherServiceImpl(IRIBuilderService iriBuilderService, AnnotationQueueRepository annotationQueueRepo, @Value("${annotation.w3c.contexts}") String[] defaultW3CContexts, @Value("${annotation.oa.contexts}") String[] defaultOAContexts) {
        this.iriBuilderService = iriBuilderService;
        this.annotationQueueRepo = annotationQueueRepo;
        this.defaultW3CContexts = Arrays.copyOf(defaultW3CContexts, defaultW3CContexts.length);
        this.defaultOAContexts = Arrays.copyOf(defaultOAContexts, defaultOAContexts.length);
    }

    @Override
    public void create(W3CAnnotation w3cAnnotation) {
        LOGGER.info(String.format("Got notification for [%s] on W3CAnnotation [%s]", QueueOperation.CREATE, w3cAnnotation));
        String iri = iriBuilderService.buildW3CAnnotationIri(w3cAnnotation.getCollectionId(), w3cAnnotation.getAnnotationId());
        W3CAnnotation w3cAnnotationCopy = SerializationUtils.clone(w3cAnnotation);
        Map<String, Object> w3cAnnotationJson = getW3CAnnotationJson(w3cAnnotationCopy);
        Map<String, Object> oaAnnotationJson = getOAAnnotationJson(w3cAnnotationCopy);
        annotationQueueRepo.sendMessage(QueueOperation.CREATE, iri, w3cAnnotationJson, oaAnnotationJson);
    }

    @Override
    public void update(W3CAnnotation w3cAnnotation) {
        LOGGER.info(String.format("Got notification for [%s] on W3CAnnotation [%s]", QueueOperation.UPDATE, w3cAnnotation));
        String iri = iriBuilderService.buildW3CAnnotationIri(w3cAnnotation.getCollectionId(), w3cAnnotation.getAnnotationId());
        W3CAnnotation w3cAnnotationCopy = SerializationUtils.clone(w3cAnnotation);
        Map<String, Object> w3cAnnotationJson = getW3CAnnotationJson(w3cAnnotationCopy);
        Map<String, Object> oaAnnotationJson = getOAAnnotationJson(w3cAnnotationCopy);
        annotationQueueRepo.sendMessage(QueueOperation.UPDATE, iri, w3cAnnotationJson, oaAnnotationJson);
    }

    @Override
    public void delete(W3CAnnotation w3cAnnotation) {
        LOGGER.info(String.format("Got notification for [%s] on W3CAnnotation [%s]", QueueOperation.DELETE, w3cAnnotation));
        String iri = iriBuilderService.buildW3CAnnotationIri(w3cAnnotation.getCollectionId(), w3cAnnotation.getAnnotationId());
        annotationQueueRepo.sendMessage(QueueOperation.DELETE, iri, null, null);
    }

    @SuppressWarnings("serial")
    private Map<String, Object> getW3CAnnotationJson(W3CAnnotation w3cAnnotation) {

        Map<String, Object> jsonMap = w3cAnnotation.getJsonMap();
        jsonMap.put(JSONLDConstants.ATTRIBUTE_ID, iriBuilderService.buildW3CAnnotationIri(w3cAnnotation.getCollectionId(), w3cAnnotation.getAnnotationId()));

        try {
            return JsonLdProcessor.compact(w3cAnnotation.getJsonMap(), new HashMap<String, List<String>>() {
                {
                    put(JSONLDConstants.ATTRIBUTE_CONTEXT, new ArrayList<String>() {
                        {
                            for (String defaultW3CContext : defaultW3CContexts) {
                                add(defaultW3CContext);
                            }
                        }
                    });
                }
            }, new JsonLdOptions());
        } catch (JsonLdError e) {
            throw new AnnotationPublisherException(String.format("An error occurred compacting JSON into contexts [%s] for W3C Annotation [%s]", Arrays.toString(defaultW3CContexts), w3cAnnotation), e);
        }
    }

    @SuppressWarnings("serial")
    private Map<String, Object> getOAAnnotationJson(W3CAnnotation w3cAnnotation) {

        Map<String, Object> jsonMap = w3cAnnotation.getJsonMap();
        jsonMap.put(JSONLDConstants.ATTRIBUTE_ID, iriBuilderService.buildOAAnnotationIri(w3cAnnotation.getCollectionId(), w3cAnnotation.getAnnotationId()));

        try {
            return JsonLdProcessor.compact(w3cAnnotation.getJsonMap(), new HashMap<String, List<String>>() {
                {
                    put(JSONLDConstants.ATTRIBUTE_CONTEXT, new ArrayList<String>() {
                        {
                            for (String defaultOaContext : defaultOAContexts) {
                                add(defaultOaContext);
                            }
                        }
                    });
                }
            }, new JsonLdOptions());
        } catch (JsonLdError e) {
            throw new AnnotationPublisherException(String.format("An error occurred compacting JSON into contexts [%s] for W3C Annotation [%s]", Arrays.toString(defaultOAContexts), w3cAnnotation), e);
        }
    }
}
