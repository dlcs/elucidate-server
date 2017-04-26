package com.digirati.elucidate.service.batch.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digirati.elucidate.common.model.annotation.oa.OAAnnotation;
import com.digirati.elucidate.model.batch.OABatchOperation;
import com.digirati.elucidate.service.batch.OAAnnotationBatchDeleteService;
import com.digirati.elucidate.service.query.OAAnnotationService;
import com.digirati.elucidate.service.search.OAAnnotationSearchService;

@Service(OAAnnotationBatchDeleteServiceImpl.SERVICE_NAME)
public class OAAnnotationBatchDeleteServiceImpl extends AbstractAnnotationBatchDeleteServiceImpl<OAAnnotation, OABatchOperation> implements OAAnnotationBatchDeleteService {

    public static final String SERVICE_NAME = "oaAnnotationBatchDeleteServiceImpl";

    @Autowired
    public OAAnnotationBatchDeleteServiceImpl(OAAnnotationService oaAnnotationService, OAAnnotationSearchService oaAnnotationSearchService) {
        super(oaAnnotationService, oaAnnotationSearchService);
    }
}
