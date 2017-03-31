package com.digirati.elucidate.repository.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.digirati.elucidate.common.repository.impl.AbstractRepositoryJDBCImpl;
import com.digirati.elucidate.infrastructure.rowmapper.AnnotationBodyRowMapper;
import com.digirati.elucidate.model.annotation.body.AnnotationBody;
import com.digirati.elucidate.repository.AnnotationBodyStoreRepository;

@Repository(AnnotationBodyStoreRepositoryJDBCImpl.REPOSITORY_NAME)
public class AnnotationBodyStoreRepositoryJDBCImpl extends AbstractRepositoryJDBCImpl implements AnnotationBodyStoreRepository {

    public static final String REPOSITORY_NAME = "annotationBodyStoreRepositoryJDBCImpl";

    @Autowired
    public AnnotationBodyStoreRepositoryJDBCImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    @Transactional(readOnly = false)
    public AnnotationBody createAnnotationBody(int annotationPK, String bodyIri, String sourceIri, String bodyJson) {
        String sql = "SELECT * FROM annotation_body_create(?, ?, ?, ?)";
        Object[] params = {annotationPK, bodyIri, sourceIri, bodyJson};
        int[] sqlTypes = {Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.OTHER};

        return queryForObject(sql, params, sqlTypes, new AnnotationBodyRowMapper());
    }

    @Override
    @Transactional(readOnly = false)
    public List<AnnotationBody> deletedAnnotationBodies(int annotationPK) {
        String sql = "SELECT * FROM annotation_body_delete(?)";
        Object[] params = {annotationPK};
        int[] sqlTypes = {Types.INTEGER};

        return queryForList(sql, params, sqlTypes, new AnnotationBodyRowMapper());
    }
}
