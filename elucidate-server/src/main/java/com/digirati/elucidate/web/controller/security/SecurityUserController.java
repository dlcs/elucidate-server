package com.digirati.elucidate.web.controller.security;

import com.digirati.elucidate.infrastructure.config.condition.IsAuthEnabled;
import com.digirati.elucidate.infrastructure.security.UserSecurityDetails;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Conditional(IsAuthEnabled.class)
public class SecurityUserController {

    @RequestMapping("/current")
    public ResponseEntity<UserSecurityDetails> getCurrentUser(Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body((UserSecurityDetails) authentication.getPrincipal());
    }

}
