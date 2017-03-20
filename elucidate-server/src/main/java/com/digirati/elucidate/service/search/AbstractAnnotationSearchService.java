package com.digirati.elucidate.service.search;

import java.util.List;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;
import com.digirati.elucidate.model.ServiceResponse;

public interface AbstractAnnotationSearchService<A extends AbstractAnnotation> {

    public ServiceResponse<List<A>> searchAnnotations(String targetIri, boolean strict, String box);
}
