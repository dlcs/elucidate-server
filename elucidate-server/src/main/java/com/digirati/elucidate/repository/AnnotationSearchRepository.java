package com.digirati.elucidate.repository;

import java.util.List;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;

public interface AnnotationSearchRepository {

    public List<W3CAnnotation> getAnnotationsByTargetId(String targetId);
}
