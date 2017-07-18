package com.digirati.elucidate.infrastructure.listener;

import com.digirati.elucidate.common.infrastructure.listener.RegisteredListener;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.service.history.AnnotationHistoryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class AnnotationHistoryRegisteredListenerImpl implements RegisteredListener {

    private static final Logger LOGGER = Logger.getLogger(AnnotationHistoryRegisteredListenerImpl.class);

    private AnnotationHistoryService annotationHistoryService;

    @Autowired
    public AnnotationHistoryRegisteredListenerImpl(AnnotationHistoryService annotationHistoryService) {
        this.annotationHistoryService = annotationHistoryService;
    }

    @Override
    public void notifyCreate(W3CAnnotation w3cAnnotation) {
        LOGGER.info(String.format("Processing CREATE on W3CAnnotation [%s]", w3cAnnotation));
        annotationHistoryService.recordAnnotationHistory(w3cAnnotation);
    }

    @Override
    public void notifyUpdate(W3CAnnotation w3cAnnotation) {
        LOGGER.info(String.format("Processing UPDATE on W3CAnnotation [%s]", w3cAnnotation));
        annotationHistoryService.recordAnnotationHistory(w3cAnnotation);
    }

    @Override
    public void notifyDelete(W3CAnnotation w3cAnnotation) {
        LOGGER.info(String.format("Ignoring DELETE on W3CAnnotation [%s]", w3cAnnotation));
    }
}
