package com.digirati.elucidate.service.batch.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.model.batch.W3CBatchOperation;
import com.digirati.elucidate.service.batch.W3CAnnotationBatchDeleteService;
import com.digirati.elucidate.service.query.W3CAnnotationService;
import com.digirati.elucidate.service.search.W3CAnnotationSearchService;

@Service(W3CAnnotationBatchDeleteServiceImpl.SERVICE_NAME)
public class W3CAnnotationBatchDeleteServiceImpl extends AbstractAnnotationBatchDeleteServiceImpl<W3CAnnotation, W3CBatchOperation> implements W3CAnnotationBatchDeleteService {

    public static final String SERVICE_NAME = "w3cAnnotationBatchDeleteServiceImpl";

    @Autowired
    public W3CAnnotationBatchDeleteServiceImpl(W3CAnnotationService w3cAnnotationService, W3CAnnotationSearchService w3cAnnotationSearchService) {
        super(w3cAnnotationService, w3cAnnotationSearchService);
    }
}
