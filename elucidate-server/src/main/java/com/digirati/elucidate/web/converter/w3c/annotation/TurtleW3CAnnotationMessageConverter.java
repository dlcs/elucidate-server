package com.digirati.elucidate.web.converter.w3c.annotation;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.github.jsonldjava.core.JsonLdProcessor;
import com.github.jsonldjava.impl.TurtleTripleCallback;

@Component
public class TurtleW3CAnnotationMessageConverter extends AbstractW3CAnnotationMessageConverter {

    private TurtleTripleCallback turtleTripleCallback;

    public TurtleW3CAnnotationMessageConverter() {
        super(APPLICATION_TURTLE);
        this.turtleTripleCallback = new TurtleTripleCallback();
    }

    @Override
    protected String getStringRepresentation(W3CAnnotation w3cAnnotation, MediaType contentType) throws Exception {
        Map<String, Object> jsonMap = w3cAnnotation.getJsonMap();
        return JsonLdProcessor.toRDF(jsonMap, turtleTripleCallback, jsonLdOptions).toString();
    }

    @Override
    protected W3CAnnotation getObjectRepresentation(String str, MediaType contentType) throws Exception {
        throw new UnsupportedOperationException(String.format("Conversion from Content Type [%s] to [%s] is not supported", contentType, W3CAnnotation.class));
    }
}
