package com.digirati.elucidate.service.query;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotationCollection;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.enumeration.ClientPreference;

import java.util.List;

public interface AbstractAnnotationCollectionService<A extends AbstractAnnotation, C extends AbstractAnnotationCollection> {

    ServiceResponse<C> getAnnotationCollection(String collectionId, List<A> annotations, ClientPreference clientPref);

    ServiceResponse<C> createAnnotationCollection(String collectionId, C annotationCollection);
}
