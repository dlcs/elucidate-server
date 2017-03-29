package com.digirati.elucidate.service.query.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotationCollection;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotationPage;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationCollection;
import com.digirati.elucidate.infrastructure.builder.AnnotationCollectionBuilder;
import com.digirati.elucidate.infrastructure.builder.function.AnnotationCollectionConverter;
import com.digirati.elucidate.infrastructure.builder.function.AnnotationCollectionIRIBuilder;
import com.digirati.elucidate.infrastructure.builder.function.AnnotationPageIRIBuilder;
import com.digirati.elucidate.infrastructure.builder.function.FirstAnnotationPageBuilder;
import com.digirati.elucidate.infrastructure.generator.IDGenerator;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.ServiceResponse.Status;
import com.digirati.elucidate.model.enumeration.ClientPreference;
import com.digirati.elucidate.repository.AnnotationCollectionStoreRepository;
import com.digirati.elucidate.service.query.AbstractAnnotationCollectionService;
import com.digirati.elucidate.service.query.AbstractAnnotationService;
import com.github.jsonldjava.utils.JsonUtils;

public abstract class AbstractAnnotationCollectionServiceImpl<A extends AbstractAnnotation, P extends AbstractAnnotationPage, C extends AbstractAnnotationCollection> implements AbstractAnnotationCollectionService<A, C> {

    protected final Logger LOGGER = Logger.getLogger(getClass());

    private AnnotationCollectionStoreRepository annotationCollectionStoreRepository;
    private IDGenerator idGenerator;
    private int pageSize;

    public AbstractAnnotationCollectionServiceImpl(AnnotationCollectionStoreRepository annotationCollectionStoreRepository, AbstractAnnotationService<A> annotationService, IDGenerator idGenerator, int pageSize) {
        this.annotationCollectionStoreRepository = annotationCollectionStoreRepository;
        this.idGenerator = idGenerator;
        this.pageSize = pageSize;
    }

    protected abstract C convertToAnnotationCollection(W3CAnnotationCollection w3cAnnotationCollection);

    protected abstract W3CAnnotationCollection convertFromAnnotationCollection(C annotationCollection);

    protected abstract ServiceResponse<P> buildFirstAnnotationPage(List<A> annotations, String collectionId, ClientPreference clientPref);

    protected abstract String buildCollectionIri(String collectionId);

    protected abstract String buildPageIri(String collectionId, int page, boolean embeddedDescriptions);

    @Override
    @Transactional(readOnly = true)
    public ServiceResponse<C> getAnnotationCollection(String collectionId, List<A> annotations, ClientPreference clientPref) {

        W3CAnnotationCollection w3cAnnotationCollection = annotationCollectionStoreRepository.getAnnotationCollectionById(collectionId);
        if (w3cAnnotationCollection == null) {
            return new ServiceResponse<C>(Status.NOT_FOUND, null);
        }

        AnnotationCollectionConverter<C> annotationCollectionConverter = () -> {
            return convertToAnnotationCollection(w3cAnnotationCollection);
        };

        AnnotationCollectionIRIBuilder annotationCollectionIriBuilder = () -> {
            return buildCollectionIri(collectionId);
        };

        AnnotationPageIRIBuilder annotationPageIriBuilder = (int _page, boolean _embeddedDescriptions) -> {
            return buildPageIri(collectionId, _page, _embeddedDescriptions);
        };

        FirstAnnotationPageBuilder<P> firstAnnotationPageBuilder = () -> {
            return buildFirstAnnotationPage(annotations, collectionId, clientPref);
        };

        return new AnnotationCollectionBuilder<A, P, C>(annotationCollectionConverter, annotationCollectionIriBuilder, annotationPageIriBuilder, firstAnnotationPageBuilder).buildAnnotationCollection(w3cAnnotationCollection, annotations, pageSize, clientPref);
    }

    @Override
    @Transactional(readOnly = false)
    public ServiceResponse<C> createAnnotationCollection(String collectionId, C annotationCollection) {

        if (StringUtils.isBlank(collectionId)) {
            collectionId = idGenerator.generateId();
        }

        if (!validateCollectionId(collectionId)) {
            return new ServiceResponse<C>(Status.NON_CONFORMANT, null);
        }

        ServiceResponse<C> serviceResponse = getAnnotationCollection(collectionId, Collections.emptyList(), ClientPreference.MINIMAL_CONTAINER);
        Status status = serviceResponse.getStatus();
        if (status.equals(Status.OK)) {
            return new ServiceResponse<C>(Status.ALREADY_EXISTS, null);
        }

        W3CAnnotationCollection w3cAnnotationCollection = convertFromAnnotationCollection(annotationCollection);

        String w3cAnnotationCollectionJson;
        try {
            Map<String, Object> w3cAnnotationCollectionMap = w3cAnnotationCollection.getJsonMap();
            w3cAnnotationCollectionJson = JsonUtils.toPrettyString(w3cAnnotationCollectionMap);
        } catch (IOException e) {
            LOGGER.debug(String.format("Detected invalid JSON on W3C Annotation Collection [%s]", w3cAnnotationCollection), e);
            return new ServiceResponse<C>(Status.NON_CONFORMANT, null);
        }

        annotationCollectionStoreRepository.createAnnotationCollection(collectionId, w3cAnnotationCollectionJson);
        return getAnnotationCollection(collectionId, Collections.emptyList(), ClientPreference.CONTAINED_DESCRIPTIONS);
    }

    private boolean validateCollectionId(String collectionId) {
        return StringUtils.isNotBlank(collectionId) && collectionId.length() <= 100;
    }
}
