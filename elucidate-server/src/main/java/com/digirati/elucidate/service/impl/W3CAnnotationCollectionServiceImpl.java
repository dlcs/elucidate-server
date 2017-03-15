package com.digirati.elucidate.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationCollection;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationPage;
import com.digirati.elucidate.common.service.IRIBuilderService;
import com.digirati.elucidate.infrastructure.generator.IDGenerator;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.enumeration.ClientPreference;
import com.digirati.elucidate.model.enumeration.SearchType;
import com.digirati.elucidate.repository.AnnotationCollectionStoreRepository;
import com.digirati.elucidate.repository.AnnotationSearchRepository;
import com.digirati.elucidate.repository.AnnotationStoreRepository;
import com.digirati.elucidate.service.W3CAnnotationCollectionService;
import com.digirati.elucidate.service.W3CAnnotationPageService;

@Service(W3CAnnotationCollectionServiceImpl.SERVICE_NAME)
public class W3CAnnotationCollectionServiceImpl extends AbstractAnnotationCollectionServiceImpl<W3CAnnotation, W3CAnnotationPage, W3CAnnotationCollection> implements W3CAnnotationCollectionService {

    public static final String SERVICE_NAME = "w3cAnnotationCollectionServiceImpl";

    private W3CAnnotationPageService w3cAnnotationPageService;
    private IRIBuilderService iriBuilderService;

    @Autowired
    public W3CAnnotationCollectionServiceImpl(AnnotationStoreRepository annotationStoreRepository, AnnotationCollectionStoreRepository annotationCollectionStoreRepository, AnnotationSearchRepository annotationSearchRepository, IRIBuilderService iriBuilderService, W3CAnnotationPageService w3cAnnotationPageService, @Qualifier("collectionIdGenerator") IDGenerator idGenerator, @Value("${annotation.page.size}") int pageSize) {
        super(annotationStoreRepository, annotationCollectionStoreRepository, annotationSearchRepository, idGenerator, pageSize);
        this.w3cAnnotationPageService = w3cAnnotationPageService;
        this.iriBuilderService = iriBuilderService;
    }

    @Override
    protected W3CAnnotationCollection convertToAnnotationCollection(W3CAnnotationCollection w3cAnnotationCollection) {
        return w3cAnnotationCollection;
    }

    @Override
    protected W3CAnnotationCollection convertFromAnnotationCollection(W3CAnnotationCollection w3cAnnotationCollection) {
        return w3cAnnotationCollection;
    }

    @Override
    protected ServiceResponse<W3CAnnotationPage> buildFirstAnnotationPage(SearchType searchType, String searchQuery, ClientPreference clientPref) {
        if (searchType.equals(SearchType.COLLECTION_ID)) {
            if (clientPref.equals(ClientPreference.CONTAINED_IRIS)) {
                return w3cAnnotationPageService.getAnnotationPage(searchQuery, false, 0);
            } else {
                return w3cAnnotationPageService.getAnnotationPage(searchQuery, true, 0);
            }
        } else if (searchType.equals(SearchType.TARGET_IRI)) {
            if (clientPref.equals(ClientPreference.CONTAINED_IRIS)) {
                return w3cAnnotationPageService.searchAnnotationPage(searchQuery, false, 0);
            } else {
                return w3cAnnotationPageService.searchAnnotationPage(searchQuery, true, 0);
            }
        } else {
            throw new IllegalArgumentException(String.format("Unexpected SearchType [%s]", searchType));
        }
    }

    @Override
    protected String buildCollectionIri(SearchType searchType, String searchQuery) {
        if (searchType.equals(SearchType.COLLECTION_ID)) {
            return iriBuilderService.buildW3CCollectionIri(searchQuery);
        } else if (searchType.equals(SearchType.TARGET_IRI)) {
            return iriBuilderService.buildW3CCollectionSearchIri(searchQuery);
        } else {
            throw new IllegalArgumentException(String.format("Unexpected SearchType [%s]", searchType));
        }
    }

    @Override
    protected String buildPageIri(SearchType searchType, String searchQuery, int page, boolean embeddedDescriptions) {
        if (searchType.equals(SearchType.COLLECTION_ID)) {
            return iriBuilderService.buildW3CPageIri(searchQuery, page, embeddedDescriptions);
        } else if (searchType.equals(SearchType.TARGET_IRI)) {
            return iriBuilderService.buildW3CPageSearchIri(searchQuery, page, embeddedDescriptions);
        } else {
            throw new IllegalArgumentException(String.format("Unexpected SearchType [%s]", searchType));
        }
    }
}
