package com.digirati.elucidate.service.security;

import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.annotation.AnnotationReferenceCollection;

public interface SecurityGroupMembershipService {
    ServiceResponse<Void> addAnnotationToGroup(String collectionId, String annotationId, String groupId);

    ServiceResponse<Void> removeAnnotationFromGroup(String collectionId, String annotationId, String groupId);

    ServiceResponse<AnnotationReferenceCollection> getGroupAnnotations(String groupId);

    ServiceResponse<Void> addUserToGroup(String userId, String groupId);

    ServiceResponse<Void> removeUserFromGroup(String userId, String groupId);

    ServiceResponse<SecurityUserReferenceCollection> getGroupUsers(String groupId);
}
