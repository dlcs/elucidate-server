package com.digirati.elucidate.common.model.annotation.w3c;

import java.io.Serializable;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotationPage;

@SuppressWarnings("serial")
public class W3CAnnotationPage extends AbstractAnnotationPage implements Serializable {

    @Override
    public String toString() {
        return "W3CAnnotationPage [getCollectionId()=" + getCollectionId() + ", isDeleted()=" + isDeleted() + ", getCreatedDateTime()=" + getCreatedDateTime() + ", getModifiedDateTime()=" + getModifiedDateTime() + ", getCacheKey()=" + getCacheKey() + "]";
    }
}
