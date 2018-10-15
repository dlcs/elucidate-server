package com.digirati.elucidate.repository.security;

import com.digirati.elucidate.model.security.SecurityGroup;

import java.util.List;
import java.util.Optional;

public interface GroupRepository {
    List<SecurityGroup> getGroupsByUserId(Integer userPk);

    SecurityGroup createGroup(Integer ownerPk, String groupId, String label);

    Optional<SecurityGroup> getGroup(String groupId);
}
