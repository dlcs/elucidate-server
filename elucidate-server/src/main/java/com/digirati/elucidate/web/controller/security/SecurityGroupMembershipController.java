package com.digirati.elucidate.web.controller.security;

import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.service.security.SecurityGroupMembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityGroupMembershipController {
    private static final String VARIABLE_COLLECTION_ID = "collectionId";
    private static final String VARIABLE_ANNOTATION_ID = "annotationId";
    private static final String VARIABLE_GROUP_ID = "groupId";
    private static final String VARIABLE_USER_ID = "userId";
    private static final String ANNOTATION_REQUEST_PATH = "/group/{" + VARIABLE_GROUP_ID + "}/annotation/{" + VARIABLE_COLLECTION_ID + "}/{" + VARIABLE_ANNOTATION_ID + "}";
    private static final String USER_REQUEST_PATH = "/group/{" + VARIABLE_GROUP_ID + "}/user/{" + VARIABLE_USER_ID + "}";
    private SecurityGroupMembershipService groupMembershipService;

    @Autowired
    public SecurityGroupMembershipController(SecurityGroupMembershipService groupMembershipService) {
        this.groupMembershipService = groupMembershipService;
    }

    @PostMapping(ANNOTATION_REQUEST_PATH)
    public ResponseEntity<Void> addAnnotationToGroup(
        @PathVariable(VARIABLE_GROUP_ID) String groupId,
        @PathVariable(VARIABLE_COLLECTION_ID) String collectionId,
        @PathVariable(VARIABLE_ANNOTATION_ID) String annotationId) {

        ServiceResponse<Void> response = groupMembershipService.addAnnotationToGroup(
            collectionId,
            annotationId,
            groupId
        );

        return convertServiceToHttpResponse(response);
    }

    @DeleteMapping(ANNOTATION_REQUEST_PATH)
    public ResponseEntity<Void> removeAnnotationFromGroup(
        @PathVariable(VARIABLE_GROUP_ID) String groupId,
        @PathVariable(VARIABLE_COLLECTION_ID) String collectionId,
        @PathVariable(VARIABLE_ANNOTATION_ID) String annotationId) {

        ServiceResponse<Void> response = groupMembershipService.removeAnnotationFromGroup(
            collectionId,
            annotationId,
            groupId
        );

        return convertServiceToHttpResponse(response);
    }

    @PostMapping(USER_REQUEST_PATH)
    public ResponseEntity<Void> addUserToGroup(
        @PathVariable(VARIABLE_GROUP_ID) String groupId,
        @PathVariable(VARIABLE_USER_ID) String userId) {

        return convertServiceToHttpResponse(groupMembershipService.addUserToGroup(userId, groupId));
    }

    @DeleteMapping(USER_REQUEST_PATH)
    public ResponseEntity<Void> removeUserFromGroup(
        @PathVariable(VARIABLE_GROUP_ID) String groupId,
        @PathVariable(VARIABLE_USER_ID) String userId) {

        return convertServiceToHttpResponse(groupMembershipService.removeUserFromGroup(userId, groupId));
    }

    private ResponseEntity<Void> convertServiceToHttpResponse(ServiceResponse<Void> response) {
        switch (response.getStatus()) {
            case NOT_FOUND:
                return ResponseEntity.notFound().build();
            case UNAUTHORIZED:
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            case DELETED:
                return ResponseEntity.status(HttpStatus.GONE).build();
        }

        return ResponseEntity.ok(response.getObj());
    }

}
