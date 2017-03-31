package com.digirati.elucidate.repository;

import com.digirati.elucidate.model.annotation.targets.AnnotationTarget;

import java.util.List;

public interface AnnotationTargetStoreRepository {

    public AnnotationTarget createAnnotationTarget(int annotationPK, String targetIri, String sourceIri, String targetJson);

    public List<AnnotationTarget> deleteAnnotationTargets(int annotationPK);
}
