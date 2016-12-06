package com.digirati.elucidate.web.converter.oa.annotationcontainer.annotationpage;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;

import com.digirati.elucidate.common.model.annotation.oa.OAAnnotationPage;
import com.digirati.elucidate.web.converter.AbstractMessageConverter;

public abstract class AbstractOAAnnotationPageMessageConverter extends AbstractMessageConverter<OAAnnotationPage> {

    protected AbstractOAAnnotationPageMessageConverter(MediaType... supportedMediaTypes) {
        super(supportedMediaTypes);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return OAAnnotationPage.class.equals(clazz);
    }

    @Override
    protected void decorateHeaders(OAAnnotationPage oaAnnotationPage, HttpOutputMessage outputMessage) {
        outputMessage.getHeaders().add(HttpHeaders.ALLOW, "GET,OPTIONS,HEAD");
        outputMessage.getHeaders().add(HttpHeaders.VARY, "Accept");
        outputMessage.getHeaders().add("Accept-Post", "application/ld+json; profile=\"http://www.w3.org/ns/anno.jsonld\", text/turtle");
    }
}
