package com.digirati.elucidate.repository.security;

import com.digirati.elucidate.model.annotation.AnnotationReference;
import com.digirati.elucidate.model.security.SecurityUserReference;
import java.util.List;

public interface GroupMembershipRepository {
    void createAnnotationGroupMembership(int annotationPk, int groupPk);

    void removeAnnotationGroupMembership(int annotationPk, int groupPk);

    List<AnnotationReference> getAnnotationGroupMemberships(int groupPk);

    void createUserGroupMembership(int userPk, int groupPk);

    void removeUserGroupMembership(int userPk, int groupPk);

    List<SecurityUserReference> getUserGroupMemberships(int groupPk);
}
