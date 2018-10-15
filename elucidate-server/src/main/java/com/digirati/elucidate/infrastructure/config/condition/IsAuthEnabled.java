package com.digirati.elucidate.infrastructure.config.condition;


import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * A Spring bean condition that causes beans to only be registered when authentication
 * is enabled.
 */
public class IsAuthEnabled implements Condition {

    /**
     * The configuration key used to check if authentication is enabled.
     */
    public static final String AUTH_ENABLED_KEY = "auth.enabled";

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        Environment env = conditionContext.getEnvironment();
        return env.getProperty(AUTH_ENABLED_KEY, Boolean.class);
    }
}
