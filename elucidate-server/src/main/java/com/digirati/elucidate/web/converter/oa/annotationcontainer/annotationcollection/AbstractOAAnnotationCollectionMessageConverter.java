package com.digirati.elucidate.web.converter.oa.annotationcontainer.annotationcollection;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;

import com.digirati.elucidate.common.infrastructure.constants.JSONLDConstants;
import com.digirati.elucidate.common.model.annotation.oa.OAAnnotationCollection;
import com.digirati.elucidate.web.converter.AbstractMessageConverter;

public abstract class AbstractOAAnnotationCollectionMessageConverter extends AbstractMessageConverter<OAAnnotationCollection> {

    protected AbstractOAAnnotationCollectionMessageConverter(MediaType... supportedMediaTypes) {
        super(supportedMediaTypes);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return OAAnnotationCollection.class.equals(clazz);
    }

    @Override
    protected void decorateHeaders(OAAnnotationCollection oaAnnotationCollection, HttpOutputMessage outputMessage) {
        outputMessage.getHeaders().add(HttpHeaders.LINK, "<http://www.w3.org/ns/ldp#BasicContainer>; rel=\"type\"");
        outputMessage.getHeaders().add(HttpHeaders.LINK, "<http://www.w3.org/TR/annotation-protocol/>; rel=\"http://www.w3.org/ns/ldp#constrainedBy\"");
        outputMessage.getHeaders().add(HttpHeaders.ALLOW, "POST,GET,OPTIONS,HEAD");
        outputMessage.getHeaders().add(HttpHeaders.VARY, "Accept,Prefer");
        outputMessage.getHeaders().add(HttpHeaders.CONTENT_LOCATION, (String) oaAnnotationCollection.getJsonMap().get(JSONLDConstants.ATTRIBUTE_ID));
        outputMessage.getHeaders().add("Accept-Post", "application/ld+json; profile=\"http://www.w3.org/ns/anno.jsonld\", text/turtle");
    }
}
