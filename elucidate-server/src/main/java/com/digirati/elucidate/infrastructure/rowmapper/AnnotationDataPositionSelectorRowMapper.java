package com.digirati.elucidate.infrastructure.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.digirati.elucidate.common.infrastructure.rowmapper.AbstractRowMapper;
import com.digirati.elucidate.model.annotation.selector.dataposition.AnnotationDataPositionSelector;

public class AnnotationDataPositionSelectorRowMapper extends AbstractRowMapper<AnnotationDataPositionSelector> {

    @Override
    public AnnotationDataPositionSelector mapRow(ResultSet rs, int rowNum) throws SQLException {
        AnnotationDataPositionSelector annotationDataPositionSelector = new AnnotationDataPositionSelector();
        annotationDataPositionSelector.setId(getInt(rs, "id"));
        annotationDataPositionSelector.setBodyiri(getString(rs, "bodyiri"));
        annotationDataPositionSelector.setBodySourceIri(getString(rs, "bodysourceiri"));
        annotationDataPositionSelector.setTargetIri(getString(rs, "targetiri"));
        annotationDataPositionSelector.setTargetSourceIri(getString(rs, "targetsourceiri"));
        annotationDataPositionSelector.setAnnotationId(getString(rs, "annotationid"));
        annotationDataPositionSelector.setCollectionId(getString(rs, "collectionid"));
        annotationDataPositionSelector.setCreatedDateTime(getDate(rs, "createddatetime"));
        annotationDataPositionSelector.setDeleted(getBoolean(rs, "deleted"));
        annotationDataPositionSelector.setEnd(getInt(rs, "end"));
        annotationDataPositionSelector.setJsonMap(getJsonMap(rs, "json"));
        annotationDataPositionSelector.setModifiedDateTime(getDate(rs, "modifieddatetime"));
        annotationDataPositionSelector.setStart(getInt(rs, "start"));
        return annotationDataPositionSelector;
    }
}
