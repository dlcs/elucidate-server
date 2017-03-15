package com.digirati.elucidate.service;

import java.util.List;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotationCollection;
import com.digirati.elucidate.model.ServiceResponse;

public abstract interface AbstractAnnotationService<A extends AbstractAnnotation, C extends AbstractAnnotationCollection> {

    public ServiceResponse<A> getAnnotation(String collectionId, String annotationId);

    public ServiceResponse<List<A>> getAnnotations(String collectionId);

    public ServiceResponse<A> createAnnotation(String collectionId, String annotationId, A annotation);

    public ServiceResponse<A> updateAnnotation(String collectionId, String annotationId, A annotation, String cacheKey);

    public ServiceResponse<Void> deleteAnnotation(String collectionId, String annotationId, String cacheKey);

    public ServiceResponse<List<A>> searchAnnotations(String targetIri);
}
