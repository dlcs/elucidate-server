package com.digirati.elucidate.web.converter.w3c.annotation;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.common.service.IRIBuilderService;
import com.digirati.elucidate.service.history.W3CAnnotationHistoryService;
import com.github.jsonldjava.core.JsonLdProcessor;
import com.github.jsonldjava.impl.TurtleTripleCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TurtleW3CAnnotationMessageConverter extends AbstractW3CAnnotationMessageConverter {

    private TurtleTripleCallback turtleTripleCallback;

    @Autowired
    public TurtleW3CAnnotationMessageConverter(IRIBuilderService iriBuilderService, W3CAnnotationHistoryService w3cAnnotationHistoryService) {
        super(iriBuilderService, w3cAnnotationHistoryService, APPLICATION_TURTLE);
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
