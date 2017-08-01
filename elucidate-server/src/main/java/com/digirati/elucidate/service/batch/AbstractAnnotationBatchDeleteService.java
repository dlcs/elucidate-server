package com.digirati.elucidate.service.batch;

import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.batch.AbstractBatchOperation;

public interface AbstractAnnotationBatchDeleteService<B extends AbstractBatchOperation> {

    ServiceResponse<B> processBatchDelete(B batchOperation);
}
