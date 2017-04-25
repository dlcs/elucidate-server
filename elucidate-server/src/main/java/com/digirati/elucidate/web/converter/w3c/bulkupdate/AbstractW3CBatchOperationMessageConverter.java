package com.digirati.elucidate.web.converter.w3c.bulkupdate;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;

import com.digirati.elucidate.model.batch.W3CBatchOperation;
import com.digirati.elucidate.web.converter.AbstractMessageConverter;

public abstract class AbstractW3CBatchOperationMessageConverter extends AbstractMessageConverter<W3CBatchOperation> {

    protected AbstractW3CBatchOperationMessageConverter(MediaType... supportedMediaTypes) {
        super(supportedMediaTypes);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return W3CBatchOperation.class.equals(clazz);
    }

    @Override
    protected void decorateHeaders(W3CBatchOperation w3cBatchOperation, HttpOutputMessage outputMessage) {}
}
