package com.digirati.elucidate.web.converter.w3c.annotationcontainer.annotationpage;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationPage;
import com.github.jsonldjava.core.JsonLdProcessor;
import com.github.jsonldjava.impl.TurtleTripleCallback;

@Component
public class TurtleW3CAnnotationPageMessageConverter extends AbstractW3CAnnotationPageMessageConverter {

    private TurtleTripleCallback turtleTripleCallback;

    public TurtleW3CAnnotationPageMessageConverter() {
        super(APPLICATION_TURTLE);
        this.turtleTripleCallback = new TurtleTripleCallback();
    }

    @Override
    protected String getStringRepresentation(W3CAnnotationPage w3cAnnotationPage, MediaType contentType) throws Exception {
        Map<String, Object> jsonMap = w3cAnnotationPage.getJsonMap();
        return JsonLdProcessor.toRDF(jsonMap, turtleTripleCallback, jsonLdOptions).toString();
    }

    @Override
    protected W3CAnnotationPage getObjectRepresentation(String str, MediaType contentType) throws Exception {
        throw new UnsupportedOperationException(String.format("Conversion from Content Type [%s] to [%s] is not supported", contentType, W3CAnnotationPage.class));
    }
}
