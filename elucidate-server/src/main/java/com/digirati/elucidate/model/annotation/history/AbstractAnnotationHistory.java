package com.digirati.elucidate.model.annotation.history;

import com.digirati.elucidate.common.model.annotation.AbstractObject;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class AbstractAnnotationHistory extends AbstractObject implements Serializable {

    private String annotationId;
    private String collectionId;
    private Integer version;

    public String getAnnotationId() {
        return annotationId;
    }

    public void setAnnotationId(String annotationId) {
        this.annotationId = annotationId;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
