package com.digirati.elucidate.common.model.annotation.oa;

import java.io.Serializable;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;

@SuppressWarnings("serial")
public class OAAnnotation extends AbstractAnnotation implements Serializable {

    @Override
    public String toString() {
        return "OAAnnotation [getAnnotationId()=" + getAnnotationId() + ", getCollectionId()=" + getCollectionId() + ", isDeleted()=" + isDeleted() + ", getCreatedDateTime()=" + getCreatedDateTime() + ", getModifiedDateTime()=" + getModifiedDateTime() + ", getCacheKey()=" + getCacheKey() + "]";
    }
}
