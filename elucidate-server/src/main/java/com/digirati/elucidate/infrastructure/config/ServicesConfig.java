package com.digirati.elucidate.infrastructure.config;

import com.digirati.elucidate.infrastructure.generator.IDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAspectJAutoProxy
@EnableTransactionManagement
@ComponentScan(basePackages = {ServicesConfig.SERVICE_PACKAGE, ServicesConfig.COMMON_SERVICE_PACKAGE, ServicesConfig.INFRASTRUCTURE_PACKAGE})
public class ServicesConfig {

    public static final String SERVICE_PACKAGE = "com.digirati.elucidate.service";
    public static final String COMMON_SERVICE_PACKAGE = "com.digirati.elucidate.common.service";
    public static final String INFRASTRUCTURE_PACKAGE = "com.digirati.elucidate.infrastructure";

    @Autowired
    private Environment environment;

    @Bean(name = "listenerTaskExecutor", initMethod = "initialize", destroyMethod = "shutdown")
    public TaskExecutor listenerTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(environment.getRequiredProperty("listener.poolSize", Integer.class));
        return threadPoolTaskExecutor;
    }

    @Bean(name = "annotationIdGenerator")
    public IDGenerator annotationIdGenerator() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class<?> clazz = Class.forName(environment.getProperty("annotation.id.generator"));
        return (IDGenerator) clazz.newInstance();
    }

    @Bean(name = "collectionIdGenerator")
    public IDGenerator collectionIdGenerator() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class<?> clazz = Class.forName(environment.getProperty("annotation.collection.id.generator"));
        return (IDGenerator) clazz.newInstance();
    }

    @Bean(name = "userIdGenerator")
    public IDGenerator userIdGenerator() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class<?> clazz = Class.forName(environment.getProperty("annotation.user.id.generator"));
        return (IDGenerator) clazz.newInstance();
    }

    @Bean(name = "groupIdGenerator")
    public IDGenerator groupIdGenerator() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class<?> clazz = Class.forName(environment.getProperty("annotation.group.id.generator"));
        return (IDGenerator) clazz.newInstance();
    }
}
