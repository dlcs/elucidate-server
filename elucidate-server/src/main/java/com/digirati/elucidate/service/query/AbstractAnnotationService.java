package com.digirati.elucidate.service.query;

import java.util.List;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;
import com.digirati.elucidate.model.ServiceResponse;

public interface AbstractAnnotationService<A extends AbstractAnnotation> {

    ServiceResponse<A> getAnnotation(String collectionId, String annotationId);

    ServiceResponse<List<A>> getAnnotations(String collectionId);

    ServiceResponse<A> createAnnotation(String collectionId, String annotationId, A annotation);

    ServiceResponse<A> updateAnnotation(String collectionId, String annotationId, A annotation, String cacheKey);

    ServiceResponse<Void> deleteAnnotation(String collectionId, String annotationId, String cacheKey);
}
