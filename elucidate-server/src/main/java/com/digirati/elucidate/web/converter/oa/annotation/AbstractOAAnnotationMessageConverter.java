package com.digirati.elucidate.web.converter.oa.annotation;

import com.digirati.elucidate.common.model.annotation.oa.OAAnnotation;
import com.digirati.elucidate.common.service.IRIBuilderService;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.ServiceResponse.Status;
import com.digirati.elucidate.model.annotation.history.OAAnnotationHistory;
import com.digirati.elucidate.service.history.OAAnnotationHistoryService;
import com.digirati.elucidate.web.converter.AbstractMessageConverter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;

public abstract class AbstractOAAnnotationMessageConverter extends AbstractMessageConverter<OAAnnotation> {

    private IRIBuilderService iriBuilderService;
    private OAAnnotationHistoryService oaAnnotationHistoryService;

    protected AbstractOAAnnotationMessageConverter(IRIBuilderService iriBuilderService, OAAnnotationHistoryService oaAnnotationHistoryService, MediaType... supportedMediaTypes) {
        super(supportedMediaTypes);
        this.iriBuilderService = iriBuilderService;
        this.oaAnnotationHistoryService = oaAnnotationHistoryService;
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return OAAnnotation.class.equals(clazz);
    }

    @Override
    protected void decorateHeaders(OAAnnotation oaAnnotation, HttpOutputMessage outputMessage) {
        outputMessage.getHeaders().add(HttpHeaders.ETAG, String.format("W/\"%s\"", oaAnnotation.getCacheKey()));
        outputMessage.getHeaders().add(HttpHeaders.LINK, "<http://www.w3.org/ns/ldp#Resource>; rel=\"type\"");
        outputMessage.getHeaders().add(HttpHeaders.ALLOW, "PUT,GET,OPTIONS,HEAD,DELETE");
        outputMessage.getHeaders().add(HttpHeaders.VARY, "Accept");
        decorateMementoDatetimeHeader(oaAnnotation, outputMessage);
        decoratePenultimateVersionHeader(oaAnnotation, outputMessage);
    }

    private void decorateMementoDatetimeHeader(OAAnnotation oaAnnotation, HttpOutputMessage outputMessage) {

        String collectionId = oaAnnotation.getCollectionId();
        String annotationId = oaAnnotation.getAnnotationId();

        ServiceResponse<OAAnnotationHistory> serviceResponse = oaAnnotationHistoryService.getLatestAnnotationVersion(collectionId, annotationId);
        Status status = serviceResponse.getStatus();

        if (status.equals(Status.OK)) {
            OAAnnotationHistory oaAnnotationHistory = serviceResponse.getObj();
            String dateStr = MEMENTO_DATE_FORMAT.format(oaAnnotationHistory.getCreatedDateTime());
            outputMessage.getHeaders().add("Memento-Datetime", dateStr);
        }
    }

    private void decoratePenultimateVersionHeader(OAAnnotation oaAnnotation, HttpOutputMessage outputMessage) {

        String collectionId = oaAnnotation.getCollectionId();
        String annotationId = oaAnnotation.getAnnotationId();

        ServiceResponse<OAAnnotationHistory> serviceResponse = oaAnnotationHistoryService.getPenultimateAnnotationVersion(collectionId, annotationId);
        Status status = serviceResponse.getStatus();

        if (status.equals(Status.OK)) {
            OAAnnotationHistory oaAnnotationHistory = serviceResponse.getObj();
            String urlStr = iriBuilderService.buildOAAnnotationHistoryIri(collectionId, annotationId, oaAnnotationHistory.getVersion());
            String dateStr = MEMENTO_DATE_FORMAT.format(oaAnnotationHistory.getCreatedDateTime());
            outputMessage.getHeaders().add(HttpHeaders.LINK, String.format("<%s>; rel=\"prev memento\"; datetime=\"%s\"", urlStr, dateStr));
        }
    }
}
