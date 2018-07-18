package com.digirati.elucidate.service.security;

import com.digirati.elucidate.model.ServiceResponse;

public interface SecurityGroupMembershipService {
    ServiceResponse<Void> addAnnotationToGroup(String collectionId, String annotationId, String groupId);

    ServiceResponse<Void> removeAnnotationFromGroup(String collectionId, String annotationId, String groupId);

    ServiceResponse<Void> addUserToGroup(String userId, String groupId);

    ServiceResponse<Void> removeUserFromGroup(String userId, String groupId);
}
