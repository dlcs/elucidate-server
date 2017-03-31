package com.digirati.elucidate.infrastructure.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.digirati.elucidate.common.infrastructure.rowmapper.AbstractRowMapper;
import com.digirati.elucidate.model.annotation.targets.AnnotationTarget;

public class AnnotationTargetRowMapper extends AbstractRowMapper<AnnotationTarget> {

    @Override
    public AnnotationTarget mapRow(ResultSet rs, int rowNum) throws SQLException {
        AnnotationTarget annotationTarget = new AnnotationTarget();
        annotationTarget.setId(getInt(rs, "id"));
        annotationTarget.setAnnotationId(getString(rs, "annotationid"));
        annotationTarget.setCollectionId(getString(rs, "collectionid"));
        annotationTarget.setTargetIri(getString(rs, "targetiri"));
        annotationTarget.setSourceIri(getString(rs, "sourceiri"));
        annotationTarget.setCreatedDateTime(getDate(rs, "createddatetime"));
        annotationTarget.setDeleted(getBoolean(rs, "deleted"));
        annotationTarget.setJsonMap(getJsonMap(rs, "json"));
        annotationTarget.setModifiedDateTime(getDate(rs, "modifieddatetime"));
        return annotationTarget;
    }
}
