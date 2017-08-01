package com.digirati.elucidate.common.infrastructure.listener;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;

public interface RegisteredListener {

    void notifyCreate(W3CAnnotation w3cAnnotation);

    void notifyUpdate(W3CAnnotation w3cAnnotation);

    void notifyDelete(W3CAnnotation w3cAnnotation);

    boolean executeInParallel();
}
