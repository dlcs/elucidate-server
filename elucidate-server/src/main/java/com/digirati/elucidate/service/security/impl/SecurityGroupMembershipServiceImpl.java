package com.digirati.elucidate.service.security.impl;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.common.service.IRIBuilderService;
import com.digirati.elucidate.infrastructure.security.Permission;
import com.digirati.elucidate.infrastructure.security.UserSecurityDetailsContext;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.ServiceResponse.Status;
import com.digirati.elucidate.model.annotation.AnnotationReference;
import com.digirati.elucidate.model.annotation.AnnotationReferenceCollection;
import com.digirati.elucidate.model.security.SecurityGroup;
import com.digirati.elucidate.model.security.SecurityUser;
import com.digirati.elucidate.model.security.SecurityUserReference;
import com.digirati.elucidate.repository.security.GroupMembershipRepository;
import com.digirati.elucidate.repository.security.UserRepository;
import com.digirati.elucidate.service.query.W3CAnnotationService;
import com.digirati.elucidate.service.security.SecurityGroupMembershipService;
import com.digirati.elucidate.service.security.SecurityGroupService;
import com.digirati.elucidate.service.security.SecurityUserReferenceCollection;
import java.util.List;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.BiConsumer;

@Service(SecurityGroupMembershipServiceImpl.SERVICE_NAME)
public class SecurityGroupMembershipServiceImpl implements SecurityGroupMembershipService {

    public static final String SERVICE_NAME = "securityGroupMembershipServiceImpl";

    private final UserSecurityDetailsContext securityContext;
    private final SecurityGroupService securityGroupService;
    private final W3CAnnotationService w3cAnnotationService;
    private final GroupMembershipRepository membershipRepository;
    private final UserRepository userRepository;
    private final IRIBuilderService iriBuilder;

    public SecurityGroupMembershipServiceImpl(
        UserSecurityDetailsContext securityContext,
        SecurityGroupService securityGroupService,
        W3CAnnotationService w3cAnnotationService,
        GroupMembershipRepository membershipRepository,
        UserRepository userRepository,
        IRIBuilderService iriBuilder) {

        this.securityContext = securityContext;
        this.securityGroupService = securityGroupService;
        this.w3cAnnotationService = w3cAnnotationService;
        this.membershipRepository = membershipRepository;
        this.userRepository = userRepository;
        this.iriBuilder = iriBuilder;
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
    public ServiceResponse<AnnotationReferenceCollection> getGroupAnnotations(String groupId) {
        ServiceResponse<SecurityGroup> groupRes = securityGroupService.getGroup(groupId);

        if (groupRes.getStatus() != Status.OK) {
            return new ServiceResponse<>(groupRes.getStatus());
        }

        SecurityGroup group = groupRes.getObj();

        if (!securityContext.isAuthorized(Permission.READ, group)) {
            return new ServiceResponse<>(Status.UNAUTHORIZED);
        }

        List<AnnotationReference> annotationRefs = membershipRepository.getAnnotationGroupMemberships(group.getPk());
        AnnotationReferenceCollection collection = new AnnotationReferenceCollection(annotationRefs);

        return new ServiceResponse<>(Status.OK, collection);
    }

    @Override
    public ServiceResponse<Void> addUserToGroup(String userId, String groupId) {
        return handleUserAndGroup(userId, groupId, membershipRepository::createUserGroupMembership);
    }

    @Override
    public ServiceResponse<Void> removeUserFromGroup(String userId, String groupId) {
        return handleUserAndGroup(userId, groupId, membershipRepository::removeUserGroupMembership);
    }

    @Override
    public ServiceResponse<SecurityUserReferenceCollection> getGroupUsers(String groupId) {
        ServiceResponse<SecurityGroup> groupRes = securityGroupService.getGroup(groupId);

        if (groupRes.getStatus() != Status.OK) {
            return new ServiceResponse<>(groupRes.getStatus());
        }

        SecurityGroup group = groupRes.getObj();

        if (!securityContext.isAuthorized(Permission.READ, group)) {
            return new ServiceResponse<>(Status.UNAUTHORIZED);
        }

        List<SecurityUserReference> users = membershipRepository.getUserGroupMemberships(group.getPk());
        SecurityUserReferenceCollection collection = new SecurityUserReferenceCollection(users);

        return new ServiceResponse<>(Status.OK, collection);
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

        if (!securityContext.isAuthorized(Permission.WRITE, annotation)
            || !securityContext.isAuthorized(Permission.WRITE, group)) {
            return new ServiceResponse<>(Status.UNAUTHORIZED, null);
        }

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

        if (!securityContext.isAuthorized(Permission.WRITE, group)) {
            return new ServiceResponse<>(Status.UNAUTHORIZED, null);
        }

        consumer.accept(user.getPk(), group.getPk());
        return new ServiceResponse<>(Status.OK, null);
    }
}
