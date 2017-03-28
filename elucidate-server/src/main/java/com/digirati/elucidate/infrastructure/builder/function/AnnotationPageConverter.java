package com.digirati.elucidate.infrastructure.builder.function;

import java.util.Map;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotationPage;

@FunctionalInterface
public interface AnnotationPageConverter<P extends AbstractAnnotationPage> {

    public P convertToAnnotationPage(Map<String, Object> jsonMap);
}
