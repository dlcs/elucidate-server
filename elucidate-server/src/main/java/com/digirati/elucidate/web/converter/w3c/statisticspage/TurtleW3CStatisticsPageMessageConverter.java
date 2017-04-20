package com.digirati.elucidate.web.converter.w3c.statisticspage;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.digirati.elucidate.model.statistics.W3CStatisticsPage;
import com.github.jsonldjava.core.JsonLdProcessor;
import com.github.jsonldjava.impl.TurtleTripleCallback;

@Component
public class TurtleW3CStatisticsPageMessageConverter extends AbstractW3CStatisticsPageMessageConverter {

    private TurtleTripleCallback turtleTripleCallback;

    public TurtleW3CStatisticsPageMessageConverter() {
        super(APPLICATION_TURTLE);
        this.turtleTripleCallback = new TurtleTripleCallback();
    }

    @Override
    protected String getStringRepresentation(W3CStatisticsPage w3cStatisticsPage, MediaType contentType) throws Exception {
        Map<String, Object> jsonMap = w3cStatisticsPage.getJsonMap();
        return JsonLdProcessor.toRDF(jsonMap, turtleTripleCallback, jsonLdOptions).toString();
    }

    @Override
    protected W3CStatisticsPage getObjectRepresentation(String str, MediaType contentType) throws Exception {
        throw new UnsupportedOperationException(String.format("Conversion from Content Type [%s] to [%s] is not supported", contentType, W3CStatisticsPage.class));
    }
}
