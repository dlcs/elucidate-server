package com.digirati.elucidate.service.batch.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digirati.elucidate.common.model.annotation.oa.OAAnnotation;
import com.digirati.elucidate.model.batch.OABatchOperation;
import com.digirati.elucidate.service.batch.OAAnnotationBatchUpdateService;
import com.digirati.elucidate.service.query.OAAnnotationService;
import com.digirati.elucidate.service.search.OAAnnotationSearchService;

@Service(OAAnnotationBatchUpdateServiceImpl.SERVICE_NAME)
public class OAAnnotationBatchUpdateServiceImpl extends AbstractAnnotationBatchUpdateServiceImpl<OAAnnotation, OABatchOperation> implements OAAnnotationBatchUpdateService {

    public static final String SERVICE_NAME = "oaAnnotationBulkUpdateServiceImpl";

    @Autowired
    public OAAnnotationBatchUpdateServiceImpl(OAAnnotationService oaAnnotationService, OAAnnotationSearchService oaAnnotationSearchService) {
        super(oaAnnotationService, oaAnnotationSearchService);
    }
}
