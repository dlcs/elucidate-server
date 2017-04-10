package com.digirati.elucidate.infrastructure.database.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.digirati.elucidate.common.infrastructure.util.ResultSetUtils;
import com.digirati.elucidate.model.annotation.targets.AnnotationTarget;

public class AnnotationTargetRowMapper implements RowMapper<AnnotationTarget> {

    @Override
    public AnnotationTarget mapRow(ResultSet rs, int rowNum) throws SQLException {
        AnnotationTarget annotationTarget = new AnnotationTarget();
        annotationTarget.setId(ResultSetUtils.getInt(rs, "id"));
        annotationTarget.setAnnotationId(ResultSetUtils.getString(rs, "annotationid"));
        annotationTarget.setCollectionId(ResultSetUtils.getString(rs, "collectionid"));
        annotationTarget.setTargetIri(ResultSetUtils.getString(rs, "targetiri"));
        annotationTarget.setSourceIri(ResultSetUtils.getString(rs, "sourceiri"));
        annotationTarget.setCreatedDateTime(ResultSetUtils.getDate(rs, "createddatetime"));
        annotationTarget.setDeleted(ResultSetUtils.getBoolean(rs, "deleted"));
        annotationTarget.setJsonMap(ResultSetUtils.getJsonMap(rs, "json"));
        annotationTarget.setModifiedDateTime(ResultSetUtils.getDate(rs, "modifieddatetime"));
        return annotationTarget;
    }
}
