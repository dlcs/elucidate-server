package com.digirati.elucidate.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ValidationError implements Serializable {

    private String jsonError;

    public String getJsonError() {
        return jsonError;
    }

    public void setJsonError(String jsonError) {
        this.jsonError = jsonError;
    }
}
