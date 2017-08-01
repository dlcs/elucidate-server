package com.digirati.elucidate.service.search;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;
import com.digirati.elucidate.model.ServiceResponse;

import java.util.Date;
import java.util.List;

public interface AbstractAnnotationSearchService<A extends AbstractAnnotation> {

    ServiceResponse<List<A>> searchAnnotationsByBody(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, String generatorIri);

    ServiceResponse<List<A>> searchAnnotationsByTarget(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, String generatorIri);

    ServiceResponse<List<A>> searchAnnotationsByCreator(List<String> levels, String type, String value, boolean strict);

    ServiceResponse<List<A>> searchAnnotationsByGenerator(List<String> levels, String type, String value, boolean strict);

    ServiceResponse<List<A>> searchAnnotationsByTemporal(List<String> levels, List<String> types, Date since);
}
