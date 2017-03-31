package com.digirati.elucidate.infrastructure.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.digirati.elucidate.common.infrastructure.rowmapper.AbstractRowMapper;
import com.digirati.elucidate.model.annotation.selector.svg.AnnotationSVGSelector;

public class AnnotationSVGSelectorRowMapper extends AbstractRowMapper<AnnotationSVGSelector> {

    @Override
    public AnnotationSVGSelector mapRow(ResultSet rs, int rowNum) throws SQLException {
        AnnotationSVGSelector annotationSvgSelector = new AnnotationSVGSelector();
        annotationSvgSelector.setId(getInt(rs, "id"));
        annotationSvgSelector.setBodyiri(getString(rs, "bodyiri"));
        annotationSvgSelector.setBodySourceIri(getString(rs, "bodysourceiri"));
        annotationSvgSelector.setTargetIri(getString(rs, "targetiri"));
        annotationSvgSelector.setTargetSourceIri(getString(rs, "targetsourceiri"));
        annotationSvgSelector.setAnnotationId(getString(rs, "annotationid"));
        annotationSvgSelector.setCollectionId(getString(rs, "collectionid"));
        annotationSvgSelector.setCreatedDateTime(getDate(rs, "createddatetime"));
        annotationSvgSelector.setDeleted(getBoolean(rs, "deleted"));
        annotationSvgSelector.setJsonMap(getJsonMap(rs, "json"));
        annotationSvgSelector.setModifiedDateTime(getDate(rs, "modifieddatetime"));
        annotationSvgSelector.setValue(getString(rs, "value"));
        return annotationSvgSelector;
    }
}
