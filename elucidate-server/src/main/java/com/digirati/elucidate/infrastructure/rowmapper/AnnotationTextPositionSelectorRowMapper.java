package com.digirati.elucidate.infrastructure.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.digirati.elucidate.common.infrastructure.rowmapper.AbstractRowMapper;
import com.digirati.elucidate.model.annotation.selector.textposition.AnnotationTextPositionSelector;

public class AnnotationTextPositionSelectorRowMapper extends AbstractRowMapper<AnnotationTextPositionSelector> {

    @Override
    public AnnotationTextPositionSelector mapRow(ResultSet rs, int rowNum) throws SQLException {
        AnnotationTextPositionSelector annotationTextPositionSelector = new AnnotationTextPositionSelector();
        annotationTextPositionSelector.setId(getInt(rs, "id"));
        annotationTextPositionSelector.setBodyiri(getString(rs, "bodyiri"));
        annotationTextPositionSelector.setBodySourceIri(getString(rs, "bodysourceiri"));
        annotationTextPositionSelector.setTargetIri(getString(rs, "targetiri"));
        annotationTextPositionSelector.setTargetSourceIri(getString(rs, "targetsourceiri"));
        annotationTextPositionSelector.setAnnotationId(getString(rs, "annotationid"));
        annotationTextPositionSelector.setCollectionId(getString(rs, "collectionid"));
        annotationTextPositionSelector.setCreatedDateTime(getDate(rs, "createddatetime"));
        annotationTextPositionSelector.setDeleted(getBoolean(rs, "deleted"));
        annotationTextPositionSelector.setEnd(getInt(rs, "end"));
        annotationTextPositionSelector.setJsonMap(getJsonMap(rs, "json"));
        annotationTextPositionSelector.setModifiedDateTime(getDate(rs, "modifieddatetime"));
        annotationTextPositionSelector.setStart(getInt(rs, "start"));
        return annotationTextPositionSelector;
    }
}
