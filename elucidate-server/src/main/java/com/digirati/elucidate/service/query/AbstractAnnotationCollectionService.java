package com.digirati.elucidate.service.query;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotationCollection;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.enumeration.ClientPreference;

public abstract interface AbstractAnnotationCollectionService<C extends AbstractAnnotationCollection> {

    public ServiceResponse<C> getAnnotationCollection(String collectionId, ClientPreference clientPref);

    public ServiceResponse<C> createAnnotationCollection(String collectionId, C annotationCollection);
}
