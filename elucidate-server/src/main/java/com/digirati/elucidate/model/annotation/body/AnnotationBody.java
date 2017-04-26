package com.digirati.elucidate.model.annotation.body;

import java.io.Serializable;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;

@SuppressWarnings("serial")
public class AnnotationBody extends AbstractAnnotation implements Serializable {

    private String bodyIri;
    private String sourceIri;

    public String getBodyIri() {
        return bodyIri;
    }

    public void setBodyIri(String bodyIri) {
        this.bodyIri = bodyIri;
    }

    public String getSourceIri() {
        return sourceIri;
    }

    public void setSourceIri(String sourceIri) {
        this.sourceIri = sourceIri;
    }
}
