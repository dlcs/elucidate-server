package com.digirati.elucidate.model.annotation.selector.textquote;

import java.io.Serializable;

import com.digirati.elucidate.model.annotation.selector.AbstractAnnotationSelector;

@SuppressWarnings("serial")
public class AnnotationTextQuoteSelector extends AbstractAnnotationSelector implements Serializable {

    private String exact;
    private String prefix;
    private String suffix;

    public String getExact() {
        return exact;
    }

    public void setExact(String exact) {
        this.exact = exact;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public String toString() {
        return "AnnotationTextQuoteSelector [getExact()=" + getExact() + ", getPrefix()=" + getPrefix() + ", getSuffix()=" + getSuffix() + ", getSelectorIri()=" + getSelectorIri() + ", getBodyiri()=" + getBodyiri() + ", getBodySourceIri()=" + getBodySourceIri() + ", getTargetIri()=" + getTargetIri() + ", getTargetSourceIri()=" + getTargetSourceIri() + ", getAnnotationId()=" + getAnnotationId() + ", getCollectionId()=" + getCollectionId() + ", getCacheKey()=" + getCacheKey() + ", getPk()=" + getPk() + ", getJsonMap()=" + getJsonMap() + ", isDeleted()=" + isDeleted() + ", getCreatedDateTime()=" + getCreatedDateTime() + ", getModifiedDateTime()=" + getModifiedDateTime() + "]";
    }
}
