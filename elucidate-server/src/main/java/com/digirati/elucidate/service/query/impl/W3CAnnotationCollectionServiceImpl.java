package com.digirati.elucidate.service.query.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationCollection;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationPage;
import com.digirati.elucidate.common.service.IRIBuilderService;
import com.digirati.elucidate.infrastructure.generator.IDGenerator;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.enumeration.ClientPreference;
import com.digirati.elucidate.repository.AnnotationCollectionStoreRepository;
import com.digirati.elucidate.repository.AnnotationSearchRepository;
import com.digirati.elucidate.repository.AnnotationStoreRepository;
import com.digirati.elucidate.service.query.W3CAnnotationCollectionService;
import com.digirati.elucidate.service.query.W3CAnnotationPageService;

@Service(W3CAnnotationCollectionServiceImpl.SERVICE_NAME)
public class W3CAnnotationCollectionServiceImpl extends AbstractAnnotationCollectionServiceImpl<W3CAnnotationPage, W3CAnnotationCollection> implements W3CAnnotationCollectionService {

    public static final String SERVICE_NAME = "w3cAnnotationCollectionServiceImpl";

    private IRIBuilderService iriBuilderService;
    private W3CAnnotationPageService w3cAnnotationPageService;

    @Autowired
    public W3CAnnotationCollectionServiceImpl(AnnotationStoreRepository annotationStoreRepository, AnnotationCollectionStoreRepository annotationCollectionStoreRepository, AnnotationSearchRepository annotationSearchRepository, IRIBuilderService iriBuilderService, W3CAnnotationPageService w3cAnnotationPageService, @Qualifier("collectionIdGenerator") IDGenerator idGenerator, @Value("${annotation.page.size}") int pageSize) {
        super(annotationStoreRepository, annotationCollectionStoreRepository, idGenerator, pageSize);
        this.iriBuilderService = iriBuilderService;
        this.w3cAnnotationPageService = w3cAnnotationPageService;
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
    protected ServiceResponse<W3CAnnotationPage> buildFirstAnnotationPage(String collectionId, ClientPreference clientPref) {
        if (clientPref.equals(ClientPreference.CONTAINED_IRIS)) {
            return w3cAnnotationPageService.getAnnotationPage(collectionId, 0, false);
        } else {
            return w3cAnnotationPageService.getAnnotationPage(collectionId, 0, true);
        }
    }

    @Override
    protected String buildCollectionIri(String searchQuery) {
        return iriBuilderService.buildW3CCollectionIri(searchQuery);
    }

    @Override
    protected String buildPageIri(String searchQuery, int page, boolean embeddedDescriptions) {
        return iriBuilderService.buildW3CPageIri(searchQuery, page, embeddedDescriptions);
    }
}
