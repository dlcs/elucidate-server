package com.digirati.elucidate.model.annotation.targets;

import java.io.Serializable;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;

@SuppressWarnings("serial")
public class AnnotationTarget extends AbstractAnnotation implements Serializable {

    private String targetIri;
    private String sourceIri;

    public String getTargetIri() {
        return targetIri;
    }

    public void setTargetIri(String targetIri) {
        this.targetIri = targetIri;
    }

    public String getSourceIri() {
        return sourceIri;
    }

    public void setSourceIri(String sourceIri) {
        this.sourceIri = sourceIri;
    }

    @Override
    public String toString() {
        return "AnnotationTarget [getTargetIri()=" + getTargetIri() + ", getSourceIri()=" + getSourceIri() + ", getAnnotationId()=" + getAnnotationId() + ", getCollectionId()=" + getCollectionId() + ", getCacheKey()=" + getCacheKey() + ", getPk()=" + getPk() + ", getJsonMap()=" + getJsonMap() + ", isDeleted()=" + isDeleted() + ", getCreatedDateTime()=" + getCreatedDateTime() + ", getModifiedDateTime()=" + getModifiedDateTime() + "]";
    }
}
