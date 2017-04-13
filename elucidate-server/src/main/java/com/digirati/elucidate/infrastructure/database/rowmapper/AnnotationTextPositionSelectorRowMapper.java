package com.digirati.elucidate.infrastructure.database.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.digirati.elucidate.common.infrastructure.util.ResultSetUtils;
import com.digirati.elucidate.model.annotation.selector.textposition.AnnotationTextPositionSelector;

public class AnnotationTextPositionSelectorRowMapper implements RowMapper<AnnotationTextPositionSelector> {

    @Override
    public AnnotationTextPositionSelector mapRow(ResultSet rs, int rowNum) throws SQLException {
        AnnotationTextPositionSelector annotationTextPositionSelector = new AnnotationTextPositionSelector();
        annotationTextPositionSelector.setPk(ResultSetUtils.getInt(rs, "id"));
        annotationTextPositionSelector.setBodyiri(ResultSetUtils.getString(rs, "bodyiri"));
        annotationTextPositionSelector.setBodySourceIri(ResultSetUtils.getString(rs, "bodysourceiri"));
        annotationTextPositionSelector.setTargetIri(ResultSetUtils.getString(rs, "targetiri"));
        annotationTextPositionSelector.setTargetSourceIri(ResultSetUtils.getString(rs, "targetsourceiri"));
        annotationTextPositionSelector.setAnnotationId(ResultSetUtils.getString(rs, "annotationid"));
        annotationTextPositionSelector.setCollectionId(ResultSetUtils.getString(rs, "collectionid"));
        annotationTextPositionSelector.setCreatedDateTime(ResultSetUtils.getDate(rs, "createddatetime"));
        annotationTextPositionSelector.setDeleted(ResultSetUtils.getBoolean(rs, "deleted"));
        annotationTextPositionSelector.setEnd(ResultSetUtils.getInt(rs, "end"));
        annotationTextPositionSelector.setJsonMap(ResultSetUtils.getJsonMap(rs, "json"));
        annotationTextPositionSelector.setModifiedDateTime(ResultSetUtils.getDate(rs, "modifieddatetime"));
        annotationTextPositionSelector.setStart(ResultSetUtils.getInt(rs, "start"));
        return annotationTextPositionSelector;
    }
}
