package com.digirati.elucidate.infrastructure.config;

import com.digirati.elucidate.common.infrastructure.util.URIUtils;
import com.digirati.elucidate.infrastructure.generator.IDGenerator;
import com.digirati.elucidate.infrastructure.security.UserSecurityDetailsContext;
import com.digirati.elucidate.infrastructure.security.UserSecurityDetailsLoader;
import com.digirati.elucidate.infrastructure.security.impl.DefaultUserSecurityDetailsContext;
import com.digirati.elucidate.infrastructure.security.impl.JwtUserAuthenticationConverter;
import com.digirati.elucidate.infrastructure.security.impl.JwtUserSecurityDetailsContext;
import com.digirati.elucidate.infrastructure.security.impl.UserSecurityDetailsLoaderImpl;
import com.digirati.elucidate.repository.security.GroupRepository;
import com.digirati.elucidate.repository.security.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableResourceServer
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthConfig implements ResourceServerConfigurer {

    /**
     * Error message shown when authentication is enabled but no token verification key was provided.
     */
    public static final String MISSING_TOKEN_KEY_ERROR = "A token verification key must be set if authentication is enabled";

    /**
     * Error message shown when no values were given for `auth.token.uidProperties`.
     */
    public static final String NO_UID_PROPERTIES_ERROR = "No property list given for identifying uids, no way to authenticate users";

    /**
     * The public key used to verify a tokens signature.
     */
    @Value("${auth.token.verifierKey:}")
    private String verifierKey;

    /**
     * The type of verifier used to validate signatures (either `secret` or `pubkey`).
     */
    @Value("${auth.token.verifierType:}")
    private String verifierType;

    /**
     * A comma-delimited list of JSON property keys that will be checked
     * for a username property in the JWT token.
     */
    @Value("${auth.token.uidProperties:}")
    private String uidProperties;

    /**
     * The public key used to verify a tokens signature.
     */
    @Value("${auth.enabled:false}")
    private boolean authEnabled;

    /**
     * The URL scheme that will be used in the OAuth2 resource id.
     */
    @Value("${base.scheme}")
    private String baseScheme;

    /**
     * The hostname that will be used in the OAuth2 resource id.
     */
    @Value("${base.host}")
    private String baseHost;

    /**
     * The port that will be used in the OAuth2 resource id.
     */
    @Value("${base.port}")
    private int basePort;

    /**
     * The base path that will be used in the OAuth2 resource id.
     */
    @Value("${base.path}")
    private String basePath;

    @Autowired
    private GroupRepository groups;

    @Autowired
    private UserRepository users;

    @Autowired
    @Qualifier("userIdGenerator")
    private IDGenerator userIdGenerator;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        if (authEnabled) {
            resources
                .tokenServices(tokenServices())
                .resourceId(URIUtils.buildBaseUrl(baseScheme, baseHost, basePort, basePath));
        }
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer.AuthorizedUrl authorizationConfigurer = http
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .anyRequest();

        if (authEnabled) {
            authorizationConfigurer.authenticated();
        } else {
            authorizationConfigurer.permitAll();
        }
    }

    @Bean
    UserSecurityDetailsContext annotationSecurityContext() {
        return authEnabled ? new JwtUserSecurityDetailsContext() : new DefaultUserSecurityDetailsContext();
    }

    @Bean
    ResourceServerTokenServices tokenServices() {
        DefaultTokenServices services = new DefaultTokenServices();
        services.setTokenStore(tokenStore());
        return services;
    }

    @Bean
    TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    JwtAccessTokenConverter accessTokenConverter() {
        if (StringUtils.isEmpty(verifierKey)) {
            throw new IllegalStateException(MISSING_TOKEN_KEY_ERROR);
        }

        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(userAuthenticationConverter());

        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        if ("pubkey".equalsIgnoreCase(verifierType)) {
            converter.setVerifier(new RsaVerifier(verifierKey));
        } else if ("secret".equalsIgnoreCase(verifierType)) {
            converter.setVerifier(new MacSigner(verifierKey));
        }

        converter.setAccessTokenConverter(accessTokenConverter);
        return converter;
    }

    @Bean
    UserAuthenticationConverter userAuthenticationConverter() {
        List<String> uidProperties = Arrays.asList(this.uidProperties.split(","));
        if (uidProperties.isEmpty()) {
            throw new IllegalStateException(NO_UID_PROPERTIES_ERROR);
        }

        return new JwtUserAuthenticationConverter(uidProperties, securityDetailsLoader());
    }

    @Bean
    UserSecurityDetailsLoader securityDetailsLoader() {
        return new UserSecurityDetailsLoaderImpl(userIdGenerator, users, groups);
    }

}
