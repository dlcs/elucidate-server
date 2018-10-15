package com.digirati.elucidate.service.security;

import com.digirati.elucidate.model.security.SecurityUserReference;
import java.util.List;

public final class SecurityUserReferenceCollection {

    private final List<SecurityUserReference> users;

    public SecurityUserReferenceCollection(List<SecurityUserReference> users) {
        this.users = users;
    }

    public List<SecurityUserReference> getUsers() {
        return users;
    }
}
