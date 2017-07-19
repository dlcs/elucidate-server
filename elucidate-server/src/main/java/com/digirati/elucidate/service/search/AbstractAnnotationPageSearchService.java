package com.digirati.elucidate.service.search;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotationPage;
import com.digirati.elucidate.model.ServiceResponse;

import java.util.List;

public interface AbstractAnnotationPageSearchService<A extends AbstractAnnotation, P extends AbstractAnnotationPage> {

    ServiceResponse<P> buildAnnotationPageByBody(List<A> annotations, List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, String generatorIri, int page, boolean embeddedDescriptions);

    ServiceResponse<P> buildAnnotationPageByTarget(List<A> annotations, List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, String generatorIri, int page, boolean embeddedDescriptions);

    ServiceResponse<P> buildAnnotationPageByCreator(List<A> annotations, List<String> levels, String type, String value, boolean strict, int page, boolean embeddedDescriptions);

    ServiceResponse<P> buildAnnotationPageByGenerator(List<A> annotations, List<String> levels, String type, String value, boolean strict, int page, boolean embeddedDescriptions);
}
