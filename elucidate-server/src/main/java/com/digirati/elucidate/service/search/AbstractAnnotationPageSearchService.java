package com.digirati.elucidate.service.search;

import java.util.List;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotationPage;
import com.digirati.elucidate.model.ServiceResponse;

public interface AbstractAnnotationPageSearchService<P extends AbstractAnnotationPage> {

    public ServiceResponse<P> searchAnnotationPageByBody(List<String> fields, String value, boolean strict, int page, boolean embeddedDescriptions);

    public ServiceResponse<P> searchAnnotationPageByTarget(List<String> fields, String value, boolean strict, String xywh, String t, int page, boolean embeddedDescriptions);
}
