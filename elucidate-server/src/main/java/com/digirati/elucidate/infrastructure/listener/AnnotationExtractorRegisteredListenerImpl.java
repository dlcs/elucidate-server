package com.digirati.elucidate.infrastructure.listener;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.digirati.elucidate.common.infrastructure.listener.RegisteredListener;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.service.extractor.AnnotationExtractorService;

public class AnnotationExtractorRegisteredListenerImpl implements RegisteredListener {

    private static final Logger LOGGER = Logger.getLogger(AnnotationExtractorRegisteredListenerImpl.class);

    private AnnotationExtractorService annotationExtractorService;

    @Autowired
    public AnnotationExtractorRegisteredListenerImpl(AnnotationExtractorService annotationExtractorService) {
        this.annotationExtractorService = annotationExtractorService;
    }

    @Override
    public void notifyCreate(W3CAnnotation w3cAnnotation) {
        LOGGER.info(String.format("Processing CREATE on W3CAnnotation [%s]", w3cAnnotation));
        annotationExtractorService.processAnnotationCreate(w3cAnnotation);
    }

    @Override
    public void notifyUpdate(W3CAnnotation w3cAnnotation) {
        LOGGER.info(String.format("Processing UPDATE on W3CAnnotation [%s]", w3cAnnotation));
        annotationExtractorService.processAnnotationUpdate(w3cAnnotation);
    }

    @Override
    public void notifyDelete(W3CAnnotation w3cAnnotation) {
        LOGGER.info(String.format("Processing DELETE on W3CAnnotation [%s]", w3cAnnotation));
        annotationExtractorService.processAnnotationDelete(w3cAnnotation);
    }

    @Override
    public boolean executeInParallel() {
        return true;
    }
}
