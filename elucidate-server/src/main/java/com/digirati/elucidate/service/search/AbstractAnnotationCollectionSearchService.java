package com.digirati.elucidate.service.search;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotationCollection;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.enumeration.ClientPreference;

public interface AbstractAnnotationCollectionSearchService<C extends AbstractAnnotationCollection> {

    public ServiceResponse<C> searchAnnotationCollection(String targetIri, boolean strict, String box, ClientPreference clientPref);
}
