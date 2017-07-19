package com.digirati.elucidate.repository.impl;

import com.digirati.elucidate.common.infrastructure.database.rowmapper.W3CAnnotationRowMapper;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.common.repository.impl.AbstractRepositoryJDBCImpl;
import com.digirati.elucidate.repository.AnnotationSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.List;

@Repository(AnnotationSearchRepositoryJDBCImpl.REPOSITORY_NAME)
public class AnnotationSearchRepositoryJDBCImpl extends AbstractRepositoryJDBCImpl implements AnnotationSearchRepository {

    public static final String REPOSITORY_NAME = "annotationSearchRepositoryJDBCImpl";

    @Autowired
    public AnnotationSearchRepositoryJDBCImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<W3CAnnotation> getAnnotationsByBody(boolean searchIds, boolean searchSources, String value, boolean strict, Integer x, Integer y, Integer w, Integer h, Integer start, Integer end, String creatorIri, String generatorIri) {
        String sql = "SELECT * FROM annotation_search_by_body(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] params = {searchIds, searchSources, value, strict, x, y, w, h, start, end, creatorIri, generatorIri};
        int[] sqlTypes = {Types.BOOLEAN, Types.BOOLEAN, Types.VARCHAR, Types.BOOLEAN, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.VARCHAR};

        return queryForList(sql, params, sqlTypes, new W3CAnnotationRowMapper());
    }

    @Override
    @Transactional(readOnly = true)
    public List<W3CAnnotation> getAnnotationsByTarget(boolean searchIds, boolean searchSources, String value, boolean strict, Integer x, Integer y, Integer w, Integer h, Integer start, Integer end, String creatorIri, String generatorIri) {
        String sql = "SELECT * FROM annotation_search_by_target(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] params = {searchIds, searchSources, value, strict, x, y, w, h, start, end, creatorIri, generatorIri};
        int[] sqlTypes = {Types.BOOLEAN, Types.BOOLEAN, Types.VARCHAR, Types.BOOLEAN, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.VARCHAR};

        return queryForList(sql, params, sqlTypes, new W3CAnnotationRowMapper());
    }

    @Override
    @Transactional(readOnly = true)
    public List<W3CAnnotation> getAnnotationsByCreator(boolean searchAnnotations, boolean searchBodies, boolean searchTargets, String type, String value, boolean strict) {
        String sql = "SELECT * FROM annotation_search_by_creator(?, ?, ?, ?, ?, ?)";
        Object[] params = {searchAnnotations, searchBodies, searchTargets, type, value, strict};
        int[] sqlTypes = {Types.BOOLEAN, Types.BOOLEAN, Types.BOOLEAN, Types.VARCHAR, Types.VARCHAR, Types.BOOLEAN};

        return queryForList(sql, params, sqlTypes, new W3CAnnotationRowMapper());
    }

    @Override
    @Transactional(readOnly = true)
    public List<W3CAnnotation> getAnnotationsByGenerator(boolean searchAnnotations, boolean searchBodies, boolean searchTargets, String type, String value, boolean strict) {
        String sql = "SELECT * FROM annotation_search_by_generator(?, ?, ?, ?, ?, ?)";
        Object[] params = {searchAnnotations, searchBodies, searchTargets, type, value, strict};
        int[] sqlTypes = {Types.BOOLEAN, Types.BOOLEAN, Types.BOOLEAN, Types.VARCHAR, Types.VARCHAR, Types.BOOLEAN};

        return queryForList(sql, params, sqlTypes, new W3CAnnotationRowMapper());
    }
}
