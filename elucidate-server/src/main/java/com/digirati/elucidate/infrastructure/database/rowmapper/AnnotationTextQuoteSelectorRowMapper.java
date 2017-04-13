package com.digirati.elucidate.infrastructure.database.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.digirati.elucidate.common.infrastructure.util.ResultSetUtils;
import com.digirati.elucidate.model.annotation.selector.textquote.AnnotationTextQuoteSelector;

public class AnnotationTextQuoteSelectorRowMapper implements RowMapper<AnnotationTextQuoteSelector> {

    @Override
    public AnnotationTextQuoteSelector mapRow(ResultSet rs, int rowNum) throws SQLException {
        AnnotationTextQuoteSelector annotationTextQuoteSelector = new AnnotationTextQuoteSelector();
        annotationTextQuoteSelector.setPk(ResultSetUtils.getInt(rs, "id"));
        annotationTextQuoteSelector.setBodyiri(ResultSetUtils.getString(rs, "bodyiri"));
        annotationTextQuoteSelector.setBodySourceIri(ResultSetUtils.getString(rs, "bodysourceiri"));
        annotationTextQuoteSelector.setTargetIri(ResultSetUtils.getString(rs, "targetiri"));
        annotationTextQuoteSelector.setTargetSourceIri(ResultSetUtils.getString(rs, "targetsourceiri"));
        annotationTextQuoteSelector.setAnnotationId(ResultSetUtils.getString(rs, "annotationid"));
        annotationTextQuoteSelector.setCollectionId(ResultSetUtils.getString(rs, "collectionid"));
        annotationTextQuoteSelector.setCreatedDateTime(ResultSetUtils.getDate(rs, "modifieddatetime"));
        annotationTextQuoteSelector.setDeleted(ResultSetUtils.getBoolean(rs, "deleted"));
        annotationTextQuoteSelector.setExact(ResultSetUtils.getString(rs, "exact"));
        annotationTextQuoteSelector.setJsonMap(ResultSetUtils.getJsonMap(rs, "json"));
        annotationTextQuoteSelector.setModifiedDateTime(ResultSetUtils.getDate(rs, "modifieddatetime"));
        annotationTextQuoteSelector.setPrefix(ResultSetUtils.getString(rs, "prefix"));
        annotationTextQuoteSelector.setSuffix(ResultSetUtils.getString(rs, "suffix"));
        return annotationTextQuoteSelector;
    }
}
