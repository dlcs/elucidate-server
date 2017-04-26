package com.digirati.elucidate.service.search;

import java.util.List;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotationPage;
import com.digirati.elucidate.model.ServiceResponse;

public abstract interface AbstractAnnotationPageSearchService<A extends AbstractAnnotation, P extends AbstractAnnotationPage> {

    public ServiceResponse<P> buildAnnotationPageByBody(List<A> annotations, List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, int page, boolean embeddedDescriptions);

    public ServiceResponse<P> buildAnnotationPageByTarget(List<A> annotations, List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, int page, boolean embeddedDescriptions);

    public ServiceResponse<P> buildAnnotationPageByCreator(List<A> annotations, List<String> levels, String type, String value, int page, boolean embeddedDescriptions);
}
