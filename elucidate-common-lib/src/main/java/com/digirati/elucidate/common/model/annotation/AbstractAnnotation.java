package com.digirati.elucidate.common.model.annotation;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class AbstractAnnotation extends AbstractObject implements Serializable {

    private String annotationId;

    public String getAnnotationId() {
        return annotationId;
    }

    public void setAnnotationId(String annotationId) {
        this.annotationId = annotationId;
    }
}
