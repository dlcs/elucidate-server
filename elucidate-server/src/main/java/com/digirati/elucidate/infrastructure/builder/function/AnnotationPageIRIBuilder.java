package com.digirati.elucidate.infrastructure.builder.function;

@FunctionalInterface
public interface AnnotationPageIRIBuilder {

    public String buildAnnotationPageIri(int page, boolean embeddedDescriptions);
}
