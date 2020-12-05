package com.digirati.elucidate.infrastructure.config;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@ComponentScan(basePackages = {RepositoryConfig.REPOSITORY_PACKAGE, RepositoryConfig.COMMON_REPOSITORY_PACKAGE})
public class RepositoryConfig {

    public static final String REPOSITORY_PACKAGE = "com.digirati.elucidate.repository";
    public static final String COMMON_REPOSITORY_PACKAGE = "com.digirati.elucidate.common.repository";

    @Autowired
    private Environment environment;

    @Bean(name = "liquibaseMigrations")
    public SpringLiquibase liquibaseMigrations() throws PropertyVetoException {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource());
        liquibase.setChangeLog("classpath:database/liquibase-changelog.xml");
        liquibase.setContexts(environment.getRequiredProperty("liquibase.contexts", String.class));

        return liquibase;
    }

    @Bean(name = "jdbcTemplate")
    public JdbcTemplate jdbcTemplate() throws PropertyVetoException {
        return new JdbcTemplate(dataSource());
    }

    @Bean(name = "dataSource", destroyMethod = "close")
    public DataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(environment.getRequiredProperty("db.driverClass"));
        dataSource.setJdbcUrl(environment.getRequiredProperty("db.url"));
        dataSource.setUser(environment.getRequiredProperty("db.user"));
        dataSource.setPassword(environment.getRequiredProperty("db.password"));
        dataSource.setInitialPoolSize(environment.getRequiredProperty("db.pool.minSize", Integer.class));
        dataSource.setMinPoolSize(environment.getRequiredProperty("db.pool.minSize", Integer.class));
        dataSource.setMaxPoolSize(environment.getRequiredProperty("db.pool.maxSize", Integer.class));
        dataSource.setMaxIdleTimeExcessConnections(environment.getRequiredProperty("db.pool.maxIdleTime", Integer.class));
        dataSource.setCheckoutTimeout(environment.getRequiredProperty("db.pool.maxWait", Integer.class));
        dataSource.setAcquireIncrement(environment.getRequiredProperty("db.pool.acquireIncrement", Integer.class));
        dataSource.setAcquireRetryAttempts(environment.getRequiredProperty("db.pool.acquireRetryAttempts", Integer.class));
        dataSource.setAcquireRetryDelay(environment.getRequiredProperty("db.pool.acquireRetryDelay", Integer.class));
        dataSource.setIdleConnectionTestPeriod(environment.getRequiredProperty("db.pool.idleConnectionTestPeriod", Integer.class));
        dataSource.setPreferredTestQuery(environment.getRequiredProperty("db.pool.connectionHealthQuery"));
        return dataSource;
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager() throws PropertyVetoException {
        return new DataSourceTransactionManager(dataSource());
    }
}
