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
import com.digirati.elucidate.service.query.AbstractAnnotationPageService;

public abstract class AbstractAnnotationPageServiceImpl<A extends AbstractAnnotation, P extends AbstractAnnotationPage> implements AbstractAnnotationPageService<A, P> {

    protected final Logger LOGGER = Logger.getLogger(getClass());

    private int pageSize;

    public AbstractAnnotationPageServiceImpl(int pageSize) {
        this.pageSize = pageSize;
    }

    protected abstract P convertToAnnotationPage(Map<String, Object> jsonMap);

    protected abstract String buildCollectionIri(String collectionId);

    protected abstract String buildPageIri(String collectionId, int page, boolean embeddedDescriptions);

    @Override
    @Transactional(readOnly = true)
    public ServiceResponse<P> buildAnnotationPage(List<A> annotations, String collectionId, int page, boolean embeddedDescriptions) {

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
