package com.digirati.elucidate.service.query.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.digirati.elucidate.common.infrastructure.constants.JSONLDConstants;
import com.digirati.elucidate.common.infrastructure.constants.OAConstants;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.infrastructure.generator.IDGenerator;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.ServiceResponse.Status;
import com.digirati.elucidate.repository.AnnotationStoreRepository;
import com.digirati.elucidate.service.query.AbstractAnnotationService;
import com.github.jsonldjava.utils.JsonUtils;

public abstract class AbstractAnnotationServiceImpl<A extends AbstractAnnotation> implements AbstractAnnotationService<A> {

    protected final Logger LOGGER = Logger.getLogger(getClass());

    private AnnotationStoreRepository annotationStoreRepository;
    private IDGenerator idGenerator;

    public AbstractAnnotationServiceImpl(AnnotationStoreRepository annotationStoreRepository, IDGenerator idGenerator) {
        this.annotationStoreRepository = annotationStoreRepository;
        this.idGenerator = idGenerator;
    }

    protected abstract A convertToAnnotation(W3CAnnotation w3cAnnotation);

    protected abstract W3CAnnotation convertFromAnnotation(A annotation);

    protected abstract String buildAnnotationIri(String collectionId, String annotationId);

    @Override
    @Transactional(readOnly = true)
    public ServiceResponse<A> getAnnotation(String collectionId, String annotationId) {

        W3CAnnotation w3cAnnotation = annotationStoreRepository.getAnnotationByCollectionIdAndAnnotationId(collectionId, annotationId);

        if (w3cAnnotation == null) {

            int deletedAnnotations = annotationStoreRepository.countDeletedAnnotations(collectionId, annotationId);

            if (deletedAnnotations > 0) {
                return new ServiceResponse<A>(Status.DELETED, null);
            }

            return new ServiceResponse<A>(Status.NOT_FOUND, null);
        }

        A annotation = convertToAnnotation(w3cAnnotation);
        annotation.getJsonMap().put(JSONLDConstants.ATTRIBUTE_ID, buildAnnotationIri(collectionId, annotationId));
        return new ServiceResponse<A>(Status.OK, annotation);
    }

    @Override
    @Transactional(readOnly = true)
    public ServiceResponse<List<A>> getAnnotations(String collectionId) {

        List<W3CAnnotation> w3cAnnotations = annotationStoreRepository.getAnnotationsByCollectionId(collectionId);

        List<A> annotations = new ArrayList<A>();
        for (W3CAnnotation w3cAnnotation : w3cAnnotations) {
            A annotation = convertToAnnotation(w3cAnnotation);
            annotation.getJsonMap().put(JSONLDConstants.ATTRIBUTE_ID, buildAnnotationIri(collectionId, annotation.getAnnotationId()));
            annotations.add(annotation);
        }

        return new ServiceResponse<List<A>>(Status.OK, annotations);
    }

    @Override
    @SuppressWarnings("serial")
    @Transactional(readOnly = false)
    public ServiceResponse<A> createAnnotation(String collectionId, String annotationId, A annotation) {

        if (StringUtils.isBlank(annotationId)) {
            annotationId = idGenerator.generateId();
        }

        if (!validateAnnotationId(annotationId)) {
            return new ServiceResponse<A>(Status.NON_CONFORMANT, null);
        }

        ServiceResponse<A> existingAnnotationServiceResponse = getAnnotation(collectionId, annotationId);
        Status existingAnnotationStatus = existingAnnotationServiceResponse.getStatus();
        if (existingAnnotationStatus.equals(Status.OK)) {
            return new ServiceResponse<A>(Status.ALREADY_EXISTS, null);
        }

        W3CAnnotation w3cAnnotation = convertFromAnnotation(annotation);

        Map<String, Object> annotationMap = w3cAnnotation.getJsonMap();
        if (StringUtils.isNotBlank((String) annotationMap.get(JSONLDConstants.ATTRIBUTE_ID))) {
            annotationMap.put(OAConstants.URI_VIA, new ArrayList<Map<String, Object>>() {
                {
                    add(new HashMap<String, Object>() {
                        {
                            put(JSONLDConstants.ATTRIBUTE_ID, annotationMap.get(JSONLDConstants.ATTRIBUTE_ID));
                        }
                    });
                }
            });
        }

        String annotationJson;
        try {
            annotationJson = JsonUtils.toPrettyString(annotationMap);
        } catch (IOException e) {
            LOGGER.debug(String.format("Detected invalid JSON in Annotation Map [%s]", annotationMap), e);
            return new ServiceResponse<A>(Status.NON_CONFORMANT, null);
        }
        w3cAnnotation = annotationStoreRepository.createAnnotation(collectionId, annotationId, annotationJson);

        annotation = convertToAnnotation(w3cAnnotation);
        annotation.getJsonMap().put(JSONLDConstants.ATTRIBUTE_ID, buildAnnotationIri(collectionId, annotationId));
        return new ServiceResponse<A>(Status.OK, annotation);
    }

