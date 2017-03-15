package com.digirati.elucidate.service;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotationCollection;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotationPage;
import com.digirati.elucidate.model.ServiceResponse;

public abstract interface AbstractAnnotationPageService<A extends AbstractAnnotation, P extends AbstractAnnotationPage, C extends AbstractAnnotationCollection> {

    public ServiceResponse<P> getAnnotationPage(String collectionId, boolean embeddedDescriptions, int page);

    public ServiceResponse<P> searchAnnotationPage(String targetIri, boolean embeddedDescriptions, int page);
}
