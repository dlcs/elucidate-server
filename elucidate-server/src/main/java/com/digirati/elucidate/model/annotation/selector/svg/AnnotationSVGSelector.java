package com.digirati.elucidate.model.annotation.selector.svg;

import java.io.Serializable;

import com.digirati.elucidate.model.annotation.selector.AbstractAnnotationSelector;

@SuppressWarnings("serial")
public class AnnotationSVGSelector extends AbstractAnnotationSelector implements Serializable {

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "AnnotationSVGSelector [getValue()=" + getValue() + ", getSelectorIri()=" + getSelectorIri() + ", getBodyiri()=" + getBodyiri() + ", getBodySourceIri()=" + getBodySourceIri() + ", getTargetIri()=" + getTargetIri() + ", getTargetSourceIri()=" + getTargetSourceIri() + ", getAnnotationId()=" + getAnnotationId() + ", getCollectionId()=" + getCollectionId() + ", getCacheKey()=" + getCacheKey() + ", getPk()=" + getPk() + ", getJsonMap()=" + getJsonMap() + ", isDeleted()=" + isDeleted() + ", getCreatedDateTime()=" + getCreatedDateTime() + ", getModifiedDateTime()=" + getModifiedDateTime() + "]";
    }
}
