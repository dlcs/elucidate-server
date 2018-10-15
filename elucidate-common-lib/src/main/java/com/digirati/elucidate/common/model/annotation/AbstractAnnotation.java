package com.digirati.elucidate.common.model.annotation;

import java.io.Serializable;
import java.util.Set;

@SuppressWarnings("serial")
public abstract class AbstractAnnotation extends AbstractObject implements Serializable {

    private String annotationId;
    private String collectionId;
    private String cacheKey;
    private int ownerId;
    private Set<Integer> groups;

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

    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    public int getOwnerId() {
        return this.ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public Set<Integer> getGroups() {
        return this.groups;
    }

    public void setGroups(Set<Integer> groups) {
        this.groups = groups;
    }
}
