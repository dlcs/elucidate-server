package com.digirati.elucidate.web.controller.security;

import com.digirati.elucidate.infrastructure.config.condition.IsAuthEnabled;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.ServiceResponse.Status;
import com.digirati.elucidate.model.security.SecurityGroup;
import com.digirati.elucidate.service.security.SecurityGroupService;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group")
@Conditional(IsAuthEnabled.class)
public class SecurityGroupController {

    private SecurityGroupService securityGroupService;

    public SecurityGroupController(SecurityGroupService securityGroupService) {
        this.securityGroupService = securityGroupService;
    }

    @PostMapping
    public ResponseEntity<SecurityGroup> createGroup(@RequestBody SecurityGroup group) {
        ServiceResponse<SecurityGroup> response = securityGroupService.createGroup(group.getLabel());
        return ResponseEntity.ok(response.getObj());
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<SecurityGroup> getGroup(@PathVariable("groupId") String groupId) {
        ServiceResponse<SecurityGroup> response = securityGroupService.getGroup(groupId);
        Status status = response.getStatus();

        if (status == Status.NOT_FOUND) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        if (status == Status.UNAUTHORIZED) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return ResponseEntity.ok(response.getObj());
    }

}
