package com.digirati.elucidate.infrastructure.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.digirati.elucidate.common.infrastructure.rowmapper.AbstractRowMapper;
import com.digirati.elucidate.model.annotation.selector.css.AnnotationCSSSelector;

public class AnnotationCSSSelectorRowMapper extends AbstractRowMapper<AnnotationCSSSelector> {

    @Override
    public AnnotationCSSSelector mapRow(ResultSet rs, int rowNum) throws SQLException {
        AnnotationCSSSelector annotationCssSelector = new AnnotationCSSSelector();
        annotationCssSelector.setId(getInt(rs, "id"));
        annotationCssSelector.setBodyiri(getString(rs, "bodyiri"));
        annotationCssSelector.setBodySourceIri(getString(rs, "bodysourceiri"));
        annotationCssSelector.setTargetIri(getString(rs, "targetiri"));
        annotationCssSelector.setTargetSourceIri(getString(rs, "targetsourceiri"));
        annotationCssSelector.setAnnotationId(getString(rs, "annotationid"));
        annotationCssSelector.setCollectionId(getString(rs, "collectionid"));
        annotationCssSelector.setCreatedDateTime(getDate(rs, "createddatetime"));
        annotationCssSelector.setDeleted(getBoolean(rs, "deleted"));
        annotationCssSelector.setJsonMap(getJsonMap(rs, "json"));
        annotationCssSelector.setModifiedDateTime(getDate(rs, "modifieddatetime"));
        annotationCssSelector.setValue(getString(rs, "value"));
        return annotationCssSelector;
    }
}
