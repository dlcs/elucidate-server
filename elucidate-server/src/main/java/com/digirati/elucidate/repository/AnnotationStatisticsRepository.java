package com.digirati.elucidate.repository;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

public interface AnnotationStatisticsRepository {

    List<Pair<String, Integer>> getBodyIdCounts();

    List<Pair<String, Integer>> getBodySourceCounts();

    List<Pair<String, Integer>> getTargetIdCounts();

    List<Pair<String, Integer>> getTargetSourceCounts();
}
