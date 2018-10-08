package com.digirati.elucidate.infrastructure.security.impl;


import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;
import com.digirati.elucidate.infrastructure.security.Permission;
import com.digirati.elucidate.infrastructure.security.UserSecurityDetails;
import com.digirati.elucidate.infrastructure.security.UserSecurityDetailsContext;
import com.digirati.elucidate.model.security.SecurityGroup;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

public class JwtUserSecurityDetailsContext implements UserSecurityDetailsContext {

    private static final GrantedAuthority ROLE_ADMIN = new SimpleGrantedAuthority("admin");

    @Override
    public boolean isAuthorized(Permission operation, AbstractAnnotation annotation) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserSecurityDetails details = (UserSecurityDetails) auth.getPrincipal();
        Collection<? extends GrantedAuthority> roles = auth.getAuthorities();

        if (roles.contains(ROLE_ADMIN) || details.getUser().getPk() == annotation.getOwnerId()) {
            return true;
        }

        return operation == Permission.READ && details.hasAnyGroup(annotation.getGroups());
    }

    @Override
    public boolean isAuthorized(Permission operation, SecurityGroup group) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> roles = auth.getAuthorities();
        UserSecurityDetails details = (UserSecurityDetails) auth.getPrincipal();

        if (roles.contains(ROLE_ADMIN) || group.getOwnerId() == details.getUser().getPk()) {
            return true;
        }

        return operation == Permission.READ && details.hasGroup(group.getPk());
    }

    @Override
    public Integer getAuthenticationId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new IllegalStateException("No authentication present in SecurityContext");
        }

        UserSecurityDetails details = (UserSecurityDetails) auth.getPrincipal();
        return details.getUser().getPk();
    }
}
