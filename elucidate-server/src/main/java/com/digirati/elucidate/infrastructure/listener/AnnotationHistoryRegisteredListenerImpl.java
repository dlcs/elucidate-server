package com.digirati.elucidate.infrastructure.listener;

import com.digirati.elucidate.common.infrastructure.listener.RegisteredListener;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.service.history.W3CAnnotationHistoryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class AnnotationHistoryRegisteredListenerImpl implements RegisteredListener {

    private static final Logger LOGGER = Logger.getLogger(AnnotationHistoryRegisteredListenerImpl.class);

    private W3CAnnotationHistoryService w3cAnnotationHistoryService;

    @Autowired
    public AnnotationHistoryRegisteredListenerImpl(W3CAnnotationHistoryService w3cAnnotationHistoryService) {
        this.w3cAnnotationHistoryService = w3cAnnotationHistoryService;
    }

    @Override
    public void notifyCreate(W3CAnnotation w3cAnnotation) {
        LOGGER.info(String.format("Processing CREATE on W3CAnnotation [%s]", w3cAnnotation));
        w3cAnnotationHistoryService.recordAnnotationHistory(w3cAnnotation);
    }

    @Override
    public void notifyUpdate(W3CAnnotation w3cAnnotation) {
        LOGGER.info(String.format("Processing UPDATE on W3CAnnotation [%s]", w3cAnnotation));
        w3cAnnotationHistoryService.recordAnnotationHistory(w3cAnnotation);
    }

    @Override
    public void notifyDelete(W3CAnnotation w3cAnnotation) {
        LOGGER.info(String.format("Processing DELETE on W3CAnnotation [%s]", w3cAnnotation));
        w3cAnnotationHistoryService.deleteAnnotationHistory(w3cAnnotation);
    }

    @Override
    public boolean executeInParallel() {
        return false;
    }
}
