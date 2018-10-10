package com.digirati.elucidate.web.controller.security;

import com.digirati.elucidate.infrastructure.config.condition.IsAuthEnabled;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.annotation.AnnotationReferenceCollection;
import com.digirati.elucidate.service.security.SecurityGroupMembershipService;
import com.digirati.elucidate.service.security.SecurityUserReferenceCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(SecurityGroupMembershipController.CONTROLLER_NAME)
@RequestMapping("/group")
@Conditional(IsAuthEnabled.class)
public class SecurityGroupMembershipController {

    public static final String CONTROLLER_NAME = "securityGroupMembershipController";

    private static final String VARIABLE_COLLECTION_ID = "collectionId";
    private static final String VARIABLE_ANNOTATION_ID = "annotationId";
    private static final String VARIABLE_GROUP_ID = "groupId";
    private static final String VARIABLE_USER_ID = "userId";
    private static final String ANNOTATION_REQUEST_PATH = "/{" + VARIABLE_GROUP_ID + "}/annotation/{" + VARIABLE_COLLECTION_ID + "}/{" + VARIABLE_ANNOTATION_ID + "}";
    private static final String USER_REQUEST_PATH = "/{" + VARIABLE_GROUP_ID + "}/user/{" + VARIABLE_USER_ID + "}";

    private static final String GROUP_USERS_REQUEST_PATH = "/{" + VARIABLE_GROUP_ID + "}/users";
    private static final String GROUP_ANNOTATIONS_REQUEST_PATH = "/{" + VARIABLE_GROUP_ID + "}/annotations";

    private final SecurityGroupMembershipService groupMembershipService;

    @Autowired
    public SecurityGroupMembershipController(SecurityGroupMembershipService groupMembershipService) {
        this.groupMembershipService = groupMembershipService;
    }

    @GetMapping(GROUP_ANNOTATIONS_REQUEST_PATH)
    @ResponseBody
    public ResponseEntity<AnnotationReferenceCollection> getGroupAnnotations(@PathVariable(VARIABLE_GROUP_ID) String groupId) {
        ServiceResponse<AnnotationReferenceCollection> response = groupMembershipService.getGroupAnnotations(groupId);
        return convertServiceToHttpResponse(response);
    }

    @PostMapping(ANNOTATION_REQUEST_PATH)
    public ResponseEntity<Void> addAnnotationToGroup(@PathVariable(VARIABLE_GROUP_ID) String groupId, @PathVariable(VARIABLE_COLLECTION_ID) String collectionId, @PathVariable(VARIABLE_ANNOTATION_ID) String annotationId) {
        ServiceResponse<Void> response = groupMembershipService.addAnnotationToGroup(collectionId, annotationId, groupId);
        return convertServiceToHttpResponse(response);
    }

    @DeleteMapping(ANNOTATION_REQUEST_PATH)
    public ResponseEntity<Void> removeAnnotationFromGroup(@PathVariable(VARIABLE_GROUP_ID) String groupId, @PathVariable(VARIABLE_COLLECTION_ID) String collectionId, @PathVariable(VARIABLE_ANNOTATION_ID) String annotationId) {
        ServiceResponse<Void> response = groupMembershipService.removeAnnotationFromGroup(collectionId, annotationId, groupId);
        return convertServiceToHttpResponse(response);
    }

    @GetMapping(GROUP_USERS_REQUEST_PATH)
    @ResponseBody
    public ResponseEntity<SecurityUserReferenceCollection> getGroupUsers(@PathVariable(VARIABLE_GROUP_ID) String groupId) {
        ServiceResponse<SecurityUserReferenceCollection> response = groupMembershipService.getGroupUsers(groupId);
        return convertServiceToHttpResponse(response);
    }

    @PostMapping(USER_REQUEST_PATH)
    public ResponseEntity<Void> addUserToGroup(@PathVariable(VARIABLE_GROUP_ID) String groupId, @PathVariable(VARIABLE_USER_ID) String userId) {
        return convertServiceToHttpResponse(groupMembershipService.addUserToGroup(userId, groupId));
    }

    @DeleteMapping(USER_REQUEST_PATH)
    public ResponseEntity<Void> removeUserFromGroup(@PathVariable(VARIABLE_GROUP_ID) String groupId, @PathVariable(VARIABLE_USER_ID) String userId) {
        return convertServiceToHttpResponse(groupMembershipService.removeUserFromGroup(userId, groupId));
    }

    @SuppressWarnings("unchecked")
    private <T> ResponseEntity<T> convertServiceToHttpResponse(ServiceResponse<T> response) {
        switch (response.getStatus()) {
            case NOT_FOUND:
                return (ResponseEntity<T>) ResponseEntity.notFound().build();
            case UNAUTHORIZED:
                return (ResponseEntity<T>) ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            case DELETED:
                return (ResponseEntity<T>) ResponseEntity.status(HttpStatus.GONE).build();
        }

        return ResponseEntity.ok(response.getObj());
    }
}
