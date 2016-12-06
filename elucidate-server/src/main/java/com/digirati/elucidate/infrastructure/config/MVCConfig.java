package com.digirati.elucidate.infrastructure.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.digirati.elucidate.web.converter.oa.annotation.JSONLDOAAnnotationMessageConverter;
import com.digirati.elucidate.web.converter.oa.annotation.TurtleOAAnnotationMessageConverter;
import com.digirati.elucidate.web.converter.oa.annotationcontainer.annotationcollection.JSONLDOAAnnotationCollectionMessageConverter;
import com.digirati.elucidate.web.converter.oa.annotationcontainer.annotationcollection.TurtleOAAnnotationCollectionMessageConverter;
import com.digirati.elucidate.web.converter.oa.annotationcontainer.annotationpage.JSONLDOAAnnotationPageMessageConverter;
import com.digirati.elucidate.web.converter.oa.annotationcontainer.annotationpage.TurtleOAAnnotationPageMessageConverter;
import com.digirati.elucidate.web.converter.validationerror.JSONLDValidationErrorMessageConverter;
import com.digirati.elucidate.web.converter.w3c.annotation.JSONLDW3CAnnotationMessageConverter;
import com.digirati.elucidate.web.converter.w3c.annotation.TurtleW3CAnnotationMessageConverter;
import com.digirati.elucidate.web.converter.w3c.annotationcontainer.annotationcollection.JSONLDW3CAnnotationCollectionMessageConverter;
import com.digirati.elucidate.web.converter.w3c.annotationcontainer.annotationcollection.TurtleW3CAnnotationCollectionMessageConverter;
import com.digirati.elucidate.web.converter.w3c.annotationcontainer.annotationpage.JSONLDW3CAnnotationPageMessageConverter;
import com.digirati.elucidate.web.converter.w3c.annotationcontainer.annotationpage.TurtleW3CAnnotationPageMessageConverter;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = MVCConfig.WEB_PACKAGE)
public class MVCConfig extends WebMvcConfigurerAdapter {

    public static final String WEB_PACKAGE = "com.digirati.elucidate.web";

    @Autowired
    private Environment env;

    @Autowired
    private JSONLDW3CAnnotationMessageConverter jsonLdW3CAnnotationMessageConverter;
    @Autowired
    private TurtleW3CAnnotationMessageConverter turtleW3CAnnotationMessageConverter;
    @Autowired
    private JSONLDW3CAnnotationCollectionMessageConverter jsonLdW3CAnnotationCollectionMessageConverter;
    @Autowired
    private TurtleW3CAnnotationCollectionMessageConverter turtleW3CAnnotationCollectionMessageConverter;
    @Autowired
    private JSONLDW3CAnnotationPageMessageConverter jsonLdW3CAnnotationPageMessageConverter;
    @Autowired
    private TurtleW3CAnnotationPageMessageConverter turtleW3CAnnotationPageMessageConverter;
    @Autowired
    private JSONLDOAAnnotationMessageConverter jsonLdOAAnnotationMessageConverter;
    @Autowired
    private TurtleOAAnnotationMessageConverter turtleOAAnnotationMessageConverter;
    @Autowired
    private JSONLDOAAnnotationCollectionMessageConverter jsonLdOAAnnotationCollectionMessageConverter;
    @Autowired
    private TurtleOAAnnotationCollectionMessageConverter turtleOAAnnotationCollectionMessageConverter;
    @Autowired
    private JSONLDOAAnnotationPageMessageConverter jsonLdOAAnnotationPageMessageConverter;
    @Autowired
    private TurtleOAAnnotationPageMessageConverter turtleOAAnnotationPageMessageConverter;
    @Autowired
    private JSONLDValidationErrorMessageConverter jsonLdValidationErrorMessageConverter;
    
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseTrailingSlashMatch(false);
        configurer.setUseSuffixPatternMatch(false);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(jsonLdW3CAnnotationMessageConverter);
        converters.add(turtleW3CAnnotationMessageConverter);
        converters.add(jsonLdW3CAnnotationCollectionMessageConverter);
        converters.add(turtleW3CAnnotationCollectionMessageConverter);
        converters.add(jsonLdW3CAnnotationPageMessageConverter);
        converters.add(turtleW3CAnnotationPageMessageConverter);
        converters.add(jsonLdOAAnnotationMessageConverter);
        converters.add(turtleOAAnnotationMessageConverter);
        converters.add(jsonLdOAAnnotationCollectionMessageConverter);
        converters.add(turtleOAAnnotationCollectionMessageConverter);
        converters.add(jsonLdOAAnnotationPageMessageConverter);
        converters.add(turtleOAAnnotationPageMessageConverter);
        converters.add(jsonLdValidationErrorMessageConverter);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/w3c/*/")
            .allowedOrigins(env.getRequiredProperty("annotation.w3c.collection.origins", String[].class))
            .allowedMethods(env.getRequiredProperty("annotation.w3c.collection.methods", String[].class))
            .allowedHeaders(env.getRequiredProperty("annotation.w3c.collection.headers.allowed", String[].class))
            .exposedHeaders(env.getRequiredProperty("annotation.w3c.collection.headers.exposed", String[].class))
            .allowCredentials(env.getRequiredProperty("annotation.w3c.collection.crdentials", Boolean.class));

        registry.addMapping("/w3c/*/*")
            .allowedOrigins(env.getRequiredProperty("annotation.w3c.origins", String[].class))
            .allowedMethods(env.getRequiredProperty("annotation.w3c.methods", String[].class))
            .allowedHeaders(env.getRequiredProperty("annotation.w3c.headers.allowed", String[].class))
            .exposedHeaders(env.getRequiredProperty("annotation.w3c.headers.exposed", String[].class))
            .allowCredentials(env.getRequiredProperty("annotation.w3c.crdentials", Boolean.class));

        registry.addMapping("/oa/*/")
            .allowedOrigins(env.getRequiredProperty("annotation.oa.collection.origins", String[].class))
            .allowedMethods(env.getRequiredProperty("annotation.oa.collection.methods", String[].class))
            .allowedHeaders(env.getRequiredProperty("annotation.oa.collection.headers.allowed", String[].class))
            .exposedHeaders(env.getRequiredProperty("annotation.oa.collection.headers.exposed", String[].class))
            .allowCredentials(env.getRequiredProperty("annotation.oa.collection.crdentials", Boolean.class));

        registry.addMapping("/oa/*/*")
            .allowedOrigins(env.getRequiredProperty("annotation.oa.origins", String[].class))
            .allowedMethods(env.getRequiredProperty("annotation.oa.methods", String[].class))
            .allowedHeaders(env.getRequiredProperty("annotation.oa.headers.allowed", String[].class))
            .exposedHeaders(env.getRequiredProperty("annotation.oa.headers.exposed", String[].class))
            .allowCredentials(env.getRequiredProperty("annotation.oa.crdentials", Boolean.class));
    }
}
