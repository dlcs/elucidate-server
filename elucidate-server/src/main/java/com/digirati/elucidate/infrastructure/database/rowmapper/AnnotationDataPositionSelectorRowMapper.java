package com.digirati.elucidate.infrastructure.database.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.digirati.elucidate.common.infrastructure.util.ResultSetUtils;
import com.digirati.elucidate.model.annotation.selector.dataposition.AnnotationDataPositionSelector;

public class AnnotationDataPositionSelectorRowMapper implements RowMapper<AnnotationDataPositionSelector> {

    @Override
    public AnnotationDataPositionSelector mapRow(ResultSet rs, int rowNum) throws SQLException {
        AnnotationDataPositionSelector annotationDataPositionSelector = new AnnotationDataPositionSelector();
        annotationDataPositionSelector.setId(ResultSetUtils.getInt(rs, "id"));
        annotationDataPositionSelector.setBodyiri(ResultSetUtils.getString(rs, "bodyiri"));
        annotationDataPositionSelector.setBodySourceIri(ResultSetUtils.getString(rs, "bodysourceiri"));
        annotationDataPositionSelector.setTargetIri(ResultSetUtils.getString(rs, "targetiri"));
        annotationDataPositionSelector.setTargetSourceIri(ResultSetUtils.getString(rs, "targetsourceiri"));
        annotationDataPositionSelector.setAnnotationId(ResultSetUtils.getString(rs, "annotationid"));
        annotationDataPositionSelector.setCollectionId(ResultSetUtils.getString(rs, "collectionid"));
        annotationDataPositionSelector.setCreatedDateTime(ResultSetUtils.getDate(rs, "createddatetime"));
        annotationDataPositionSelector.setDeleted(ResultSetUtils.getBoolean(rs, "deleted"));
        annotationDataPositionSelector.setEnd(ResultSetUtils.getInt(rs, "end"));
        annotationDataPositionSelector.setJsonMap(ResultSetUtils.getJsonMap(rs, "json"));
        annotationDataPositionSelector.setModifiedDateTime(ResultSetUtils.getDate(rs, "modifieddatetime"));
        annotationDataPositionSelector.setStart(ResultSetUtils.getInt(rs, "start"));
        return annotationDataPositionSelector;
    }
}
