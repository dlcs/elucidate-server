package com.digirati.elucidate.infrastructure.config;

import com.digirati.elucidate.common.infrastructure.util.URIUtils;
import com.digirati.elucidate.infrastructure.config.condition.IsSecurityEnabled;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableResourceServer
@Conditional(IsSecurityEnabled.class)
public class AuthConfig implements ResourceServerConfigurer {

    /**
     * Error message shown when authentication is enabled but no token verification key was provided.
     */
    public static final String MISSING_TOKEN_KEY_ERROR = "A token verification key must be set if authentication is enabled";

    /**
     * The public key used to verify a tokens signature.
     */
    @Value("${auth.token.publicKey:}")
    private String verifierKey;

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

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources
            .tokenServices(tokenServices())
            .resourceId(URIUtils.buildBaseUrl(baseScheme, baseHost, basePort, basePath));
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/**")
            .authenticated();
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
        MacSigner signerVerifier = new MacSigner(verifierKey);
        converter.setSigner(signerVerifier);
        converter.setVerifier(signerVerifier);
        converter.setAccessTokenConverter(accessTokenConverter);
        return converter;
    }

    @Bean
    UserAuthenticationConverter userAuthenticationConverter() {
        DefaultUserAuthenticationConverter authConverter = new DefaultUserAuthenticationConverter();
        authConverter.setUserDetailsService(userDetailsService());

        return authConverter;
    }

    @Bean
    UserDetailsService userDetailsService() {
        UserDetailsService uds = new InMemoryUserDetailsManager();
        return uds;
    }
}
