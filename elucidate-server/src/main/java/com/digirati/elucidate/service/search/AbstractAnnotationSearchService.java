package com.digirati.elucidate.service.search;

import java.util.List;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;
import com.digirati.elucidate.model.ServiceResponse;

public interface AbstractAnnotationSearchService<A extends AbstractAnnotation> {

    ServiceResponse<List<A>> searchAnnotationsByBody(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri);

    ServiceResponse<List<A>> searchAnnotationsByTarget(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri);

    ServiceResponse<List<A>> searchAnnotationsByCreator(List<String> levels, String type, String value);
}
