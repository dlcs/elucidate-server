package com.digirati.elucidate.repository;

import java.util.List;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;

public interface AnnotationStoreRepository {

    public W3CAnnotation getAnnotationByCollectionIdAndAnnotationId(String collectionId, String annotationId);

    public List<W3CAnnotation> getAnnotationsByCollectionId(String collectionId);

    public W3CAnnotation createAnnotation(String collectionId, String annotationId, String annotationJson);

    public W3CAnnotation updateAnnotation(String collectionId, String annotationId, String annotationJson);

    public W3CAnnotation deleteAnnotation(String collectionId, String annotationId);

    public int countDeletedAnnotations(String collectionId, String annotationId);
}
