package com.digirati.elucidate.model.security;

public final class SecurityUserReference {

    private final String uid;

    public SecurityUserReference(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return this.uid;
    }
}
