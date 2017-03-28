package com.digirati.elucidate.infrastructure.builder.function;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotationPage;
import com.digirati.elucidate.model.ServiceResponse;

@FunctionalInterface
public interface FirstAnnotationPageBuilder<P extends AbstractAnnotationPage> {

    public ServiceResponse<P> buildFirstAnnotationPage();
}
