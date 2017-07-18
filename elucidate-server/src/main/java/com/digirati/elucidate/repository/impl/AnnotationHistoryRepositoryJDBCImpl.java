package com.digirati.elucidate.repository.impl;

import com.digirati.elucidate.common.repository.impl.AbstractRepositoryJDBCImpl;
import com.digirati.elucidate.infrastructure.database.rowmapper.AnnotationHistoryRowMapper;
import com.digirati.elucidate.model.annotation.history.AnnotationHistory;
import com.digirati.elucidate.repository.AnnotationHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;

@Repository
public class AnnotationHistoryRepositoryJDBCImpl extends AbstractRepositoryJDBCImpl implements AnnotationHistoryRepository {

    public static final String REPOSITORY_NAME = "annotationHistoryRepositoryJDBCImpl";

    @Autowired
    protected AnnotationHistoryRepositoryJDBCImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    @Transactional(readOnly = false)
    public AnnotationHistory createAnnotationHistory(Integer annotationPK, String annotationJsonStr) {
        String sql = "SELECT * FROM annotation_history_create(?, ?)";
        Object[] params = {annotationPK, annotationJsonStr};
        int[] sqlTypes = {Types.INTEGER, Types.OTHER};

        return queryForObject(sql, params, sqlTypes, new AnnotationHistoryRowMapper());
    }
}
