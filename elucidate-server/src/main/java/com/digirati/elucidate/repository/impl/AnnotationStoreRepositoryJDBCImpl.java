package com.digirati.elucidate.repository.impl;

import com.digirati.elucidate.common.infrastructure.database.rowmapper.W3CAnnotationRowMapper;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.common.repository.impl.AbstractRepositoryJDBCImpl;
import com.digirati.elucidate.repository.AnnotationStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.List;

@Repository
public class AnnotationStoreRepositoryJDBCImpl extends AbstractRepositoryJDBCImpl implements AnnotationStoreRepository {

    public static final String REPOSITORY_NAME = "annotationStoreRepositoryJdbcImpl";

    @Autowired
    public AnnotationStoreRepositoryJDBCImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    @Transactional(readOnly = true)
    public W3CAnnotation getAnnotationByCollectionIdAndAnnotationId(String collectionId, String annotationId) {
        String sql = "SELECT * FROM annotation_get WHERE collectionid = ? AND annotationid = ? AND deleted = ?";
        Object[] params = {collectionId, annotationId, false};
        int[] sqlTypes = {Types.VARCHAR, Types.VARCHAR, Types.BOOLEAN};

        return queryForObject(sql, params, sqlTypes, new W3CAnnotationRowMapper());
    }

    @Override
    @Transactional(readOnly = true)
    public List<W3CAnnotation> getAnnotationsByCollectionId(String collectionId) {
        String sql = "SELECT * FROM annotation_get WHERE collectionid = ? AND deleted = ? ORDER BY COALESCE(modifieddatetime, createddatetime)";
        Object[] params = {collectionId, false};
        int[] sqlTypes = {Types.VARCHAR, Types.BOOLEAN};

        return queryForList(sql, params, sqlTypes, new W3CAnnotationRowMapper());
    }

    @Override
    @Transactional(readOnly = false)
    public W3CAnnotation createAnnotation(String collectionId, String annotationId, String annotationJson, Integer ownerId) {
        String sql = "SELECT * FROM annotation_create(?, ?, ?, ?)";
        Object[] params = {collectionId, annotationId, annotationJson, ownerId};
        int[] sqlTypes = {Types.VARCHAR, Types.VARCHAR, Types.OTHER, Types.INTEGER};

        return queryForObject(sql, params, sqlTypes, new W3CAnnotationRowMapper());
    }

    @Override
    @Transactional(readOnly = false)
    public W3CAnnotation updateAnnotation(String collectionId, String annotationId, String annotationJson) {
        String sql = "SELECT * FROM annotation_update(?, ?, ?)";
        Object[] params = {collectionId, annotationId, annotationJson};
        int[] sqlTypes = {Types.VARCHAR, Types.VARCHAR, Types.OTHER};

        return queryForObject(sql, params, sqlTypes, new W3CAnnotationRowMapper());
    }

    @Override
    @Transactional(readOnly = false)
    public W3CAnnotation deleteAnnotation(String collectionId, String annotationId) {
        String sql = "SELECT * FROM annotation_delete(?, ?)";
        Object[] params = {collectionId, annotationId};
        int[] sqlTypes = {Types.VARCHAR, Types.VARCHAR};

        return queryForObject(sql, params, sqlTypes, new W3CAnnotationRowMapper());
    }

    @Override
    @Transactional(readOnly = true)
    public int countDeletedAnnotations(String collectionId, String annotationId) {
        String sql = "SELECT COUNT(1) FROM annotation_get WHERE collectionid = ? AND annotationid = ? AND deleted = ?";
        Object[] params = {collectionId, annotationId, true};
        int[] sqlTypes = {Types.VARCHAR, Types.VARCHAR, Types.BOOLEAN};

        return queryForClass(sql, params, sqlTypes, Integer.class);
    }
}
