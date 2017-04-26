package com.digirati.elucidate.service.batch.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.model.batch.W3CBatchOperation;
import com.digirati.elucidate.service.batch.W3CAnnotationBatchUpdateService;
import com.digirati.elucidate.service.query.W3CAnnotationService;
import com.digirati.elucidate.service.search.W3CAnnotationSearchService;

@Service(W3CAnnotationBatchUpdateServiceImpl.SERVICE_NAME)
public class W3CAnnotationBatchUpdateServiceImpl extends AbstractAnnotationBatchUpdateServiceImpl<W3CAnnotation, W3CBatchOperation> implements W3CAnnotationBatchUpdateService {

    public static final String SERVICE_NAME = "w3cAnnotationBulkUpdateServiceImpl";

    @Autowired
    public W3CAnnotationBatchUpdateServiceImpl(W3CAnnotationService w3cAnnotationService, W3CAnnotationSearchService w3cAnnotationSearchService) {
        super(w3cAnnotationService, w3cAnnotationSearchService);
    }
}
