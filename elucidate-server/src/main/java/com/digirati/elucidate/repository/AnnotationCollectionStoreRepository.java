package com.digirati.elucidate.repository;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationCollection;

public interface AnnotationCollectionStoreRepository {

    public W3CAnnotationCollection getAnnotationCollectionById(String collectionId);

    public W3CAnnotationCollection createAnnotationCollection(String collectionId, String annotationCollectionJson);
}
