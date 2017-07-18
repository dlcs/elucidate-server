package com.digirati.elucidate.service.history;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;

public interface AnnotationHistoryService {

    public void recordAnnotationHistory(W3CAnnotation w3cAnnotation);
}
