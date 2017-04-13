package com.digirati.elucidate.service.query;

import java.util.List;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotationPage;
import com.digirati.elucidate.model.ServiceResponse;

public abstract interface AbstractAnnotationPageService<A extends AbstractAnnotation, P extends AbstractAnnotationPage> {

    public ServiceResponse<P> buildAnnotationPage(List<A> annotations, String collectionId, int page, boolean embeddedDescriptions);
}
