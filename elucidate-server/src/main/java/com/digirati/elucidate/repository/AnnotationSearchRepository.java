package com.digirati.elucidate.repository;

import java.util.List;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;

public interface AnnotationSearchRepository {

    public List<W3CAnnotation> getAnnotationsByBody(boolean searchIds, boolean searchSources, String value, boolean strict);

    public List<W3CAnnotation> getAnnotationsByTarget(boolean searchIds, boolean searchSources, String value, boolean strict);
}
