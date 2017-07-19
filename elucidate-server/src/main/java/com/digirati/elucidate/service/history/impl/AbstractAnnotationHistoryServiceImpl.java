package com.digirati.elucidate.service.history.impl;

import com.digirati.elucidate.common.infrastructure.constants.JSONLDConstants;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.ServiceResponse.Status;
import com.digirati.elucidate.model.annotation.history.AbstractAnnotationHistory;
import com.digirati.elucidate.model.annotation.history.W3CAnnotationHistory;
import com.digirati.elucidate.repository.AnnotationHistoryRepository;
import com.digirati.elucidate.service.history.AbstractAnnotationHistoryService;
import com.github.jsonldjava.utils.JsonUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractAnnotationHistoryServiceImpl<A extends AbstractAnnotation, H extends AbstractAnnotationHistory> implements AbstractAnnotationHistoryService<A, H> {

    protected final Logger LOGGER = Logger.getLogger(AbstractAnnotationHistoryServiceImpl.class);

    private AnnotationHistoryRepository annotationHistoryRepository;

    protected AbstractAnnotationHistoryServiceImpl(AnnotationHistoryRepository annotationHistoryRepository) {
        this.annotationHistoryRepository = annotationHistoryRepository;
    }

    public abstract H convertToAnnotationHistory(W3CAnnotationHistory w3CAnnotationHistory);

    protected abstract String buildAnnotationIri(String collectionId, String annotationId);

    @Override
    public ServiceResponse<H> recordAnnotationHistory(A annotation) {

        int annotationPK = annotation.getPk();
        String jsonStr = convertJsonMapToString(annotation);

        LOGGER.info(String.format("Recording history for Annotation with PK [%s] and JSON [%s]", annotationPK, jsonStr));
        W3CAnnotationHistory w3cAnnotationHistory = annotationHistoryRepository.createAnnotationHistory(annotationPK, jsonStr);

        H annotationHistory = convertToAnnotationHistory(w3cAnnotationHistory);
        annotationHistory.getJsonMap().put(JSONLDConstants.ATTRIBUTE_ID, buildAnnotationIri(annotationHistory.getCollectionId(), annotationHistory.getAnnotationId()));
        return new ServiceResponse<H>(Status.OK, annotationHistory);
    }

    @Override
    public ServiceResponse<List<H>> deleteAnnotationHistory(A annotation) {

        List<W3CAnnotationHistory> w3cAnnotationHistories = annotationHistoryRepository.deleteAnnotationHistory(annotation.getPk());
        if (w3cAnnotationHistories == null || w3cAnnotationHistories.isEmpty()) {
            return new ServiceResponse<List<H>>(Status.NOT_FOUND, null);
        }

        List<H> annotationHistories = new ArrayList<H>();
        for (W3CAnnotationHistory w3cAnnotationHistory : w3cAnnotationHistories) {
            H annotationHistory = convertToAnnotationHistory(w3cAnnotationHistory);
            annotationHistory.getJsonMap().put(JSONLDConstants.ATTRIBUTE_ID, buildAnnotationIri(annotationHistory.getCollectionId(), annotationHistory.getAnnotationId()));
        }
        return new ServiceResponse<List<H>>(Status.OK, annotationHistories);
    }

    @Override
    public ServiceResponse<H> getVersionedAnnotation(String collectionId, String annotationId, int version) {
        W3CAnnotationHistory w3cAnnotationHistory = annotationHistoryRepository.getAnnotationHistory(collectionId, annotationId, version);
        return processAnnotationHistoryResponse(w3cAnnotationHistory);
    }

    @Override
    public ServiceResponse<H> getLatestAnnotationVersion(String collectionId, String annotationId) {
        W3CAnnotationHistory w3cAnnotationHistory = annotationHistoryRepository.getLatestAnnotationHistory(collectionId, annotationId);
        return processAnnotationHistoryResponse(w3cAnnotationHistory);
    }

    @Override
    public ServiceResponse<H> getPenultimateAnnotationVersion(String collectionId, String annotationId) {
        W3CAnnotationHistory w3cAnnotationHistory = annotationHistoryRepository.getPenultimateAnnotationHistory(collectionId, annotationId);
        return processAnnotationHistoryResponse(w3cAnnotationHistory);
    }

    @Override
    public ServiceResponse<H> getNextAnnotationVersion(String collectionId, String annotationId, int version) {
        W3CAnnotationHistory w3cAnnotationHistory = annotationHistoryRepository.getNextAnnotationHistory(collectionId, annotationId, version);
        return processAnnotationHistoryResponse(w3cAnnotationHistory);
    }

    @Override
    public ServiceResponse<H> getPreviousAnnotationVersion(String collectionId, String annotationId, int version) {
        W3CAnnotationHistory w3cAnnotationHistory = annotationHistoryRepository.getPreviousAnnotationHistory(collectionId, annotationId, version);
        return processAnnotationHistoryResponse(w3cAnnotationHistory);
    }

    private ServiceResponse<H> processAnnotationHistoryResponse(W3CAnnotationHistory w3cAnnotationHistory) {

        if (w3cAnnotationHistory == null) {
            return new ServiceResponse<H>(Status.NOT_FOUND, null);
        }

        H annotationHistory = convertToAnnotationHistory(w3cAnnotationHistory);
        annotationHistory.getJsonMap().put(JSONLDConstants.ATTRIBUTE_ID, buildAnnotationIri(annotationHistory.getCollectionId(), annotationHistory.getAnnotationId()));
        return new ServiceResponse<H>(Status.OK, annotationHistory);
    }

    private String convertJsonMapToString(A annotation) {
        Map<String, Object> jsonMap = annotation.getJsonMap();
        try {
            return JsonUtils.toPrettyString(jsonMap);
        } catch (IOException e) {
            throw new RuntimeException(String.format("An error occurred converting JSON Map {%s] to String", jsonMap), e);
        }
    }
}
