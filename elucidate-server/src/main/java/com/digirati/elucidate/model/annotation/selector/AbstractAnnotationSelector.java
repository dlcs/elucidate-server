package com.digirati.elucidate.model.annotation.selector;

import java.io.Serializable;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;

@SuppressWarnings("serial")
public abstract class AbstractAnnotationSelector extends AbstractAnnotation implements Serializable {

    private String selectorIri;
    private String bodyiri;
    private String bodySourceIri;
    private String targetIri;
    private String targetSourceIri;

    public String getSelectorIri() {
        return selectorIri;
    }

    public void setSelectorIri(String selectorIri) {
        this.selectorIri = selectorIri;
    }

    public String getBodyiri() {
        return bodyiri;
    }

    public void setBodyiri(String bodyiri) {
        this.bodyiri = bodyiri;
    }

    public String getBodySourceIri() {
        return bodySourceIri;
    }

    public void setBodySourceIri(String bodySourceIri) {
        this.bodySourceIri = bodySourceIri;
    }

    public String getTargetIri() {
        return targetIri;
    }

    public void setTargetIri(String targetIri) {
        this.targetIri = targetIri;
    }

    public String getTargetSourceIri() {
        return targetSourceIri;
    }

    public void setTargetSourceIri(String targetSourceIri) {
        this.targetSourceIri = targetSourceIri;
    }
}
