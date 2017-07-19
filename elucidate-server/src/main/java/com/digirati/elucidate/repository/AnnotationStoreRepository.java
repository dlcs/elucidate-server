package com.digirati.elucidate.repository;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;

import java.util.List;

public interface AnnotationStoreRepository {

    W3CAnnotation getAnnotationByCollectionIdAndAnnotationId(String collectionId, String annotationId);

    List<W3CAnnotation> getAnnotationsByCollectionId(String collectionId);

    W3CAnnotation createAnnotation(String collectionId, String annotationId, String annotationJson);

    W3CAnnotation updateAnnotation(String collectionId, String annotationId, String annotationJson);

    W3CAnnotation deleteAnnotation(String collectionId, String annotationId);

    int countDeletedAnnotations(String collectionId, String annotationId);
}
