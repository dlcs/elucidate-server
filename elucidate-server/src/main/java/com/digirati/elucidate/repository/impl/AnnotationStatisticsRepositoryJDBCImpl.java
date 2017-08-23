package com.digirati.elucidate.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.digirati.elucidate.common.infrastructure.util.ResultSetUtils;
import com.digirati.elucidate.common.repository.impl.AbstractRepositoryJDBCImpl;
import com.digirati.elucidate.repository.AnnotationStatisticsRepository;

@Repository(AnnotationStatisticsRepositoryJDBCImpl.REPOSITORY_NAME)
public class AnnotationStatisticsRepositoryJDBCImpl extends AbstractRepositoryJDBCImpl implements AnnotationStatisticsRepository {

    public static final String REPOSITORY_NAME = "statisticsRepositoryJDBCImpl";

    @Autowired
    public AnnotationStatisticsRepositoryJDBCImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pair<String, Integer>> getBodyIdCounts() {
        String sql = "SELECT bodyiri AS value, COUNT(1) AS count FROM annotation_body_get WHERE bodyiri IS NOT NULL AND deleted = false GROUP BY bodyiri ORDER BY count DESC";

        return queryForList(sql, null, null, new CountRowMapper());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pair<String, Integer>> getBodySourceCounts() {
        String sql = "SELECT sourceiri AS value, COUNT(1) AS count FROM annotation_body_get WHERE sourceiri IS NOT NULL AND deleted = false GROUP BY sourceiri ORDER BY count DESC";

        return queryForList(sql, null, null, new CountRowMapper());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pair<String, Integer>> getTargetIdCounts() {
        String sql = "SELECT targetiri AS value, COUNT(1) AS count FROM annotation_target_get WHERE targetiri IS NOT NULL AND deleted = false GROUP BY targetiri ORDER BY count DESC";

        return queryForList(sql, null, null, new CountRowMapper());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pair<String, Integer>> getTargetSourceCounts() {
        String sql = "SELECT sourceiri AS value, COUNT(1) AS count FROM annotation_target_get WHERE sourceiri IS NOT NULL  AND deleted = false GROUP BY sourceiri ORDER BY count DESC";

        return queryForList(sql, null, null, new CountRowMapper());
    }

    private static class CountRowMapper implements RowMapper<Pair<String, Integer>> {

        @Override
        public Pair<String, Integer> mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Pair.of(ResultSetUtils.getString(rs, "value"), ResultSetUtils.getInt(rs, "count"));
        }
    }
}
