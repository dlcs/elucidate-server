package com.digirati.elucidate.model.annotation.temporal;

import com.digirati.elucidate.common.model.annotation.AbstractObject;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class AnnotationTemporal extends AbstractObject implements Serializable {

    private String type;
    private Date value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getValue() {
        return value;
    }

    public void setValue(Date value) {
        this.value = value;
    }
}
