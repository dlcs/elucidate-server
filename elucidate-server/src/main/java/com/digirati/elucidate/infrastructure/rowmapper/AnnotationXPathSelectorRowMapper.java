package com.digirati.elucidate.infrastructure.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.digirati.elucidate.common.infrastructure.rowmapper.AbstractRowMapper;
import com.digirati.elucidate.model.annotation.selector.xpath.AnnotationXPathSelector;

public class AnnotationXPathSelectorRowMapper extends AbstractRowMapper<AnnotationXPathSelector> {

    @Override
    public AnnotationXPathSelector mapRow(ResultSet rs, int rowNum) throws SQLException {
        AnnotationXPathSelector annotationXPathSelector = new AnnotationXPathSelector();
        annotationXPathSelector.setId(getInt(rs, "id"));
        annotationXPathSelector.setBodyiri(getString(rs, "bodyiri"));
        annotationXPathSelector.setBodySourceIri(getString(rs, "bodysourceiri"));
        annotationXPathSelector.setTargetIri(getString(rs, "targetiri"));
        annotationXPathSelector.setTargetSourceIri(getString(rs, "targetsourceiri"));
        annotationXPathSelector.setAnnotationId(getString(rs, "annotationid"));
        annotationXPathSelector.setCollectionId(getString(rs, "collectionid"));
        annotationXPathSelector.setCreatedDateTime(getDate(rs, "createddatetime"));
        annotationXPathSelector.setDeleted(getBoolean(rs, "deleted"));
        annotationXPathSelector.setJsonMap(getJsonMap(rs, "json"));
        annotationXPathSelector.setModifiedDateTime(getDate(rs, "modifieddatetime"));
        annotationXPathSelector.setValue(getString(rs, "value"));
        return annotationXPathSelector;
    }
}
