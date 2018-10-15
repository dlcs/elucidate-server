package com.digirati.elucidate.infrastructure.security.impl;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;
import com.digirati.elucidate.infrastructure.security.Permission;
import com.digirati.elucidate.infrastructure.security.UserSecurityDetailsContext;
import com.digirati.elucidate.model.security.SecurityGroup;

public class DefaultUserSecurityDetailsContext implements UserSecurityDetailsContext {
    @Override
    public boolean isAuthorized(Permission operation, AbstractAnnotation annotation) {
        return true;
    }

    @Override
    public boolean isAuthorized(Permission operation, SecurityGroup group) {
        return true;
    }

    @Override
    public Integer getAuthenticationId() {
        return null;
    }
}
