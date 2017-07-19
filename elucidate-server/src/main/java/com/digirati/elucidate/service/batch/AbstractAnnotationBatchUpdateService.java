package com.digirati.elucidate.service.batch;

import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.batch.AbstractBatchOperation;

public interface AbstractAnnotationBatchUpdateService<B extends AbstractBatchOperation> {

    ServiceResponse<B> processBatchUpdate(B batchOperation);
}
