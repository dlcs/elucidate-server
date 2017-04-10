package com.digirati.elucidate.infrastructure.database.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.digirati.elucidate.common.infrastructure.util.ResultSetUtils;
import com.digirati.elucidate.model.annotation.selector.svg.AnnotationSVGSelector;

public class AnnotationSVGSelectorRowMapper implements RowMapper<AnnotationSVGSelector> {

    @Override
    public AnnotationSVGSelector mapRow(ResultSet rs, int rowNum) throws SQLException {
        AnnotationSVGSelector annotationSvgSelector = new AnnotationSVGSelector();
        annotationSvgSelector.setId(ResultSetUtils.getInt(rs, "id"));
        annotationSvgSelector.setBodyiri(ResultSetUtils.getString(rs, "bodyiri"));
        annotationSvgSelector.setBodySourceIri(ResultSetUtils.getString(rs, "bodysourceiri"));
        annotationSvgSelector.setTargetIri(ResultSetUtils.getString(rs, "targetiri"));
        annotationSvgSelector.setTargetSourceIri(ResultSetUtils.getString(rs, "targetsourceiri"));
        annotationSvgSelector.setAnnotationId(ResultSetUtils.getString(rs, "annotationid"));
        annotationSvgSelector.setCollectionId(ResultSetUtils.getString(rs, "collectionid"));
        annotationSvgSelector.setCreatedDateTime(ResultSetUtils.getDate(rs, "createddatetime"));
        annotationSvgSelector.setDeleted(ResultSetUtils.getBoolean(rs, "deleted"));
        annotationSvgSelector.setJsonMap(ResultSetUtils.getJsonMap(rs, "json"));
        annotationSvgSelector.setModifiedDateTime(ResultSetUtils.getDate(rs, "modifieddatetime"));
        annotationSvgSelector.setValue(ResultSetUtils.getString(rs, "value"));
        return annotationSvgSelector;
    }
}
