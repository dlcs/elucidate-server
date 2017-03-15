package com.digirati.elucidate.service;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotationCollection;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.enumeration.ClientPreference;

public abstract interface AbstractAnnotationCollectionService<A extends AbstractAnnotation, C extends AbstractAnnotationCollection> {

    public ServiceResponse<C> getAnnotationCollection(String collectionId, ClientPreference clientPref);

    public ServiceResponse<C> createAnnotationCollection(String collectionId, C annotationCollection);

    public ServiceResponse<C> searchAnnotationCollection(String targetId, ClientPreference clientPref);
}
