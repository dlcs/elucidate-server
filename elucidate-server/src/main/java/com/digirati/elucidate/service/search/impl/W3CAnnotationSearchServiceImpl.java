package com.digirati.elucidate.service.search.impl;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.common.service.IRIBuilderService;
import com.digirati.elucidate.infrastructure.security.UserSecurityDetailsContext;
import com.digirati.elucidate.repository.AnnotationSearchRepository;
import com.digirati.elucidate.service.search.W3CAnnotationSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(W3CAnnotationSearchServiceImpl.SERVICE_NAME)
public class W3CAnnotationSearchServiceImpl extends AbstractAnnotationSearchServiceImpl<W3CAnnotation> implements W3CAnnotationSearchService {

    public static final String SERVICE_NAME = "w3cAnnotationSearchServiceImpl";

    private IRIBuilderService iriBuilderService;

    @Autowired
    public W3CAnnotationSearchServiceImpl(UserSecurityDetailsContext securityContext, AnnotationSearchRepository annotationSearchRepository, IRIBuilderService iriBuilderService) {
        super(securityContext, annotationSearchRepository);
        this.iriBuilderService = iriBuilderService;
    }

    @Override
    protected W3CAnnotation convertToAnnotation(W3CAnnotation w3cAnnotation) {
        return w3cAnnotation;
    }

    @Override
    protected String buildAnnotationIri(String collectionId, String annotationId) {
        return iriBuilderService.buildW3CAnnotationIri(collectionId, annotationId);
    }
}
