package com.digirati.elucidate.web.converter.oa.history;

import com.digirati.elucidate.common.service.IRIBuilderService;
import com.digirati.elucidate.model.annotation.history.OAAnnotationHistory;
import com.digirati.elucidate.service.history.OAAnnotationHistoryService;
import com.github.jsonldjava.core.JsonLdProcessor;
import com.github.jsonldjava.impl.TurtleTripleCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TurtleOAAnnotationHistoryMessageConverter extends AbstractOAAnnotationHistoryMessageConverter {

    private TurtleTripleCallback turtleTripleCallback;

    @Autowired
    public TurtleOAAnnotationHistoryMessageConverter(IRIBuilderService iriBuilderService, OAAnnotationHistoryService oaAnnotationHistoryService) {
        super(iriBuilderService, oaAnnotationHistoryService, APPLICATION_TURTLE);
        this.turtleTripleCallback = new TurtleTripleCallback();
    }

    @Override
    protected String getStringRepresentation(OAAnnotationHistory oaAnnotationHistory, MediaType contentType) throws Exception {
        Map<String, Object> jsonMap = oaAnnotationHistory.getJsonMap();
        return JsonLdProcessor.toRDF(jsonMap, turtleTripleCallback, jsonLdOptions).toString();
    }

    @Override
    protected OAAnnotationHistory getObjectRepresentation(String str, MediaType contentType) throws Exception {
        throw new UnsupportedOperationException(String.format("Conversion from Content Type [%s] to [%s] is not supported", contentType, OAAnnotationHistory.class));
    }
}
