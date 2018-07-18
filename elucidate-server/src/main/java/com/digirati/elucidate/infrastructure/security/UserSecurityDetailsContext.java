package com.digirati.elucidate.infrastructure.security;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;
import com.digirati.elucidate.model.security.SecurityGroup;
import com.digirati.elucidate.model.security.SecurityUser;

public interface UserSecurityDetailsContext {
    /**
     * Check if the current security principal in the {@link org.springframework.security.core.context.SecurityContext}
     * has access to the read/write {@code operation} on the given {@code annotation}.
     *
     * @param operation The operation being authorized.
     * @param annotation The object the operation is being authorized on.
     * @return {@code true} iff the operation was authorized.
     */
    boolean isAuthorized(Permission operation, AbstractAnnotation annotation);

    /**
     * Check if the current security principal in the {@link org.springframework.security.core.context.SecurityContext}
     * has access to the read/write {@code operation} on the given {@code annotation}.
     *
     * @param operation The operation being authorized.
     * @param group The object the operation is being authorized on.
     * @return {@code true} iff the operation was authorized.
     */
    boolean isAuthorized(Permission operation, SecurityGroup group);

    /**
     * Get the {@code id} of the {@link SecurityUser} represented by the current security context, if any.
     *
     * @return An optional user authentication ID.
     */
    Integer getAuthenticationId();
}
