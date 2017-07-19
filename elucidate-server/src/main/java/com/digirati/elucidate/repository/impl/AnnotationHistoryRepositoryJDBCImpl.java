package com.digirati.elucidate.repository.impl;

import com.digirati.elucidate.common.repository.impl.AbstractRepositoryJDBCImpl;
import com.digirati.elucidate.infrastructure.database.rowmapper.W3CAnnotationHistoryRowMapper;
import com.digirati.elucidate.model.annotation.history.W3CAnnotationHistory;
import com.digirati.elucidate.repository.AnnotationHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.List;

@Repository(AnnotationHistoryRepositoryJDBCImpl.REPOSITORY_NAME)
public class AnnotationHistoryRepositoryJDBCImpl extends AbstractRepositoryJDBCImpl implements AnnotationHistoryRepository {

    public static final String REPOSITORY_NAME = "annotationHistoryRepositoryJDBCImpl";

    @Autowired
    protected AnnotationHistoryRepositoryJDBCImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    @Transactional(readOnly = false)
    public W3CAnnotationHistory createAnnotationHistory(Integer annotationPK, String annotationJsonStr) {
        String sql = "SELECT * FROM annotation_history_create(?, ?)";
        Object[] params = {annotationPK, annotationJsonStr};
        int[] sqlTypes = {Types.INTEGER, Types.OTHER};

        return queryForObject(sql, params, sqlTypes, new W3CAnnotationHistoryRowMapper());
    }

    @Override
    @Transactional(readOnly = false)
    public List<W3CAnnotationHistory> deleteAnnotationHistory(int annotationPK) {
        String sql = "SELECT * FROM annotation_history_delete(?)";
        Object[] params = {annotationPK};
        int[] sqlTypes = {Types.INTEGER};

        return queryForList(sql, params, sqlTypes, new W3CAnnotationHistoryRowMapper());
    }

    @Override
    @Transactional(readOnly = true)
    public W3CAnnotationHistory getAnnotationHistory(String collectionId, String annotationId, int version) {
        String sql = "SELECT * FROM annotation_history_get where collectionid = ? AND annotationid = ? AND version = ? and deleted = false";
        Object[] params = {collectionId, annotationId, version};
        int[] sqlTypes = {Types.VARCHAR, Types.VARCHAR, Types.INTEGER};

        return queryForObject(sql, params, sqlTypes, new W3CAnnotationHistoryRowMapper());
    }

    @Override
    public W3CAnnotationHistory getLatestAnnotationHistory(String collectionId, String annotationId) {
        String sql = "SELECT * FROM annotation_history_get WHERE (collectionid, annotationid, version) IN (SELECT collectionid, annotationid, MAX(version) FROM annotation_history_get WHERE collectionid = ? AND annotationid = ? AND deleted = false GROUP BY collectionid, annotationid) AND deleted = false";
        Object[] params = {collectionId, annotationId};
        int[] sqlTypes = {Types.VARCHAR, Types.VARCHAR};

        return queryForObject(sql, params, sqlTypes, new W3CAnnotationHistoryRowMapper());
    }

    @Override
    @Transactional(readOnly = true)
    public W3CAnnotationHistory getPenultimateAnnotationHistory(String collectionId, String annotationId) {
        String sql = "SELECT * FROM annotation_history_get WHERE (collectionid, annotationid, version) IN (SELECT collectionid, annotationid, MAX(version) - 1 FROM annotation_history_get WHERE collectionid = ? AND annotationid = ? AND deleted = false GROUP BY collectionid, annotationid) AND deleted = false";
        Object[] params = {collectionId, annotationId};
        int[] sqlTypes = {Types.VARCHAR, Types.VARCHAR};

        return queryForObject(sql, params, sqlTypes, new W3CAnnotationHistoryRowMapper());
    }

    @Override
    @Transactional(readOnly = true)
    public W3CAnnotationHistory getNextAnnotationHistory(String collectionId, String annotationId, int version) {
        String sql = "SELECT * FROM annotation_history_get WHERE collectionid = ? AND annotationid = ? AND version = ? +1 AND deleted = false";
        Object[] params = {collectionId, annotationId, version};
        int[] sqlTypes = {Types.VARCHAR, Types.VARCHAR, Types.INTEGER};

        return queryForObject(sql, params, sqlTypes, new W3CAnnotationHistoryRowMapper());
    }

    @Override
    @Transactional(readOnly = true)
    public W3CAnnotationHistory getPreviousAnnotationHistory(String collectionId, String annotationId, int version) {
        String sql = "SELECT * FROM annotation_history_get WHERE collectionid = ? AND annotationid = ? AND version = ? - 1 AND deleted = false";
        Object[] params = {collectionId, annotationId, version};
        int[] sqlTypes = {Types.VARCHAR, Types.VARCHAR, Types.INTEGER};

        return queryForObject(sql, params, sqlTypes, new W3CAnnotationHistoryRowMapper());
    }
}
