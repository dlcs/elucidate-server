package com.digirati.elucidate.model.annotation.selector.fragment;

import java.io.Serializable;

import com.digirati.elucidate.model.annotation.selector.AbstractAnnotationSelector;

@SuppressWarnings("serial")
public class AnnotationFragmentSelector extends AbstractAnnotationSelector implements Serializable {

    private String conformsTo;
    private String value;
    private Integer x;
    private Integer y;
    private Integer w;
    private Integer h;
    private Integer start;
    private Integer end;

    public String getConformsTo() {
        return conformsTo;
    }

    public void setConformsTo(String conformsTo) {
        this.conformsTo = conformsTo;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getW() {
        return w;
    }

    public void setW(Integer w) {
        this.w = w;
    }

    public Integer getH() {
        return h;
    }

    public void setH(Integer h) {
        this.h = h;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "AnnotationFragmentSelector [getConformsTo()=" + getConformsTo() + ", getValue()=" + getValue() + ", getX()=" + getX() + ", getY()=" + getY() + ", getW()=" + getW() + ", getH()=" + getH() + ", getStart()=" + getStart() + ", getEnd()=" + getEnd() + ", getSelectorIri()=" + getSelectorIri() + ", getBodyiri()=" + getBodyiri() + ", getBodySourceIri()=" + getBodySourceIri() + ", getTargetIri()=" + getTargetIri() + ", getTargetSourceIri()=" + getTargetSourceIri() + ", getAnnotationId()=" + getAnnotationId() + ", getCollectionId()=" + getCollectionId() + ", getCacheKey()=" + getCacheKey() + ", getPk()=" + getPk() + ", getJsonMap()=" + getJsonMap() + ", isDeleted()=" + isDeleted() + ", getCreatedDateTime()=" + getCreatedDateTime() + ", getModifiedDateTime()=" + getModifiedDateTime() + "]";
    }
}
