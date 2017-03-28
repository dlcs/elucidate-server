package com.digirati.elucidate.service.search;

import java.util.List;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotationCollection;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.enumeration.ClientPreference;

public interface AbstractAnnotationCollectionSearchService<C extends AbstractAnnotationCollection> {

    public ServiceResponse<C> searchAnnotationCollectionByBody(List<String> fields, String value, boolean strict, ClientPreference clientPref);

    public ServiceResponse<C> searchAnnotationCollectionByTarget(List<String> fields, String value, boolean strict, String xywh, String t, ClientPreference clientPref);
}
