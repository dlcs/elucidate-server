package com.digirati.elucidate.common.model.annotation;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class AbstractAnnotationCollection extends AbstractObject implements Serializable {

    private String collectionId;
    private String cacheKey;

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }
}
