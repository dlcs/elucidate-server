package com.digirati.elucidate.repository;

import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface AnnotationStatisticsRepository {

    List<Pair<String, Integer>> getBodyIdCounts();

    List<Pair<String, Integer>> getBodySourceCounts();

    List<Pair<String, Integer>> getTargetIdCounts();

    List<Pair<String, Integer>> getTargetSourceCounts();
}
