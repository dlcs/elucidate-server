package com.digirati.elucidate.infrastructure.exception;

import com.digirati.elucidate.model.ServiceResponse.Status;

@SuppressWarnings("serial")
public class InvalidServiceResponseException extends RuntimeException {

    public InvalidServiceResponseException(Status status) {
        super(String.format("Unexpected service response status [%s]", status));
    }
}
