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
import com.digirati.elucidate.repository.AnnotationCollectionStoreRepository;
import com.digirati.elucidate.repository.AnnotationSearchRepository;
import com.digirati.elucidate.repository.AnnotationStoreRepository;
import com.digirati.elucidate.service.W3CAnnotationCollectionService;
import com.digirati.elucidate.service.W3CAnnotationPageService;

@Service(W3CAnnotationCollectionServiceImpl.SERVICE_NAME)
public class W3CAnnotationCollectionServiceImpl extends AbstractAnnotationCollectionServiceImpl<W3CAnnotation, W3CAnnotationPage, W3CAnnotationCollection> implements W3CAnnotationCollectionService {

    public static final String SERVICE_NAME = "w3cAnnotationCollectionServiceImpl";

    private IRIBuilderService iriBuilderService;

    @Autowired
    public W3CAnnotationCollectionServiceImpl(IRIBuilderService iriBuilderService, W3CAnnotationPageService w3cAnnotationPageService, AnnotationStoreRepository annotationStoreRepository, AnnotationCollectionStoreRepository annotationCollectionStoreRepository, AnnotationSearchRepository annotationSearchRepository, @Qualifier("collectionIdGenerator") IDGenerator idGenerator, @Value("${annotation.page.size}") int pageSize) {
        super(w3cAnnotationPageService, annotationStoreRepository, annotationCollectionStoreRepository, annotationSearchRepository, idGenerator, pageSize);
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
    protected String buildCollectionIri(String collectionId) {
        return iriBuilderService.buildW3CCollectionIri(collectionId);
    }

    @Override
    protected String buildCollectionSearchIri(String targetId) {
        return iriBuilderService.buildW3CCollectionSearchIri(targetId);
    }

    @Override
    protected String buildPageIri(String collectionId, int page, boolean embeddedDescriptions) {
        return iriBuilderService.buildW3CPageIri(collectionId, page, embeddedDescriptions);
    }

    @Override
    protected String buildPageSearchIri(String targetId, int page, boolean embeddedDescriptions) {
        return iriBuilderService.buildW3CPageSearchIri(targetId, page, embeddedDescriptions);
    }
}
