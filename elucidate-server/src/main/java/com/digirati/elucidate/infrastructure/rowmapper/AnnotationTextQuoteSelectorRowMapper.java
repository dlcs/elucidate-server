package com.digirati.elucidate.infrastructure.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.digirati.elucidate.common.infrastructure.rowmapper.AbstractRowMapper;
import com.digirati.elucidate.model.annotation.selector.textquote.AnnotationTextQuoteSelector;

public class AnnotationTextQuoteSelectorRowMapper extends AbstractRowMapper<AnnotationTextQuoteSelector> {

    @Override
    public AnnotationTextQuoteSelector mapRow(ResultSet rs, int rowNum) throws SQLException {
        AnnotationTextQuoteSelector annotationTextQuoteSelector = new AnnotationTextQuoteSelector();
        annotationTextQuoteSelector.setId(getInt(rs, "id"));
        annotationTextQuoteSelector.setBodyiri(getString(rs, "bodyiri"));
        annotationTextQuoteSelector.setBodySourceIri(getString(rs, "bodysourceiri"));
        annotationTextQuoteSelector.setTargetIri(getString(rs, "targetiri"));
        annotationTextQuoteSelector.setTargetSourceIri(getString(rs, "targetsourceiri"));
        annotationTextQuoteSelector.setAnnotationId(getString(rs, "annotationid"));
        annotationTextQuoteSelector.setCollectionId(getString(rs, "collectionid"));
        annotationTextQuoteSelector.setCreatedDateTime(getDate(rs, "modifieddatetime"));
        annotationTextQuoteSelector.setDeleted(getBoolean(rs, "deleted"));
        annotationTextQuoteSelector.setExact(getString(rs, "exact"));
        annotationTextQuoteSelector.setJsonMap(getJsonMap(rs, "json"));
        annotationTextQuoteSelector.setModifiedDateTime(getDate(rs, "modifieddatetime"));
        annotationTextQuoteSelector.setPrefix(getString(rs, "prefix"));
        annotationTextQuoteSelector.setSuffix(getString(rs, "suffix"));
        return annotationTextQuoteSelector;
    }
}
