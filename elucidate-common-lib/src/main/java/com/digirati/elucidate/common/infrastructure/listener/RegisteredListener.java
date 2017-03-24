package com.digirati.elucidate.common.infrastructure.listener;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;

public interface RegisteredListener {

    public void notifyCreate(W3CAnnotation w3cAnnotation);

    public void notifyUpdate(W3CAnnotation w3cAnnotation);

    public void notifyDelete(W3CAnnotation w3cAnnotation);
}
