package com.digirati.elucidate.service.search.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotationCollection;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotationPage;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationCollection;
import com.digirati.elucidate.common.model.enumeration.SearchType;
import com.digirati.elucidate.infrastructure.builder.AnnotationCollectionBuilder;
import com.digirati.elucidate.infrastructure.builder.function.AnnotationCollectionConverter;
import com.digirati.elucidate.infrastructure.builder.function.AnnotationCollectionIRIBuilder;
import com.digirati.elucidate.infrastructure.builder.function.AnnotationPageIRIBuilder;
import com.digirati.elucidate.infrastructure.builder.function.FirstAnnotationPageBuilder;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.ServiceResponse.Status;
import com.digirati.elucidate.model.enumeration.ClientPreference;
import com.digirati.elucidate.service.search.AbstractAnnotationCollectionSearchService;
import com.digirati.elucidate.service.search.AbstractAnnotationSearchService;

public abstract class AbstractAnnotationCollectionSearchServiceImpl<A extends AbstractAnnotation, P extends AbstractAnnotationPage, C extends AbstractAnnotationCollection> implements AbstractAnnotationCollectionSearchService<C> {

    protected final Logger LOGGER = Logger.getLogger(getClass());

    private AbstractAnnotationSearchService<A> annotationSearchService;
    private int pageSize;

    public AbstractAnnotationCollectionSearchServiceImpl(AbstractAnnotationSearchService<A> annotationSearchService, int pageSize) {
        this.annotationSearchService = annotationSearchService;
        this.pageSize = pageSize;
    }

    protected abstract C convertToAnnotationCollection(W3CAnnotationCollection w3cAnnotationCollection);

    protected abstract ServiceResponse<P> buildFirstAnnotationPage(SearchType searchType, List<A> annotations, List<String> fields, String value, boolean strict, String xywh, String t, ClientPreference clientPref);

    protected abstract String buildCollectionIri(SearchType searchType, List<String> fields, String value, boolean strict, String xywh, String t);

    protected abstract String buildPageIri(SearchType searchType, List<String> fields, String value, boolean strict, String xywh, String t, int page, boolean embeddedDescriptions);

    @Override
    public ServiceResponse<C> searchAnnotationCollectionByBody(List<String> fields, String value, boolean strict, ClientPreference clientPref) {

        W3CAnnotationCollection w3cAnnotationCollection = new W3CAnnotationCollection();
        w3cAnnotationCollection.setJsonMap(new HashMap<String, Object>());

        ServiceResponse<List<A>> serviceResponse = annotationSearchService.searchAnnotationsByBody(fields, value, strict);
        Status status = serviceResponse.getStatus();

        if (!status.equals(Status.OK)) {
            return new ServiceResponse<C>(status, null);
        }

        List<A> annotations = serviceResponse.getObj();

        AnnotationCollectionConverter<C> annotationCollectionConverter = () -> convertToAnnotationCollection(w3cAnnotationCollection);
        AnnotationCollectionIRIBuilder annotationCollectionIriBuilder = () -> buildCollectionIri(SearchType.BODY, fields, value, strict, null, null);
        AnnotationPageIRIBuilder annotationPageIriBuilder = (int _page, boolean _embeddedDescriptions) -> buildPageIri(SearchType.BODY, fields, value, strict, null, null, _page, _embeddedDescriptions);
        FirstAnnotationPageBuilder<P> firstAnnotationPageBuilder = () -> buildFirstAnnotationPage(SearchType.BODY, annotations, fields, value, strict, null, null, clientPref);

        return new AnnotationCollectionBuilder<A, P, C>(annotationCollectionConverter, annotationCollectionIriBuilder, annotationPageIriBuilder, firstAnnotationPageBuilder).buildAnnotationCollection(w3cAnnotationCollection, annotations, pageSize, clientPref);
    }

    @Override
    public ServiceResponse<C> searchAnnotationCollectionByTarget(List<String> fields, String value, boolean strict, String xywh, String t, ClientPreference clientPref) {

        W3CAnnotationCollection w3cAnnotationCollection = new W3CAnnotationCollection();
        w3cAnnotationCollection.setJsonMap(new HashMap<String, Object>());

        ServiceResponse<List<A>> serviceResponse = annotationSearchService.searchAnnotationsByTarget(fields, value, strict, xywh, t);
        Status status = serviceResponse.getStatus();

        if (!status.equals(Status.OK)) {
            return new ServiceResponse<C>(status, null);
        }

        List<A> annotations = serviceResponse.getObj();

        AnnotationCollectionConverter<C> annotationCollectionConverter = () -> convertToAnnotationCollection(w3cAnnotationCollection);
        AnnotationCollectionIRIBuilder annotationCollectionIriBuilder = () -> buildCollectionIri(SearchType.TARGET, fields, value, strict, xywh, t);
        AnnotationPageIRIBuilder annotationPageIriBuilder = (int _page, boolean _embeddedDescriptions) -> buildPageIri(SearchType.TARGET, fields, value, strict, xywh, t, _page, _embeddedDescriptions);
        FirstAnnotationPageBuilder<P> firstAnnotationPageBuilder = () -> buildFirstAnnotationPage(SearchType.TARGET, annotations, fields, value, strict, xywh, t, clientPref);

        return new AnnotationCollectionBuilder<A, P, C>(annotationCollectionConverter, annotationCollectionIriBuilder, annotationPageIriBuilder, firstAnnotationPageBuilder).buildAnnotationCollection(w3cAnnotationCollection, annotations, pageSize, clientPref);
    }
}
