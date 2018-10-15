package com.digirati.elucidate.infrastructure.security;

import java.util.Optional;

/**
 * Security detail loader for users that have already been authenticated
 * by an authorization server.
 */
public interface UserSecurityDetailsLoader {

    UserSecurityDetails createUser(String username);

    Optional<UserSecurityDetails> getUser(String username);

    /**
     * Get the security details for the user identified by the given
     * unique username.
     *
     * @param username The unique id of the user.
     * @return The details for the given user.
     */
    default UserSecurityDetails findOrCreateUserDetails(String username) {
        return getUser(username).orElseGet(() -> createUser(username));
    }
}
