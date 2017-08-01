package com.digirati.elucidate.service.history.impl;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.common.service.IRIBuilderService;
import com.digirati.elucidate.model.annotation.history.W3CAnnotationHistory;
import com.digirati.elucidate.repository.AnnotationHistoryRepository;
import com.digirati.elucidate.service.history.W3CAnnotationHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(W3CAnnotationHistoryServiceImpl.SERVICE_NAME)
public class W3CAnnotationHistoryServiceImpl extends AbstractAnnotationHistoryServiceImpl<W3CAnnotation, W3CAnnotationHistory> implements W3CAnnotationHistoryService {

    public static final String SERVICE_NAME = "w3cAnnotationHistoryServiceImpl";

    private IRIBuilderService iriBuilderService;

    @Autowired
    public W3CAnnotationHistoryServiceImpl(AnnotationHistoryRepository annotationHistoryRepository, IRIBuilderService iriBuilderService) {
        super(annotationHistoryRepository);
        this.iriBuilderService = iriBuilderService;
    }

    @Override
    public W3CAnnotationHistory convertToAnnotationHistory(W3CAnnotationHistory w3CAnnotationHistory) {
        return w3CAnnotationHistory;
    }

    @Override
    protected String buildAnnotationIri(String collectionId, String annotationId) {
        return iriBuilderService.buildW3CAnnotationIri(collectionId, annotationId);
    }
}
