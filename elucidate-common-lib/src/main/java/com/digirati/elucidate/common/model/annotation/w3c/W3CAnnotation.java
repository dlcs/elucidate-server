package com.digirati.elucidate.common.model.annotation.w3c;

import java.io.Serializable;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;

@SuppressWarnings("serial")
public class W3CAnnotation extends AbstractAnnotation implements Serializable {

    @Override
    public String toString() {
        return "W3CAnnotation [getAnnotationId()=" + getAnnotationId() + ", getCollectionId()=" + getCollectionId() + ", isDeleted()=" + isDeleted() + ", getCreatedDateTime()=" + getCreatedDateTime() + ", getModifiedDateTime()=" + getModifiedDateTime() + ", getCacheKey()=" + getCacheKey() + "]";
    }
}
