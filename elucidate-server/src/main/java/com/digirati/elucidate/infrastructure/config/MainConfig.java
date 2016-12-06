package com.digirati.elucidate.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("${elucidate.server.properties}")
@Import({MVCConfig.class, ServicesConfig.class, RepositoryConfig.class})
public class MainConfig {

}
