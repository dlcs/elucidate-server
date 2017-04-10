package com.digirati.elucidate.repository.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.digirati.elucidate.common.repository.impl.AbstractRepositoryJDBCImpl;
import com.digirati.elucidate.infrastructure.database.rowmapper.AnnotationTargetRowMapper;
import com.digirati.elucidate.model.annotation.targets.AnnotationTarget;
import com.digirati.elucidate.repository.AnnotationTargetStoreRepository;

@Repository(AnnotationTargetStoreRepositoryJDBCImpl.REPOSITORY_NAME)
public class AnnotationTargetStoreRepositoryJDBCImpl extends AbstractRepositoryJDBCImpl implements AnnotationTargetStoreRepository {

    public static final String REPOSITORY_NAME = "annotationTargetStoreRepositoryJDBCImpl";

    @Autowired
    public AnnotationTargetStoreRepositoryJDBCImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    @Transactional(readOnly = false)
    public AnnotationTarget createAnnotationTarget(int annotationPK, String targetIri, String sourceIri, String targetJson) {
        String sql = "SELECT * FROM annotation_target_create(?, ?, ?, ?)";
        Object[] params = {annotationPK, targetIri, sourceIri, targetJson};
        int[] sqlTypes = {Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.OTHER};

        return queryForObject(sql, params, sqlTypes, new AnnotationTargetRowMapper());
    }

    @Override
    @Transactional(readOnly = false)
    public List<AnnotationTarget> deleteAnnotationTargets(int annotationPK) {
        String sql = "SELECT * FROM annotation_target_delete(?)";
        Object[] params = {annotationPK};
        int[] sqlTypes = {Types.INTEGER};

        return queryForList(sql, params, sqlTypes, new AnnotationTargetRowMapper());
    }
}
