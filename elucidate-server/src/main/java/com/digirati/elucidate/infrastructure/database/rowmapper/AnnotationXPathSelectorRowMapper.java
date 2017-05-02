package com.digirati.elucidate.infrastructure.database.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.digirati.elucidate.common.infrastructure.util.ResultSetUtils;
import com.digirati.elucidate.model.annotation.selector.xpath.AnnotationXPathSelector;

public class AnnotationXPathSelectorRowMapper implements RowMapper<AnnotationXPathSelector> {

    @Override
    public AnnotationXPathSelector mapRow(ResultSet rs, int rowNum) throws SQLException {
        AnnotationXPathSelector annotationXPathSelector = new AnnotationXPathSelector();
        annotationXPathSelector.setPk(ResultSetUtils.getInt(rs, "id"));
        annotationXPathSelector.setBodyiri(ResultSetUtils.getString(rs, "bodyiri"));
        annotationXPathSelector.setBodySourceIri(ResultSetUtils.getString(rs, "bodysourceiri"));
        annotationXPathSelector.setTargetIri(ResultSetUtils.getString(rs, "targetiri"));
        annotationXPathSelector.setTargetSourceIri(ResultSetUtils.getString(rs, "targetsourceiri"));
        annotationXPathSelector.setAnnotationId(ResultSetUtils.getString(rs, "annotationid"));
        annotationXPathSelector.setCollectionId(ResultSetUtils.getString(rs, "collectionid"));
        annotationXPathSelector.setCreatedDateTime(ResultSetUtils.getDate(rs, "createddatetime"));
        annotationXPathSelector.setDeleted(ResultSetUtils.getBoolean(rs, "deleted"));
        annotationXPathSelector.setJsonMap(ResultSetUtils.getJsonMap(rs, "json"));
        annotationXPathSelector.setModifiedDateTime(ResultSetUtils.getDate(rs, "modifieddatetime"));
        annotationXPathSelector.setValue(ResultSetUtils.getString(rs, "value"));
        return annotationXPathSelector;
    }
}
