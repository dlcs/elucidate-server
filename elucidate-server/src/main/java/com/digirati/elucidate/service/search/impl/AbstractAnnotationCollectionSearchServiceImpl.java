package com.digirati.elucidate.service.search.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.digirati.elucidate.common.infrastructure.constants.SearchConstants;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotationCollection;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotationPage;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationCollection;
import com.digirati.elucidate.common.model.enumeration.SearchType;
import com.digirati.elucidate.infrastructure.builder.AnnotationCollectionBuilder;
import com.digirati.elucidate.infrastructure.builder.function.AnnotationCollectionConverter;
import com.digirati.elucidate.infrastructure.builder.function.AnnotationCollectionIRIBuilder;
import com.digirati.elucidate.infrastructure.builder.function.AnnotationPageIRIBuilder;
import com.digirati.elucidate.infrastructure.builder.function.FirstAnnotationPageBuilder;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.enumeration.ClientPreference;
import com.digirati.elucidate.repository.AnnotationSearchRepository;
import com.digirati.elucidate.service.search.AbstractAnnotationCollectionSearchService;

public abstract class AbstractAnnotationCollectionSearchServiceImpl<P extends AbstractAnnotationPage, C extends AbstractAnnotationCollection> implements AbstractAnnotationCollectionSearchService<C> {

    protected final Logger LOGGER = Logger.getLogger(getClass());

    private AnnotationSearchRepository annotationSearchRepository;
    private int pageSize;

    public AbstractAnnotationCollectionSearchServiceImpl(AnnotationSearchRepository annotationSearchRepository, int pageSize) {
        this.annotationSearchRepository = annotationSearchRepository;
        this.pageSize = pageSize;
    }

    protected abstract C convertToAnnotationCollection(W3CAnnotationCollection w3cAnnotationCollection);

    protected abstract ServiceResponse<P> buildFirstAnnotationPage(SearchType searchType, List<String> fields, String value, boolean strict, String xywh, String t, ClientPreference clientPref);

    protected abstract String buildCollectionIri(SearchType searchType, List<String> fields, String value, boolean strict, String xywh, String t);

    protected abstract String buildPageIri(SearchType searchType, List<String> fields, String value, boolean strict, String xywh, String t, int page, boolean embeddedDescriptions);

    @Override
    @Transactional(readOnly = false)
    public ServiceResponse<C> searchAnnotationCollectionByBody(List<String> fields, String value, boolean strict, ClientPreference clientPref) {

        W3CAnnotationCollection w3cAnnotationCollection = new W3CAnnotationCollection();
        w3cAnnotationCollection.setJsonMap(new HashMap<String, Object>());

        boolean searchIds = fields.contains(SearchConstants.FIELD_ID);
        boolean searchSources = fields.contains(SearchConstants.FIELD_SOURCE);
        List<W3CAnnotation> w3cAnnotations = annotationSearchRepository.getAnnotationsByBody(searchIds, searchSources, value, strict);

        AnnotationCollectionConverter<C> annotationCollectionConverter = () -> {
            return convertToAnnotationCollection(w3cAnnotationCollection);
        };

        AnnotationCollectionIRIBuilder annotationCollectionIriBuilder = () -> {
            return buildCollectionIri(SearchType.BODY, fields, value, strict, null, null);
        };

        AnnotationPageIRIBuilder annotationPageIriBuilder = (int _page, boolean _embeddedDescriptions) -> {
            return buildPageIri(SearchType.BODY, fields, value, strict, null, null, _page, _embeddedDescriptions);
        };

        FirstAnnotationPageBuilder<P> firstAnnotationPageBuilder = () -> {
            return buildFirstAnnotationPage(SearchType.BODY, fields, value, strict, null, null, clientPref);
        };

        return new AnnotationCollectionBuilder<P, C>(annotationCollectionConverter, annotationCollectionIriBuilder, annotationPageIriBuilder, firstAnnotationPageBuilder).buildAnnotationCollection(w3cAnnotationCollection, w3cAnnotations, pageSize, clientPref);
    }

    @Override
    @Transactional(readOnly = false)
    public ServiceResponse<C> searchAnnotationCollectionByTarget(List<String> fields, String value, boolean strict, String xywh, String t, ClientPreference clientPref) {

        W3CAnnotationCollection w3cAnnotationCollection = new W3CAnnotationCollection();
        w3cAnnotationCollection.setJsonMap(new HashMap<String, Object>());

        boolean searchIds = fields.contains(SearchConstants.FIELD_ID);
        boolean searchSources = fields.contains(SearchConstants.FIELD_SOURCE);
        List<W3CAnnotation> w3cAnnotations = annotationSearchRepository.getAnnotationsByTarget(searchIds, searchSources, value, strict);

        AnnotationCollectionConverter<C> annotationCollectionConverter = () -> {
            return convertToAnnotationCollection(w3cAnnotationCollection);
        };

        AnnotationCollectionIRIBuilder annotationCollectionIriBuilder = () -> {
            return buildCollectionIri(SearchType.TARGET, fields, value, strict, xywh, t);
        };

        AnnotationPageIRIBuilder annotationPageIriBuilder = (int _page, boolean _embeddedDescriptions) -> {
            return buildPageIri(SearchType.TARGET, fields, value, strict, xywh, t, _page, _embeddedDescriptions);
        };

        FirstAnnotationPageBuilder<P> firstAnnotationPageBuilder = () -> {
            return buildFirstAnnotationPage(SearchType.TARGET, fields, value, strict, xywh, t, clientPref);
        };

        return new AnnotationCollectionBuilder<P, C>(annotationCollectionConverter, annotationCollectionIriBuilder, annotationPageIriBuilder, firstAnnotationPageBuilder).buildAnnotationCollection(w3cAnnotationCollection, w3cAnnotations, pageSize, clientPref);
    }
}
