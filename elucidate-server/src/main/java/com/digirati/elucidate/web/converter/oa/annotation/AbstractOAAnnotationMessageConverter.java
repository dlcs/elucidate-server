package com.digirati.elucidate.web.converter.oa.annotation;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;

import com.digirati.elucidate.common.model.annotation.oa.OAAnnotation;
import com.digirati.elucidate.web.converter.AbstractMessageConverter;

public abstract class AbstractOAAnnotationMessageConverter extends AbstractMessageConverter<OAAnnotation> {

    protected AbstractOAAnnotationMessageConverter(MediaType... supportedMediaTypes) {
        super(supportedMediaTypes);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return OAAnnotation.class.equals(clazz);
    }

    @Override
    protected void decorateHeaders(OAAnnotation oaAnnotation, HttpOutputMessage outputMessage) {
        outputMessage.getHeaders().add(HttpHeaders.ETAG, String.format("W/\"%s\"", oaAnnotation.getCacheKey()));
        outputMessage.getHeaders().add(HttpHeaders.LINK, "<http://www.w3.org/ns/ldp#Resource>; rel=\"type\"");
        outputMessage.getHeaders().add(HttpHeaders.ALLOW, "PUT,GET,OPTIONS,HEAD,DELETE");
        outputMessage.getHeaders().add(HttpHeaders.VARY, "Accept");
    }
}
