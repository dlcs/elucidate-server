package com.digirati.elucidate.common.model.annotation.oa;

import java.io.Serializable;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotationPage;

@SuppressWarnings("serial")
public class OAAnnotationPage extends AbstractAnnotationPage implements Serializable {

    @Override
    public String toString() {
        return "OAAnnotationPage [getPk()=" + getPk() + ", getJsonMap()=" + getJsonMap() + ", isDeleted()=" + isDeleted() + ", getCreatedDateTime()=" + getCreatedDateTime() + ", getModifiedDateTime()=" + getModifiedDateTime() + "]";
    }
}
