package com.digirati.elucidate.service.query;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotationPage;
import com.digirati.elucidate.model.ServiceResponse;

public abstract interface AbstractAnnotationPageService<P extends AbstractAnnotationPage> {

    public ServiceResponse<P> getAnnotationPage(String collectionId, int page, boolean embeddedDescriptions);
}
