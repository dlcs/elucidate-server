package com.digirati.elucidate.infrastructure.exception;

@SuppressWarnings("serial")
public class InvalidAnnotationException extends RuntimeException {

    private final String errorJson;

    public InvalidAnnotationException(String message, String errorJson) {
        super(String.format("[%s]: [%s]", message, errorJson));
        this.errorJson = errorJson;
    }

    public String getErrorJson() {
        return errorJson;
    }
}
