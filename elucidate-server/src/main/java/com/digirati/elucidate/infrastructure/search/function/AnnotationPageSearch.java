package com.digirati.elucidate.infrastructure.search.function;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotationPage;
import com.digirati.elucidate.model.ServiceResponse;

@FunctionalInterface
public interface AnnotationPageSearch<P extends AbstractAnnotationPage> {

    public ServiceResponse<P> searchAnnotationPage(boolean embeddedDescriptions);
}
