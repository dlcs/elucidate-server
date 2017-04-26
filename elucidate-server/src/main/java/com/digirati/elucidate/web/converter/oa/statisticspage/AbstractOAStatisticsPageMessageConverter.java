package com.digirati.elucidate.web.converter.oa.statisticspage;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;

import com.digirati.elucidate.model.statistics.OAStatisticsPage;
import com.digirati.elucidate.web.converter.AbstractMessageConverter;

public abstract class AbstractOAStatisticsPageMessageConverter extends AbstractMessageConverter<OAStatisticsPage> {

    protected AbstractOAStatisticsPageMessageConverter(MediaType... supportedMediaTypes) {
        super(supportedMediaTypes);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return OAStatisticsPage.class.equals(clazz);
    }

    @Override
    protected void decorateHeaders(OAStatisticsPage oaStatisticsPage, HttpOutputMessage outputMessage) {
        outputMessage.getHeaders().add(HttpHeaders.ALLOW, "GET,OPTIONS,HEAD");
        outputMessage.getHeaders().add(HttpHeaders.VARY, "Accept");
        outputMessage.getHeaders().add("Accept-Post", "application/ld+json; profile=\"http://www.w3.org/ns/anno.jsonld\", text/turtle");
    }
}
