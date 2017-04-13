package com.digirati.elucidate.infrastructure.database.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.digirati.elucidate.common.infrastructure.util.ResultSetUtils;
import com.digirati.elucidate.model.annotation.selector.fragment.AnnotationFragmentSelector;

public class AnnotationFragmentSelectorRowMapper implements RowMapper<AnnotationFragmentSelector> {

    @Override
    public AnnotationFragmentSelector mapRow(ResultSet rs, int rowNum) throws SQLException {
        AnnotationFragmentSelector annotationFragmentSelector = new AnnotationFragmentSelector();
        annotationFragmentSelector.setPk(ResultSetUtils.getInt(rs, "id"));
        annotationFragmentSelector.setBodyiri(ResultSetUtils.getString(rs, "bodyiri"));
        annotationFragmentSelector.setBodySourceIri(ResultSetUtils.getString(rs, "bodysourceiri"));
        annotationFragmentSelector.setTargetIri(ResultSetUtils.getString(rs, "targetiri"));
        annotationFragmentSelector.setTargetSourceIri(ResultSetUtils.getString(rs, "targetsourceiri"));
        annotationFragmentSelector.setAnnotationId(ResultSetUtils.getString(rs, "annotationid"));
        annotationFragmentSelector.setCollectionId(ResultSetUtils.getString(rs, "collectionid"));
        annotationFragmentSelector.setConformsTo(ResultSetUtils.getString(rs, "conformsto"));
        annotationFragmentSelector.setCreatedDateTime(ResultSetUtils.getDate(rs, "createddatetime"));
        annotationFragmentSelector.setDeleted(ResultSetUtils.getBoolean(rs, "deleted"));
        annotationFragmentSelector.setJsonMap(ResultSetUtils.getJsonMap(rs, "json"));
        annotationFragmentSelector.setModifiedDateTime(ResultSetUtils.getDate(rs, "modifieddatetime"));
        annotationFragmentSelector.setValue(ResultSetUtils.getString(rs, "value"));
        annotationFragmentSelector.setX(ResultSetUtils.getInt(rs, "x"));
        annotationFragmentSelector.setY(ResultSetUtils.getInt(rs, "y"));
        annotationFragmentSelector.setW(ResultSetUtils.getInt(rs, "w"));
        annotationFragmentSelector.setH(ResultSetUtils.getInt(rs, "h"));
        annotationFragmentSelector.setStart(ResultSetUtils.getInt(rs, "start"));
        annotationFragmentSelector.setEnd(ResultSetUtils.getInt(rs, "end"));
        return annotationFragmentSelector;
    }
}
