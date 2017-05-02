package com.digirati.elucidate.web.converter.oa.annotationcontainer.annotationcollection;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.digirati.elucidate.common.model.annotation.oa.OAAnnotationCollection;
import com.github.jsonldjava.core.JsonLdProcessor;
import com.github.jsonldjava.impl.TurtleTripleCallback;

@Component
public class TurtleOAAnnotationCollectionMessageConverter extends AbstractOAAnnotationCollectionMessageConverter {

    private TurtleTripleCallback turtleTripleCallback;

    public TurtleOAAnnotationCollectionMessageConverter() {
        super(APPLICATION_TURTLE);
        this.turtleTripleCallback = new TurtleTripleCallback();
    }

    @Override
    protected String getStringRepresentation(OAAnnotationCollection oaAnnotationCollection, MediaType contentType) throws Exception {
        Map<String, Object> jsonMap = oaAnnotationCollection.getJsonMap();
        return JsonLdProcessor.toRDF(jsonMap, turtleTripleCallback, jsonLdOptions).toString();
    }

    @Override
    protected OAAnnotationCollection getObjectRepresentation(String str, MediaType contentType) throws Exception {
        throw new UnsupportedOperationException(String.format("Conversion from Content Type [%s] to [%s] is not supported", contentType, OAAnnotationCollection.class));
    }
}
