package com.digirati.elucidate.web.converter.w3c.statisticspage;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;

import com.digirati.elucidate.model.statistics.W3CStatisticsPage;
import com.digirati.elucidate.web.converter.AbstractMessageConverter;

public abstract class AbstractW3CStatisticsPageMessageConverter extends AbstractMessageConverter<W3CStatisticsPage> {

    protected AbstractW3CStatisticsPageMessageConverter(MediaType... supportedMediaTypes) {
        super(supportedMediaTypes);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return W3CStatisticsPage.class.equals(clazz);
    }

    @Override
    protected void decorateHeaders(W3CStatisticsPage w3cStatisticsPage, HttpOutputMessage outputMessage) {
        outputMessage.getHeaders().add(HttpHeaders.ALLOW, "GET,OPTIONS,HEAD");
        outputMessage.getHeaders().add(HttpHeaders.VARY, "Accept");
        outputMessage.getHeaders().add("Accept-Post", "application/ld+json; profile=\"http://www.w3.org/ns/anno.jsonld\", text/turtle");
    }
}
