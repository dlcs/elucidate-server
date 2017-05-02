package com.digirati.elucidate.web.converter.oa.bulkupdate;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;

import com.digirati.elucidate.model.batch.OABatchOperation;
import com.digirati.elucidate.web.converter.AbstractMessageConverter;

public abstract class AbstractOABatchOperationMessageConverter extends AbstractMessageConverter<OABatchOperation> {

    protected AbstractOABatchOperationMessageConverter(MediaType... supportedMediaTypes) {
        super(supportedMediaTypes);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return OABatchOperation.class.equals(clazz);
    }

    @Override
    protected void decorateHeaders(OABatchOperation oaBatchOperation, HttpOutputMessage outputMessage) {}
}
