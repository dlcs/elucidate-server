package com.digirati.elucidate.infrastructure.builder.function;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotationCollection;

@FunctionalInterface
public interface AnnotationCollectionConverter<C extends AbstractAnnotationCollection> {

    C convertToAnnotationCollection();
}
