package com.digirati.elucidate.infrastructure.batch.function;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;

import java.util.List;

@FunctionalInterface
public interface AnnotationBatchSearch<A extends AbstractAnnotation> {

    List<A> searchForAnnotations(List<String> searchFields, String searchValue);
}
