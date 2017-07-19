package com.digirati.elucidate.web.converter.w3c.history;

import com.digirati.elucidate.common.service.IRIBuilderService;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.ServiceResponse.Status;
import com.digirati.elucidate.model.annotation.history.W3CAnnotationHistory;
import com.digirati.elucidate.service.history.W3CAnnotationHistoryService;
import com.digirati.elucidate.web.converter.AbstractMessageConverter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;

public abstract class AbstractW3CAnnotationHistoryMessageConverter extends AbstractMessageConverter<W3CAnnotationHistory> {

    private IRIBuilderService iriBuilderService;
    private W3CAnnotationHistoryService w3cAnnotationHistoryService;

    protected AbstractW3CAnnotationHistoryMessageConverter(IRIBuilderService iriBuilderService, W3CAnnotationHistoryService w3cAnnotationHistoryService, MediaType... supportedMediaTypes) {
        super(supportedMediaTypes);
        this.iriBuilderService = iriBuilderService;
        this.w3cAnnotationHistoryService = w3cAnnotationHistoryService;
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return W3CAnnotationHistory.class.equals(clazz);
    }

    @Override
    protected void decorateHeaders(W3CAnnotationHistory w3cAnnotationHistory, HttpOutputMessage outputMessage) {
        decorateMementoDatetimeHeader(w3cAnnotationHistory, outputMessage);
        decorateNextVersionHeader(w3cAnnotationHistory, outputMessage);
        decoratePreviousVersionHeader(w3cAnnotationHistory, outputMessage);
    }

    private void decorateMementoDatetimeHeader(W3CAnnotationHistory w3cAnnotationHistory, HttpOutputMessage outputMessage) {
        String dateStr = MEMENTO_DATE_FORMAT.format(w3cAnnotationHistory.getCreatedDateTime());
        outputMessage.getHeaders().add("Memento-Datetime", dateStr);
    }

    private void decorateNextVersionHeader(W3CAnnotationHistory w3cAnnotationHistory, HttpOutputMessage outputMessage) {

        String collectionId = w3cAnnotationHistory.getCollectionId();
        String annotationId = w3cAnnotationHistory.getAnnotationId();
        int version = w3cAnnotationHistory.getVersion();

        ServiceResponse<W3CAnnotationHistory> serviceResponse = w3cAnnotationHistoryService.getNextAnnotationVersion(collectionId, annotationId, version);
        Status status = serviceResponse.getStatus();

        if (status.equals(Status.OK)) {
            w3cAnnotationHistory = serviceResponse.getObj();
            String urlStr = iriBuilderService.buildW3CAnnotationHistoryIri(collectionId, annotationId, w3cAnnotationHistory.getVersion());
            String dateStr = MEMENTO_DATE_FORMAT.format(w3cAnnotationHistory.getCreatedDateTime());
            outputMessage.getHeaders().add(HttpHeaders.LINK, String.format("<%s>; rel=\"next memento\"; datetime=\"%s\"", urlStr, dateStr));
        }
    }

    private void decoratePreviousVersionHeader(W3CAnnotationHistory w3cAnnotationHistory, HttpOutputMessage outputMessage) {

        String collectionId = w3cAnnotationHistory.getCollectionId();
        String annotationId = w3cAnnotationHistory.getAnnotationId();
        int version = w3cAnnotationHistory.getVersion();

        ServiceResponse<W3CAnnotationHistory> serviceResponse = w3cAnnotationHistoryService.getPreviousAnnotationVersion(collectionId, annotationId, version);
        Status status = serviceResponse.getStatus();

        if (status.equals(Status.OK)) {
            w3cAnnotationHistory = serviceResponse.getObj();
            String urlStr = iriBuilderService.buildW3CAnnotationHistoryIri(collectionId, annotationId, w3cAnnotationHistory.getVersion());
            String dateStr = MEMENTO_DATE_FORMAT.format(w3cAnnotationHistory.getCreatedDateTime());
            outputMessage.getHeaders().add(HttpHeaders.LINK, String.format("<%s>; rel=\"prev memento\"; datetime=\"%s\"", urlStr, dateStr));
        }
    }
}