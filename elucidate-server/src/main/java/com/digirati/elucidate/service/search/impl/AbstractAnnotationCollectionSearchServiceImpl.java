package com.digirati.elucidate.service.search.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotationCollection;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotationPage;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationCollection;
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
    }

    protected abstract C convertToAnnotationCollection(W3CAnnotationCollection w3cAnnotationCollection);

    protected abstract ServiceResponse<P> buildFirstAnnotationPage(String targetIri, boolean strict, ClientPreference clientPref);

    protected abstract String buildCollectionIri(String targetIri, boolean strict);

    protected abstract String buildPageIri(String targetIri, boolean strict, int page, boolean embeddedDescriptions);

    @Override
    @Transactional(readOnly = true)
    public ServiceResponse<C> searchAnnotationCollection(String targetIri, boolean strict, ClientPreference clientPref) {

        W3CAnnotationCollection w3cAnnotationCollection = new W3CAnnotationCollection();
        w3cAnnotationCollection.setJsonMap(new HashMap<String, Object>());

        List<W3CAnnotation> w3cAnnotations = annotationSearchRepository.getAnnotationsByTargetIri(targetIri, strict);

        AnnotationCollectionConverter<C> annotationCollectionConverter = () -> {
            return convertToAnnotationCollection(w3cAnnotationCollection);
        };

        AnnotationCollectionIRIBuilder annotationCollectionIriBuilder = () -> {
            return buildCollectionIri(targetIri, strict);
        };

        AnnotationPageIRIBuilder annotationPageIriBuilder = (int _page, boolean _embeddedDescriptions) -> {
            return buildPageIri(targetIri, strict, _page, _embeddedDescriptions);
        };

        FirstAnnotationPageBuilder<P> firstAnnotationPageBuilder = () -> {
            return buildFirstAnnotationPage(targetIri, strict, clientPref);
        };

        return new AnnotationCollectionBuilder<P, C>(annotationCollectionConverter, annotationCollectionIriBuilder, annotationPageIriBuilder, firstAnnotationPageBuilder).buildAnnotationCollection(w3cAnnotationCollection, w3cAnnotations, pageSize, clientPref);
    }
}
