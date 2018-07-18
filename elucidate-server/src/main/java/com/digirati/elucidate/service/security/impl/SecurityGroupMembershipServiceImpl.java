package com.digirati.elucidate.service.security.impl;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.infrastructure.security.UserSecurityDetailsContext;
import com.digirati.elucidate.infrastructure.security.UserSecurityDetailsLoader;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.ServiceResponse.Status;
import com.digirati.elucidate.model.security.SecurityGroup;
import com.digirati.elucidate.model.security.SecurityUser;
import com.digirati.elucidate.repository.security.GroupMembershipRepository;
import com.digirati.elucidate.repository.security.UserRepository;
import com.digirati.elucidate.service.query.W3CAnnotationService;
import com.digirati.elucidate.service.security.SecurityGroupMembershipService;
import com.digirati.elucidate.service.security.SecurityGroupService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.BiConsumer;

@Service(SecurityGroupMembershipServiceImpl.SERVICE_NAME)
public class SecurityGroupMembershipServiceImpl implements SecurityGroupMembershipService {

    public static final String SERVICE_NAME = "securityGroupMembershipServiceImpl";

    private final UserSecurityDetailsContext securityContext;
    private final SecurityGroupService securityGroupService;
    private final W3CAnnotationService w3cAnnotationService;
    private UserSecurityDetailsLoader securityDetailsLoader;
    private GroupMembershipRepository membershipRepository;
    private UserRepository userRepository;

    public SecurityGroupMembershipServiceImpl(
        UserSecurityDetailsContext securityContext,
        SecurityGroupService securityGroupService,
        W3CAnnotationService w3cAnnotationService,
        GroupMembershipRepository membershipRepository,
        UserRepository userRepository) {

        this.securityContext = securityContext;
        this.securityGroupService = securityGroupService;
        this.w3cAnnotationService = w3cAnnotationService;
        this.membershipRepository = membershipRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ServiceResponse<Void> addAnnotationToGroup(String collectionId, String annotationId, String groupId) {
        return handleAnnotationAndGroup(
            collectionId,
            annotationId,
            groupId,
            membershipRepository::createAnnotationGroupMembership
        );
    }

    @Override
    public ServiceResponse<Void> removeAnnotationFromGroup(String collectionId, String annotationId, String groupId) {
        return handleAnnotationAndGroup(
            collectionId,
            annotationId,
            groupId,
            membershipRepository::removeAnnotationGroupMembership
        );
    }

    @Override
    public ServiceResponse<Void> addUserToGroup(String userId, String groupId) {
        return handleUserAndGroup(userId, groupId, membershipRepository::createUserGroupMembership);
    }

    @Override
    public ServiceResponse<Void> removeUserFromGroup(String userId, String groupId) {
        return handleUserAndGroup(userId, groupId, membershipRepository::removeUserGroupMembership);
    }

    private ServiceResponse<Void> handleAnnotationAndGroup(String collectionId, String annotationId, String groupId, BiConsumer<Integer, Integer> consumer) {
        ServiceResponse<SecurityGroup> groupRes = securityGroupService.getGroup(groupId);
        if (groupRes.getStatus() != Status.OK) {
            return new ServiceResponse<>(groupRes.getStatus(), null);
        }

        ServiceResponse<W3CAnnotation> annotationRes = w3cAnnotationService.getAnnotation(collectionId, annotationId);
        if (annotationRes.getStatus() != Status.OK) {
            return new ServiceResponse<>(annotationRes.getStatus(), null);
        }

        W3CAnnotation annotation = annotationRes.getObj();
        SecurityGroup group = groupRes.getObj();
        consumer.accept(annotation.getPk(), group.getPk());

        return new ServiceResponse<>(Status.OK, null);
    }

    private ServiceResponse<Void> handleUserAndGroup(String userId, String groupId, BiConsumer<Integer, Integer> consumer) {
        ServiceResponse<SecurityGroup> groupRes = securityGroupService.getGroup(groupId);

        if (groupRes.getStatus() != Status.OK) {
            return new ServiceResponse<>(groupRes.getStatus(), null);
        }

        Optional<SecurityUser> details = userRepository.getUserById(userId);
        if (!details.isPresent()) {
            return new ServiceResponse<>(Status.NOT_FOUND, null);
        }

        SecurityUser user = details.get();
        SecurityGroup group = groupRes.getObj();
        consumer.accept(user.getPk(), group.getPk());

        return new ServiceResponse<>(Status.OK, null);
    }
}
