package com.digirati.elucidate.service.query.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotationPage;
import com.digirati.elucidate.infrastructure.builder.AnnotationPageBuilder;
import com.digirati.elucidate.infrastructure.builder.function.AnnotationCollectionIRIBuilder;
import com.digirati.elucidate.infrastructure.builder.function.AnnotationPageConverter;
import com.digirati.elucidate.infrastructure.builder.function.AnnotationPageIRIBuilder;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.ServiceResponse.Status;
import com.digirati.elucidate.service.query.AbstractAnnotationPageService;
import com.digirati.elucidate.service.query.AbstractAnnotationService;

public abstract class AbstractAnnotationPageServiceImpl<A extends AbstractAnnotation, P extends AbstractAnnotationPage> implements AbstractAnnotationPageService<P> {

    protected final Logger LOGGER = Logger.getLogger(getClass());

    private AbstractAnnotationService<A> annotationService;
    private int pageSize;

    public AbstractAnnotationPageServiceImpl(AbstractAnnotationService<A> annotationService, int pageSize) {
        this.annotationService = annotationService;
        this.pageSize = pageSize;
    }

    protected abstract P convertToAnnotationPage(Map<String, Object> jsonMap);

    protected abstract String buildCollectionIri(String collectionId);

    protected abstract String buildPageIri(String collectionId, int page, boolean embeddedDescriptions);

    @Override
    @Transactional(readOnly = true)
    public ServiceResponse<P> getAnnotationPage(String collectionId, int page, boolean embeddedDescriptions) {

        ServiceResponse<List<A>> serviceResponse = annotationService.getAnnotations(collectionId);
        Status status = serviceResponse.getStatus();

        if (!status.equals(Status.OK)) {
            return new ServiceResponse<P>(status, null);
        }

        List<A> annotations = serviceResponse.getObj();

        AnnotationPageConverter<P> annotationPageConverter = (Map<String, Object> _jsonMap) -> {
            return convertToAnnotationPage(_jsonMap);
        };

        AnnotationCollectionIRIBuilder annotationCollectionIRIBuilder = () -> {
            return buildCollectionIri(collectionId);
        };

        AnnotationPageIRIBuilder annotationPageIRIBuilder = (int _page, boolean _embeddedDescriptions) -> {
            return buildPageIri(collectionId, _page, _embeddedDescriptions);
        };

        return new AnnotationPageBuilder<A, P>(annotationPageConverter, annotationCollectionIRIBuilder, annotationPageIRIBuilder).buildAnnotationPage(annotations, page, embeddedDescriptions, pageSize);
    }
}
