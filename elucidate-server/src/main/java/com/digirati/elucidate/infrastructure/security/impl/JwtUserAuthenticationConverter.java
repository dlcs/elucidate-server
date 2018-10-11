package com.digirati.elucidate.infrastructure.security.impl;

import com.digirati.elucidate.infrastructure.security.UserSecurityDetails;
import com.digirati.elucidate.infrastructure.security.UserSecurityDetailsLoader;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

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
                Collection<String> roles = (Collection<String>) details.get(AUTHORITIES);

                if (roles == null) {
                    roles = Collections.emptyList();
                }

                List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(roles.toArray(new String[0]));
                Authentication auth = new UsernamePasswordAuthenticationToken(
                    securityDetails,
                    "N/A",
                    authorities
                );

                return auth;
            })
            .orElse(null);
    }
}
