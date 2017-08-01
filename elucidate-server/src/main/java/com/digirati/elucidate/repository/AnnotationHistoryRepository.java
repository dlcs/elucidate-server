package com.digirati.elucidate.repository;

import com.digirati.elucidate.model.annotation.history.W3CAnnotationHistory;

import java.util.List;

public interface AnnotationHistoryRepository {

    W3CAnnotationHistory createAnnotationHistory(Integer annotationPK, String annotationJsonStr);

    List<W3CAnnotationHistory> deleteAnnotationHistory(int annotationPK);

    W3CAnnotationHistory getAnnotationHistory(String collectionId, String annotationId, int version);

    W3CAnnotationHistory getLatestAnnotationHistory(String collectionId, String annotationId);

    W3CAnnotationHistory getPenultimateAnnotationHistory(String collectionId, String annotationId);

    W3CAnnotationHistory getNextAnnotationHistory(String collectionId, String annotationId, int version);

    W3CAnnotationHistory getPreviousAnnotationHistory(String collectionId, String annotationId, int version);
}
