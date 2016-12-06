package com.digirati.elucidate.common.infrastructure.exception;

@SuppressWarnings("serial")
public class InvalidIRIException extends RuntimeException {

    public InvalidIRIException(String message, Throwable cause) {
        super(message, cause);
    }
}
