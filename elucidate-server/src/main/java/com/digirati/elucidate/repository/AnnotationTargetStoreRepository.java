package com.digirati.elucidate.repository;

import com.digirati.elucidate.model.annotation.targets.AnnotationTarget;

import java.util.List;

public interface AnnotationTargetStoreRepository {

    AnnotationTarget createAnnotationTarget(int annotationPK, String targetIri, String sourceIri, String targetJson);

    List<AnnotationTarget> deleteAnnotationTargets(int annotationPK);
}
