package com.digirati.elucidate.infrastructure.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.digirati.elucidate.common.infrastructure.rowmapper.AbstractRowMapper;
import com.digirati.elucidate.model.annotation.body.AnnotationBody;

public class AnnotationBodyRowMapper extends AbstractRowMapper<AnnotationBody> {

    @Override
    public AnnotationBody mapRow(ResultSet rs, int rowNum) throws SQLException {
        AnnotationBody annotationBody = new AnnotationBody();
        annotationBody.setId(getInt(rs, "id"));
        annotationBody.setAnnotationId(getString(rs, "annotationid"));
        annotationBody.setCollectionId(getString(rs, "collectionid"));
        annotationBody.setBodyIri(getString(rs, "bodyiri"));
        annotationBody.setSourceIri(getString(rs, "sourceiri"));
        annotationBody.setCreatedDateTime(getDate(rs, "createddatetime"));
        annotationBody.setDeleted(getBoolean(rs, "deleted"));
        annotationBody.setJsonMap(getJsonMap(rs, "json"));
        annotationBody.setModifiedDateTime(getDate(rs, "modifieddatetime"));
        return annotationBody;
    }
}
