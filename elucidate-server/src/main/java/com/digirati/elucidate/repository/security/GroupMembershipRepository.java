package com.digirati.elucidate.repository.security;

public interface GroupMembershipRepository {
    void createAnnotationGroupMembership(int annotationPk, int groupPk);

    void removeAnnotationGroupMembership(int annotationPk, int groupPk);

    void createUserGroupMembership(int userPk, int groupPk);

    void removeUserGroupMembership(int userPk, int groupPk);
}
