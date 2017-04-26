package com.digirati.elucidate.service.batch;

import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.batch.AbstractBatchOperation;

public abstract interface AbstractAnnotationBatchUpdateService<B extends AbstractBatchOperation> {

    public ServiceResponse<B> processBatchUpdate(B batchOperation);
}
