package com.digirati.elucidate.infrastructure.builder.function;

@FunctionalInterface
public interface AnnotationPageIRIBuilder {

    String buildAnnotationPageIri(int page, boolean embeddedDescriptions);
}
