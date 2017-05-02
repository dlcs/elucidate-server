package com.digirati.elucidate.infrastructure.database.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.digirati.elucidate.common.infrastructure.util.ResultSetUtils;
import com.digirati.elucidate.model.annotation.body.AnnotationBody;

public class AnnotationBodyRowMapper implements RowMapper<AnnotationBody> {

    @Override
    public AnnotationBody mapRow(ResultSet rs, int rowNum) throws SQLException {
        AnnotationBody annotationBody = new AnnotationBody();
        annotationBody.setPk(ResultSetUtils.getInt(rs, "id"));
        annotationBody.setAnnotationId(ResultSetUtils.getString(rs, "annotationid"));
        annotationBody.setCollectionId(ResultSetUtils.getString(rs, "collectionid"));
        annotationBody.setBodyIri(ResultSetUtils.getString(rs, "bodyiri"));
        annotationBody.setSourceIri(ResultSetUtils.getString(rs, "sourceiri"));
        annotationBody.setCreatedDateTime(ResultSetUtils.getDate(rs, "createddatetime"));
        annotationBody.setDeleted(ResultSetUtils.getBoolean(rs, "deleted"));
        annotationBody.setJsonMap(ResultSetUtils.getJsonMap(rs, "json"));
        annotationBody.setModifiedDateTime(ResultSetUtils.getDate(rs, "modifieddatetime"));
        return annotationBody;
    }
}
