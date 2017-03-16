package com.digirati.elucidate.repository.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.digirati.elucidate.common.infrastructure.rowmapper.W3CAnnotationRowMapper;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.common.repository.impl.AbstractRepositoryJDBCImpl;
import com.digirati.elucidate.repository.AnnotationSearchRepository;

@Repository(AnnotationSearchRepositoryJDBCImpl.REPOSITORY_NAME)
public class AnnotationSearchRepositoryJDBCImpl extends AbstractRepositoryJDBCImpl implements AnnotationSearchRepository {

    public static final String REPOSITORY_NAME = "annotationSearchRepositoryJDBCImpl";

    @Autowired
    public AnnotationSearchRepositoryJDBCImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public List<W3CAnnotation> getAnnotationsByTargetIri(String targetIri, boolean strict) {
        String sql = "SELECT * FROM annotation_search_by_target(?, ?)";
        Object[] params = {targetIri, strict};
        int[] sqlTypes = {Types.VARCHAR, Types.BOOLEAN};

        return queryForList(sql, params, sqlTypes, new W3CAnnotationRowMapper());
    }
}
