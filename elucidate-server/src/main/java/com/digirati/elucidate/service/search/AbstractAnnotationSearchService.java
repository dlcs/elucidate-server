package com.digirati.elucidate.service.search;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;
import com.digirati.elucidate.model.ServiceResponse;

import java.util.List;

public interface AbstractAnnotationSearchService<A extends AbstractAnnotation> {

    public ServiceResponse<List<A>> searchAnnotationsByBody(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, String generatorIri);

    public ServiceResponse<List<A>> searchAnnotationsByTarget(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, String generatorIri);

    public ServiceResponse<List<A>> searchAnnotationsByCreator(List<String> levels, String type, String value, boolean strict);

    public ServiceResponse<List<A>> searchAnnotationsByGenerator(List<String> levels, String type, String value, boolean strict);
}
