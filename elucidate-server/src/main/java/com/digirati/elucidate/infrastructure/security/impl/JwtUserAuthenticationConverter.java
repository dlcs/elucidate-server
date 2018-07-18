package com.digirati.elucidate.infrastructure.security.impl;

import com.digirati.elucidate.infrastructure.security.UserSecurityDetails;
import com.digirati.elucidate.infrastructure.security.UserSecurityDetailsLoader;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class JwtUserAuthenticationConverter extends DefaultUserAuthenticationConverter {

    private List<String> uidProperties;
    private UserSecurityDetailsLoader securityDetailsLoader;

    public JwtUserAuthenticationConverter(List<String> uidProperties, UserSecurityDetailsLoader securityDetailsLoader) {
        this.uidProperties = uidProperties;
        this.securityDetailsLoader = securityDetailsLoader;
    }

    @Override
    public Authentication extractAuthentication(Map<String, ?> details) {
        return uidProperties.stream()
            .filter(details::containsKey)
            .map(prop -> (String) details.get(prop))
            .findFirst()
            .map(uid -> {
                UserSecurityDetails securityDetails = securityDetailsLoader.findOrCreateUserDetails(uid);
                Authentication auth = new UsernamePasswordAuthenticationToken(
                    securityDetails,
                    "N/A",
                    Collections.emptySet()
                );

                return auth;
            })
            .orElse(null);
    }
}
