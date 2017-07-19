package com.digirati.elucidate.service.extractor;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;

public interface AnnotationExtractorService {

    void processAnnotationCreate(W3CAnnotation w3cAnnotation);

    void processAnnotationUpdate(W3CAnnotation w3CAnnotation);

    void processAnnotationDelete(W3CAnnotation w3CAnnotation);
}
