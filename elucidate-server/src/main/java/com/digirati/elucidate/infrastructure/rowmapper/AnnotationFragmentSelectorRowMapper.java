package com.digirati.elucidate.infrastructure.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.digirati.elucidate.common.infrastructure.rowmapper.AbstractRowMapper;
import com.digirati.elucidate.model.annotation.selector.fragment.AnnotationFragmentSelector;

public class AnnotationFragmentSelectorRowMapper extends AbstractRowMapper<AnnotationFragmentSelector> {

    @Override
    public AnnotationFragmentSelector mapRow(ResultSet rs, int rowNum) throws SQLException {
        AnnotationFragmentSelector annotationFragmentSelector = new AnnotationFragmentSelector();
        annotationFragmentSelector.setId(getInt(rs, "id"));
        annotationFragmentSelector.setBodyiri(getString(rs, "bodyiri"));
        annotationFragmentSelector.setBodySourceIri(getString(rs, "bodysourceiri"));
        annotationFragmentSelector.setTargetIri(getString(rs, "targetiri"));
        annotationFragmentSelector.setTargetSourceIri(getString(rs, "targetsourceiri"));
        annotationFragmentSelector.setAnnotationId(getString(rs, "annotationid"));
        annotationFragmentSelector.setCollectionId(getString(rs, "collectionid"));
        annotationFragmentSelector.setConformsTo(getString(rs, "conformsto"));
        annotationFragmentSelector.setCreatedDateTime(getDate(rs, "createddatetime"));
        annotationFragmentSelector.setDeleted(getBoolean(rs, "deleted"));
        annotationFragmentSelector.setJsonMap(getJsonMap(rs, "json"));
        annotationFragmentSelector.setModifiedDateTime(getDate(rs, "modifieddatetime"));
        annotationFragmentSelector.setValue(getString(rs, "value"));
        annotationFragmentSelector.setX(getInt(rs, "x"));
        annotationFragmentSelector.setY(getInt(rs, "y"));
        annotationFragmentSelector.setW(getInt(rs, "w"));
        annotationFragmentSelector.setH(getInt(rs, "h"));
        annotationFragmentSelector.setStart(getInt(rs, "start"));
        annotationFragmentSelector.setEnd(getInt(rs, "end"));
        return annotationFragmentSelector;
    }
}
