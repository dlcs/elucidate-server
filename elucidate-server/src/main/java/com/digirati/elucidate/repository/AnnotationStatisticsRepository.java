package com.digirati.elucidate.repository;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

public interface AnnotationStatisticsRepository {

    public List<Pair<String, Integer>> getBodyIdCounts();

    public List<Pair<String, Integer>> getBodySourceCounts();

    public List<Pair<String, Integer>> getTargetIdCounts();

    public List<Pair<String, Integer>> getTargetSourceCounts();
}
