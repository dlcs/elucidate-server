package com.digirati.elucidate.infrastructure.batch.function;

import java.util.List;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;

@FunctionalInterface
public interface AnnotationBatchSearch<A extends AbstractAnnotation> {

    public List<A> searchForAnnotations(List<String> searchFields, String searchValue);
}
