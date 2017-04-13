package com.digirati.elucidate.repository.impl;

import java.sql.Types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.digirati.elucidate.common.infrastructure.database.rowmapper.W3CAnnotationCollectionRowMapper;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationCollection;
import com.digirati.elucidate.common.repository.impl.AbstractRepositoryJDBCImpl;
import com.digirati.elucidate.repository.AnnotationCollectionStoreRepository;

@Repository(AnnotationCollectionStoreRepositoryJDBCImpl.REPOSITORY_NAME)
public class AnnotationCollectionStoreRepositoryJDBCImpl extends AbstractRepositoryJDBCImpl implements AnnotationCollectionStoreRepository {

    public static final String REPOSITORY_NAME = "annotationCollectionStoreRepositoryJdbcImpl";

    @Autowired
    public AnnotationCollectionStoreRepositoryJDBCImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    @Transactional(readOnly = true)
    public W3CAnnotationCollection getAnnotationCollectionById(String collectionId) {
        String sql = "SELECT * from annotation_collection_get WHERE collectionid = ? AND deleted = ?";
        Object[] params = {collectionId, false};
        int[] sqlTypes = {Types.VARCHAR, Types.BOOLEAN};

        return queryForObject(sql, params, sqlTypes, new W3CAnnotationCollectionRowMapper());
    }

    @Override
    @Transactional(readOnly = false)
    public W3CAnnotationCollection createAnnotationCollection(String collectionId, String annotationCollectionJson) {
        String sql = "SELECT * FROM annotation_collection_create(?, ?)";
        Object[] params = {collectionId, annotationCollectionJson};
        int[] sqlTypes = {Types.VARCHAR, Types.OTHER};

        return queryForObject(sql, params, sqlTypes, new W3CAnnotationCollectionRowMapper());
    }
}
