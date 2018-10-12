package com.digirati.elucidate.model.security;

public final class SecurityUserReference {

    private final String id;
    private final String uid;

    public SecurityUserReference(String id, String uid) {
        this.id = id;
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }
}
