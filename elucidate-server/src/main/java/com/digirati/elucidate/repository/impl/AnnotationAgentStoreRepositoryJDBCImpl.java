package com.digirati.elucidate.repository.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.digirati.elucidate.common.repository.impl.AbstractRepositoryJDBCImpl;
import com.digirati.elucidate.infrastructure.database.resultsetextractor.AnnotationAgentResultSetExtractor;
import com.digirati.elucidate.model.annotation.agent.AnnotationAgent;
import com.digirati.elucidate.repository.AnnotationAgentStoreRepository;

@Repository(AnnotationAgentStoreRepositoryJDBCImpl.REPOSITORY_NAME)
public class AnnotationAgentStoreRepositoryJDBCImpl extends AbstractRepositoryJDBCImpl implements AnnotationAgentStoreRepository {

    public static final String REPOSITORY_NAME = "annotationAgentStoreRepositoryJDBCImpl";

    @Autowired
    public AnnotationAgentStoreRepositoryJDBCImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public AnnotationAgent createAnnotationCreator(Integer annotationPK, Integer bodyPK, Integer targetPK, String creatorIri, String creatorJson, String[] types, String[] typesJson, String[] names, String[] namesJson, String nickname, String[] emails, String[] emailsJson, String[] emailSha1s, String[] emailSha1sJson, String[] homepages, String[] homepagesJson) {
        String sql = "SELECT * FROM annotation_creator_create(?, ?, ?, ?, ?, string_to_array(?, ','), string_to_array(?, ',')::jsonb[], string_to_array(?, ','), string_to_array(?, ',')::jsonb[], ?, string_to_array(?, ','), string_to_array(?, ',')::jsonb[], string_to_array(?, ','), string_to_array(?, ',')::jsonb[], string_to_array(?, ','), string_to_array(?, ',')::jsonb[])";
        Object[] params = {annotationPK, bodyPK, targetPK, creatorIri, creatorJson, String.join(",", types), String.join(",", typesJson), String.join(",", names), String.join(",", namesJson), nickname, String.join(",", emails), String.join(",", emailsJson), String.join(",", emailSha1s), String.join(",", emailSha1sJson), String.join(",", homepages), String.join(",", homepagesJson)};
        int[] sqlTypes = {Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.OTHER, Types.VARCHAR, Types.OTHER, Types.VARCHAR, Types.OTHER, Types.VARCHAR, Types.VARCHAR, Types.OTHER, Types.VARCHAR, Types.OTHER, Types.VARCHAR, Types.OTHER};
        
        List<AnnotationAgent> annotationAgents = queryForObject(sql, params, sqlTypes, new AnnotationAgentResultSetExtractor());
        if (annotationAgents.isEmpty()) {
            return null;
        } else if (annotationAgents.size() == 1) {
            return annotationAgents.get(0);
        } else {
            throw new IncorrectResultSizeDataAccessException(0, annotationAgents.size());
        }
    }

    @Override
    public AnnotationAgent deleteAnnotationCreators(Integer annotationPK, Integer bodyPK, Integer targetPK) {
        String sql = "SELECT * FROM annotation_creator_delete(?, ?, ?)";
        Object[] params = {annotationPK, bodyPK, targetPK};
        int[] sqlTypes = {Types.INTEGER, Types.INTEGER, Types.INTEGER};

        List<AnnotationAgent> annotationAgents = queryForObject(sql, params, sqlTypes, new AnnotationAgentResultSetExtractor());
        if (annotationAgents.isEmpty()) {
            return null;
        } else if (annotationAgents.size() == 1) {
            return annotationAgents.get(0);
        } else {
            throw new IncorrectResultSizeDataAccessException(0, annotationAgents.size());
        }
    }
}
