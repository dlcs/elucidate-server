package com.digirati.elucidate.repository;

import com.digirati.elucidate.model.annotation.temporal.AnnotationTemporal;

import java.util.Date;
import java.util.List;

public interface AnnotationTemporalStoreRepository {

    AnnotationTemporal createAnnotationTemporal(Integer annotationPk, Integer bodyPk, Integer targetPk, String type, Date value, String temporalJson);

    List<AnnotationTemporal> deleteAnnotationTemporals(Integer annotationPk, Integer bodyPk, Integer targetPk);
}
