package com.digirati.elucidate.web.converter.w3c.annotation;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.common.service.IRIBuilderService;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.ServiceResponse.Status;
import com.digirati.elucidate.model.annotation.history.W3CAnnotationHistory;
import com.digirati.elucidate.service.history.W3CAnnotationHistoryService;
import com.digirati.elucidate.web.converter.AbstractMessageConverter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;

public abstract class AbstractW3CAnnotationMessageConverter extends AbstractMessageConverter<W3CAnnotation> {

    private IRIBuilderService iriBuilderService;
    private W3CAnnotationHistoryService w3cAnnotationHistoryService;

    protected AbstractW3CAnnotationMessageConverter(IRIBuilderService iriBuilderService, W3CAnnotationHistoryService w3cAnnotationHistoryService, MediaType... supportedMediaTypes) {
        super(supportedMediaTypes);
        this.iriBuilderService = iriBuilderService;
        this.w3cAnnotationHistoryService = w3cAnnotationHistoryService;
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return W3CAnnotation.class.equals(clazz);
    }

    @Override
    protected void decorateHeaders(W3CAnnotation w3cAnnotation, HttpOutputMessage outputMessage) {
        outputMessage.getHeaders().add(HttpHeaders.ETAG, String.format("W/\"%s\"", w3cAnnotation.getCacheKey()));
        outputMessage.getHeaders().add(HttpHeaders.LINK, "<http://www.w3.org/ns/ldp#Resource>; rel=\"type\"");
        outputMessage.getHeaders().add(HttpHeaders.ALLOW, "PUT,GET,OPTIONS,HEAD,DELETE");
        outputMessage.getHeaders().add(HttpHeaders.VARY, "Accept");
        decorateMementoDatetimeHeader(w3cAnnotation, outputMessage);
        decoratePenultimateVersionHeader(w3cAnnotation, outputMessage);
    }

    private void decorateMementoDatetimeHeader(W3CAnnotation w3cAnnotation, HttpOutputMessage outputMessage) {

        String collectionId = w3cAnnotation.getCollectionId();
        String annotationId = w3cAnnotation.getAnnotationId();

        ServiceResponse<W3CAnnotationHistory> serviceResponse = w3cAnnotationHistoryService.getLatestAnnotationVersion(collectionId, annotationId);
        Status status = serviceResponse.getStatus();

        if (status.equals(Status.OK)) {
            W3CAnnotationHistory w3cAnnotationHistory = serviceResponse.getObj();
            String dateStr = MEMENTO_DATE_FORMAT.format(w3cAnnotationHistory.getCreatedDateTime());
            outputMessage.getHeaders().add("Memento-Datetime", dateStr);
        }
    }

    private void decoratePenultimateVersionHeader(W3CAnnotation w3cAnnotation, HttpOutputMessage outputMessage) {

        String collectionId = w3cAnnotation.getCollectionId();
        String annotationId = w3cAnnotation.getAnnotationId();

        ServiceResponse<W3CAnnotationHistory> serviceResponse = w3cAnnotationHistoryService.getPenultimateAnnotationVersion(collectionId, annotationId);
        Status status = serviceResponse.getStatus();

        if (status.equals(Status.OK)) {
            W3CAnnotationHistory w3cAnnotationHistory = serviceResponse.getObj();
            String urlStr = iriBuilderService.buildW3CAnnotationHistoryIri(collectionId, annotationId, w3cAnnotationHistory.getVersion());
            String dateStr = MEMENTO_DATE_FORMAT.format(w3cAnnotationHistory.getCreatedDateTime());
            outputMessage.getHeaders().add(HttpHeaders.LINK, String.format("<%s>; rel=\"prev memento\"; datetime=\"%s\"", urlStr, dateStr));
        }
    }
}
