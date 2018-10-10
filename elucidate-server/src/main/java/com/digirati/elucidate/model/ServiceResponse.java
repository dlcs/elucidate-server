package com.digirati.elucidate.model;

public class ServiceResponse<T> {

    public enum Status {
        OK, NOT_FOUND, CACHE_KEY_MISS, ILLEGAL_MODIFICATION, NON_CONFORMANT, DELETED, ALREADY_EXISTS,
        UNAUTHORIZED
    }

    private final Status status;
    private final T obj;

    public ServiceResponse(Status status) {
        this(status, null);
    }

    public ServiceResponse(Status status, T obj) {
        this.status = status;
        this.obj = obj;
    }

    public Status getStatus() {
        return status;
    }

    public T getObj() {
        return obj;
    }
}
