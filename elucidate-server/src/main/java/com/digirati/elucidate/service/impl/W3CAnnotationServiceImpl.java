package com.digirati.elucidate.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationCollection;
import com.digirati.elucidate.common.service.IRIBuilderService;
import com.digirati.elucidate.infrastructure.generator.IDGenerator;
import com.digirati.elucidate.repository.AnnotationSearchRepository;
import com.digirati.elucidate.repository.AnnotationStoreRepository;
import com.digirati.elucidate.service.W3CAnnotationService;

@Service(W3CAnnotationServiceImpl.SERVICE_NAME)
public class W3CAnnotationServiceImpl extends AbstractAnnotationServiceImpl<W3CAnnotation, W3CAnnotationCollection> implements W3CAnnotationService {

    public static final String SERVICE_NAME = "w3cAnnotationServiceImpl";

    private IRIBuilderService iriBuilderService;

    @Autowired
    public W3CAnnotationServiceImpl(IRIBuilderService iriBuilderService, AnnotationStoreRepository annotationRepository, AnnotationSearchRepository annotationSearchRepository, @Qualifier("annotationIdGenerator") IDGenerator idGenerator) {
        super(annotationRepository, annotationSearchRepository, idGenerator);
        this.iriBuilderService = iriBuilderService;
    }

    @Override
    protected W3CAnnotation convertToAnnotation(W3CAnnotation w3cAnnotation) {
        return w3cAnnotation;
    }

    @Override
    protected W3CAnnotation convertFromAnnotation(W3CAnnotation w3cAnnotation) {
        return w3cAnnotation;
    }

    @Override
    protected String buildAnnotationIri(String collectionId, String annotationId) {
        return iriBuilderService.buildW3CAnnotationIri(collectionId, annotationId);
    }
}
