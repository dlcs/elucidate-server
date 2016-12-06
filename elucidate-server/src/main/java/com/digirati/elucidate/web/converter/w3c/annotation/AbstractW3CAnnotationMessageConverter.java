package com.digirati.elucidate.web.converter.w3c.annotation;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.web.converter.AbstractMessageConverter;

public abstract class AbstractW3CAnnotationMessageConverter extends AbstractMessageConverter<W3CAnnotation> {

    protected AbstractW3CAnnotationMessageConverter(MediaType... supportedMediaTypes) {
        super(supportedMediaTypes);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return W3CAnnotation.class.equals(clazz);
    }

    @Override
    protected void decorateHeaders(W3CAnnotation w3cAnnotation, HttpOutputMessage outputMessage) {
        outputMessage.getHeaders().add(HttpHeaders.ETAG, String.format("W/\"%s\"", w3cAnnotation.getCacheKey()));
        outputMessage.getHeaders().add(HttpHeaders.LINK, "<http://www.w3.org/ns/ldp#Resource>; rel=\"type\"");
        outputMessage.getHeaders().add(HttpHeaders.ALLOW, "PUT,GET,OPTIONS,HEAD,DELETE");
        outputMessage.getHeaders().add(HttpHeaders.VARY, "Accept");
    }
}
