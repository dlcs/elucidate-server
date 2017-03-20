package com.digirati.elucidate.service.search;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotationPage;
import com.digirati.elucidate.model.ServiceResponse;

public interface AbstractAnnotationPageSearchService<P extends AbstractAnnotationPage> {

    public ServiceResponse<P> searchAnnotationPage(String targetIri, boolean strict, String box, int page, boolean embeddedDescriptions);
}
