package com.digirati.elucidate.repository.impl;

import com.digirati.elucidate.common.repository.impl.AbstractRepositoryJDBCImpl;
import com.digirati.elucidate.infrastructure.database.rowmapper.AnnotationTemporalRowMapper;
import com.digirati.elucidate.model.annotation.temporal.AnnotationTemporal;
import com.digirati.elucidate.repository.AnnotationTemporalStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.Date;
import java.util.List;

@Repository
public class AnnotationTemporalStoreRepositoryJDBCImpl extends AbstractRepositoryJDBCImpl implements AnnotationTemporalStoreRepository {

    public static final String REPOSITORY_NAME = "annotationTemporalStoreRepositoryJDBCImpl";

    @Autowired
    public AnnotationTemporalStoreRepositoryJDBCImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    @Transactional(readOnly = false)
    public AnnotationTemporal createAnnotationTemporal(Integer annotationPk, Integer bodyPk, Integer targetPk, String type, Date value, String temporalJson) {
        String sql = "SELECT * FROM annotation_temporal_create(?, ?, ?, ?, ?, ?)";
        Object[] params = {annotationPk, bodyPk, targetPk, type, value, temporalJson};
        int[] sqlTypes = {Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.TIMESTAMP, Types.OTHER};

        return queryForObject(sql, params, sqlTypes, new AnnotationTemporalRowMapper());
    }

    @Override
    @Transactional(readOnly = false)
    public List<AnnotationTemporal> deleteAnnotationTemporals(Integer annotationPk, Integer bodyPk, Integer targetPk) {
        String sql = "SELECT * FROM annotation_temporal_delete(?, ?, ?)";
        Object[] params = {annotationPk, bodyPk, targetPk};
        int[] sqlTypes = {Types.INTEGER, Types.INTEGER, Types.INTEGER};

        return queryForList(sql, params, sqlTypes, new AnnotationTemporalRowMapper());
    }
}
