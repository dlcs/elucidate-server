package com.digirati.elucidate.service.extractor;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;

public interface AnnotationExtractorService {

    public void processAnnotationCreate(W3CAnnotation w3cAnnotation);

    public void processAnnotationUpdate(W3CAnnotation w3CAnnotation);

    public void processAnnotationDelete(W3CAnnotation w3CAnnotation);
}
