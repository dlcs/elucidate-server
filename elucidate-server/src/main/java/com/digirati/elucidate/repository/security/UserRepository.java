package com.digirati.elucidate.repository.security;

import com.digirati.elucidate.model.security.SecurityUser;

import java.util.Optional;

public interface UserRepository {
    Optional<SecurityUser> getUser(String uid);

    Optional<SecurityUser> getUserById(String id);

    SecurityUser createUser(String id, String uid);
}
