package com.digirati.elucidate.service.search;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotationCollection;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.enumeration.ClientPreference;

import java.util.List;

public interface AbstractAnnotationCollectionSearchService<C extends AbstractAnnotationCollection> {

    public ServiceResponse<C> searchAnnotationCollectionByBody(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, String generatorIri, ClientPreference clientPref);

    public ServiceResponse<C> searchAnnotationCollectionByTarget(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, String generatorIri, ClientPreference clientPref);

    public ServiceResponse<C> searchAnnotationCollectionByCreator(List<String> levels, String type, String value, ClientPreference clientPref);

    public ServiceResponse<C> searchAnnotationCollectionByGenerator(List<String> levels, String type, String value, ClientPreference clientPref);
}
