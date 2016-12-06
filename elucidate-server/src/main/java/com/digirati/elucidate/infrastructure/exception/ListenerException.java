package com.digirati.elucidate.infrastructure.exception;

@SuppressWarnings("serial")
public class ListenerException extends RuntimeException {

    public ListenerException(String message, Throwable cause) {
        super(message, cause);
    }
}
