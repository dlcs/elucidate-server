package com.digirati.elucidate.web.converter.w3c.annotationcontainer.annotationpage;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationPage;
import com.digirati.elucidate.web.converter.AbstractMessageConverter;

public abstract class AbstractW3CAnnotationPageMessageConverter extends AbstractMessageConverter<W3CAnnotationPage> {

    protected AbstractW3CAnnotationPageMessageConverter(MediaType... supportedMediaTypes) {
        super(supportedMediaTypes);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return W3CAnnotationPage.class.equals(clazz);
    }

    @Override
    protected void decorateHeaders(W3CAnnotationPage w3cAnnotationPage, HttpOutputMessage outputMessage) {
        outputMessage.getHeaders().add(HttpHeaders.ALLOW, "GET,OPTIONS,HEAD");
        outputMessage.getHeaders().add(HttpHeaders.VARY, "Accept");
        outputMessage.getHeaders().add("Accept-Post", "application/ld+json; profile=\"http://www.w3.org/ns/anno.jsonld\", text/turtle");
    }
}
