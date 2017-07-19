package com.digirati.elucidate.service.query;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotationPage;
import com.digirati.elucidate.model.ServiceResponse;

import java.util.List;

public interface AbstractAnnotationPageService<A extends AbstractAnnotation, P extends AbstractAnnotationPage> {

    ServiceResponse<P> buildAnnotationPage(List<A> annotations, String collectionId, int page, boolean embeddedDescriptions);
}
