package com.digirati.elucidate.service.search.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotationPage;
import com.digirati.elucidate.common.model.enumeration.SearchType;
import com.digirati.elucidate.infrastructure.builder.AnnotationPageBuilder;
import com.digirati.elucidate.infrastructure.builder.function.AnnotationCollectionIRIBuilder;
import com.digirati.elucidate.infrastructure.builder.function.AnnotationPageConverter;
import com.digirati.elucidate.infrastructure.builder.function.AnnotationPageIRIBuilder;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.service.search.AbstractAnnotationPageSearchService;

public abstract class AbstractAnnotationPageSearchServiceImpl<A extends AbstractAnnotation, P extends AbstractAnnotationPage> implements AbstractAnnotationPageSearchService<A, P> {

    protected final Logger LOGGER = Logger.getLogger(getClass());

    private int pageSize;

    public AbstractAnnotationPageSearchServiceImpl(int pageSize) {
        this.pageSize = pageSize;
    }

    protected abstract P convertToAnnotationPage(Map<String, Object> jsonMap);

    protected abstract String buildCollectionIri(SearchType searchType, List<String> fields, String value, boolean strict, String xywh, String t);

    protected abstract String buildPageIri(SearchType searchType, List<String> fields, String value, boolean strict, String xywh, String t, int page, boolean embeddedDescriptions);

    @Override
    @Transactional(readOnly = false)
    public ServiceResponse<P> buildAnnotationPageByBody(List<A> annotations, List<String> fields, String value, boolean strict, int page, boolean embeddedDescriptions) {

        AnnotationPageConverter<P> annotationPageConverter = (Map<String, Object> _jsonMap) -> {
            return convertToAnnotationPage(_jsonMap);
        };

        AnnotationCollectionIRIBuilder annotationCollectionIriBuilder = () -> {
            return buildCollectionIri(SearchType.BODY, fields, value, strict, null, null);
        };

        AnnotationPageIRIBuilder annotationPageIriBuilder = (int _page, boolean _embeddedDescriptions) -> {
            return buildPageIri(SearchType.BODY, fields, value, strict, null, null, _page, _embeddedDescriptions);
        };

        return new AnnotationPageBuilder<A, P>(annotationPageConverter, annotationCollectionIriBuilder, annotationPageIriBuilder).buildAnnotationPage(annotations, page, embeddedDescriptions, pageSize);
    }

    @Override
    @Transactional(readOnly = true)
    public ServiceResponse<P> buildAnnotationPageByTarget(List<A> annotations, List<String> fields, String value, boolean strict, String xywh, String t, int page, boolean embeddedDescriptions) {

        AnnotationPageConverter<P> annotationPageConverter = (Map<String, Object> _jsonMap) -> {
            return convertToAnnotationPage(_jsonMap);
        };

        AnnotationCollectionIRIBuilder annotationCollectionIriBuilder = () -> {
            return buildCollectionIri(SearchType.TARGET, fields, value, strict, xywh, t);
        };

        AnnotationPageIRIBuilder annotationPageIriBuilder = (int _page, boolean _embeddedDescriptions) -> {
            return buildPageIri(SearchType.TARGET, fields, value, strict, xywh, t, _page, _embeddedDescriptions);
        };

        return new AnnotationPageBuilder<A, P>(annotationPageConverter, annotationCollectionIriBuilder, annotationPageIriBuilder).buildAnnotationPage(annotations, page, embeddedDescriptions, pageSize);
    }
}
