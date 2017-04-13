package com.digirati.elucidate.model.annotation.selector.dataposition;

import java.io.Serializable;

import com.digirati.elucidate.model.annotation.selector.AbstractAnnotationSelector;

@SuppressWarnings("serial")
public class AnnotationDataPositionSelector extends AbstractAnnotationSelector implements Serializable {

    private Integer start;
    private Integer end;

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
        return "AnnotationDataPositionSelector [getStart()=" + getStart() + ", getEnd()=" + getEnd() + ", getSelectorIri()=" + getSelectorIri() + ", getBodyiri()=" + getBodyiri() + ", getBodySourceIri()=" + getBodySourceIri() + ", getTargetIri()=" + getTargetIri() + ", getTargetSourceIri()=" + getTargetSourceIri() + ", getAnnotationId()=" + getAnnotationId() + ", getCollectionId()=" + getCollectionId() + ", getCacheKey()=" + getCacheKey() + ", getPk()=" + getPk() + ", getJsonMap()=" + getJsonMap() + ", isDeleted()=" + isDeleted() + ", getCreatedDateTime()=" + getCreatedDateTime() + ", getModifiedDateTime()=" + getModifiedDateTime() + "]";
    }
}
