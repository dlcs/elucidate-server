package com.digirati.elucidate.model.annotation;

import java.util.List;

public final class AnnotationReferenceCollection {

    private final List<AnnotationReference> annotations;

    public AnnotationReferenceCollection(List<AnnotationReference> annotations) {
        this.annotations = annotations;
    }

    public List<AnnotationReference> getAnnotations() {
        return annotations;
    }
}
