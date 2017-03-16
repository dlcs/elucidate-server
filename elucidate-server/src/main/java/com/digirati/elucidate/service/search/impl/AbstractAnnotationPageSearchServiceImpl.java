package com.digirati.elucidate.service.search.impl;

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
import com.digirati.elucidate.service.search.AbstractAnnotationPageSearchService;
import com.digirati.elucidate.service.search.AbstractAnnotationSearchService;

public abstract class AbstractAnnotationPageSearchServiceImpl<A extends AbstractAnnotation, P extends AbstractAnnotationPage> implements AbstractAnnotationPageSearchService<P> {

    protected final Logger LOGGER = Logger.getLogger(getClass());

    private AbstractAnnotationSearchService<A> annotationSearchService;
    private int pageSize;

    public AbstractAnnotationPageSearchServiceImpl(AbstractAnnotationSearchService<A> annotationSearchService, int pageSize) {
        this.annotationSearchService = annotationSearchService;
        this.pageSize = pageSize;
    }

    protected abstract P convertToAnnotationPage(Map<String, Object> jsonMap);

    protected abstract String buildCollectionIri(String targetIri, boolean strict);

    protected abstract String buildPageIri(String targetIri, boolean strict, int page, boolean embeddedDescriptions);

    @Override
    @Transactional(readOnly = true)
    public ServiceResponse<P> searchAnnotationPage(String targetIri, boolean strict, int page, boolean embeddedDescription) {

        ServiceResponse<List<A>> serviceResponse = annotationSearchService.searchAnnotations(targetIri, strict);
        Status status = serviceResponse.getStatus();

        if (!status.equals(Status.OK)) {
            return new ServiceResponse<P>(status, null);
        }

        List<A> annotations = serviceResponse.getObj();

        AnnotationPageConverter<P> annotationPageConverter = (Map<String, Object> _jsonMap) -> {
            return convertToAnnotationPage(_jsonMap);
        };

        AnnotationCollectionIRIBuilder annotationCollectionIriBuilder = () -> {
            return buildCollectionIri(targetIri, strict);
        };

        AnnotationPageIRIBuilder annotationPageIriBuilder = (int _page, boolean _embeddedDescriptions) -> {
            return buildPageIri(targetIri, strict, _page, _embeddedDescriptions);
        };

        return new AnnotationPageBuilder<A, P>(annotationPageConverter, annotationCollectionIriBuilder, annotationPageIriBuilder).buildAnnotationPage(annotations, page, embeddedDescription, pageSize);
    }
}