    @Override
    @Transactional(readOnly = false)
    public ServiceResponse<A> updateAnnotation(String collectionId, String annotationId, A annotation, String cacheKey) {

        ServiceResponse<A> existingAnnotationServiceResponse = getAnnotation(collectionId, annotationId);
        Status existingAnnotationStatus = existingAnnotationServiceResponse.getStatus();

        if (existingAnnotationStatus.equals(Status.NOT_FOUND)) {
            return new ServiceResponse<A>(Status.NOT_FOUND, null);
        }

        if (existingAnnotationStatus.equals(Status.DELETED)) {
            return new ServiceResponse<A>(Status.DELETED, null);
        }

        A existingAnnotation = existingAnnotationServiceResponse.getObj();

        String existingCacheKey = existingAnnotation.getCacheKey();
        if (!StringUtils.equals(cacheKey, existingCacheKey)) {
            return new ServiceResponse<A>(Status.CACHE_KEY_MISS, null);
        }

        Map<String, Object> annotationMap = annotation.getJsonMap();
        Map<String, Object> existingAnnotationMap = existingAnnotation.getJsonMap();

        if (!StringUtils.equals((String) annotationMap.get("via"), (String) existingAnnotationMap.get("via"))) {
            return new ServiceResponse<A>(Status.ILLEGAL_MODIFICATION, null);
        }

        if (!StringUtils.equals((String) annotationMap.get("canonical"), (String) existingAnnotationMap.get("canonical"))) {
            return new ServiceResponse<A>(Status.ILLEGAL_MODIFICATION, null);
        }

        W3CAnnotation w3cAnnotation = convertFromAnnotation(annotation);
        annotationMap = w3cAnnotation.getJsonMap();

        String annotationJson;
        try {
            annotationJson = JsonUtils.toPrettyString(annotationMap);
        } catch (IOException e) {
            LOGGER.debug(String.format("Detected invalid JSON in Annotation Map [%s]", annotationMap), e);
            return new ServiceResponse<A>(Status.NON_CONFORMANT, null);
        }
        w3cAnnotation = annotationStoreRepository.updateAnnotation(collectionId, annotationId, annotationJson);

        annotation = convertToAnnotation(w3cAnnotation);
        annotation.getJsonMap().put(JSONLDConstants.ATTRIBUTE_ID, buildAnnotationIri(collectionId, annotationId));
        return new ServiceResponse<A>(Status.OK, annotation);
    }

    @Override
    @Transactional(readOnly = false)
    public ServiceResponse<Void> deleteAnnotation(String collectionId, String annotationId, String cacheKey) {

        ServiceResponse<A> existingAnnotationServiceResponse = getAnnotation(collectionId, annotationId);
        Status existingAnnotationStatus = existingAnnotationServiceResponse.getStatus();

        if (existingAnnotationStatus.equals(Status.NOT_FOUND)) {
            return new ServiceResponse<Void>(Status.NOT_FOUND, null);
        }

        if (existingAnnotationStatus.equals(Status.DELETED)) {
            return new ServiceResponse<Void>(Status.DELETED, null);
        }

        A existingAnnotation = existingAnnotationServiceResponse.getObj();

        String existingCacheKey = existingAnnotation.getCacheKey();
        if (!StringUtils.equals(cacheKey, existingCacheKey)) {
            return new ServiceResponse<Void>(Status.CACHE_KEY_MISS, null);
        }

        annotationStoreRepository.deleteAnnotation(collectionId, annotationId);
        return new ServiceResponse<Void>(Status.OK, null);
    }

    private boolean validateAnnotationId(String annotationId) {
        return StringUtils.isNotBlank(annotationId) && annotationId.length() <= 100;
    }
}
