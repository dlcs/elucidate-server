package com.digirati.elucidate.service.search;

import java.util.List;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;
import com.digirati.elucidate.model.ServiceResponse;

public interface AbstractAnnotationSearchService<A extends AbstractAnnotation> {

    public ServiceResponse<List<A>> searchAnnotationsByBody(List<String> fields, String value, boolean strict);

    public ServiceResponse<List<A>> searchAnnotationsByTarget(List<String> fields, String value, boolean strict, String xywh, String t);
}
