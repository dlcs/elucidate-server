package com.digirati.elucidate.common.model.annotation.w3c;

import java.io.Serializable;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotationCollection;

@SuppressWarnings("serial")
public class W3CAnnotationCollection extends AbstractAnnotationCollection implements Serializable {

    @Override
    public String toString() {
        return "W3CAnnotationCollection [getCollectionId()=" + getCollectionId() + ", isDeleted()=" + isDeleted() + ", getCreatedDateTime()=" + getCreatedDateTime() + ", getModifiedDateTime()=" + getModifiedDateTime() + ", getCacheKey()=" + getCacheKey() + "]";
    }
}
