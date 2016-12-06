package com.digirati.elucidate.common.infrastructure.exception;

@SuppressWarnings("serial")
public class AWSSQSException extends RuntimeException {

    public AWSSQSException(String message) {
        super(message);
    }

    public AWSSQSException(String message, Throwable cause) {
        super(message, cause);
    }
}
