package com.digirati.elucidate.repository;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;

import java.util.Date;
import java.util.List;

public interface AnnotationSearchRepository {

    List<W3CAnnotation> getAnnotationsByBody(boolean searchIds, boolean searchSources, String value, boolean strict, Integer x, Integer y, Integer w, Integer h, Integer start, Integer end, String creatorIri, String generatorIri);

    List<W3CAnnotation> getAnnotationsByTarget(boolean searchIds, boolean searchSources, String value, boolean strict, Integer x, Integer y, Integer w, Integer h, Integer start, Integer end, String creatorIri, String generatorIri);

    List<W3CAnnotation> getAnnotationsByCreator(boolean searchAnnotations, boolean searchBodies, boolean searchTargets, String type, String value, boolean strict);

    List<W3CAnnotation> getAnnotationsByGenerator(boolean searchAnnotations, boolean searchBodies, boolean searchTargets, String type, String value, boolean strict);

    List<W3CAnnotation> getAnnotationsByTemporal(boolean searchAnnotations, boolean searchBodies, boolean searchTargets, String[] types, Date since);
}
