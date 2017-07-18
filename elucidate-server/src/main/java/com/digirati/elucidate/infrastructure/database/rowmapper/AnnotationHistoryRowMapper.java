package com.digirati.elucidate.infrastructure.database.rowmapper;

import com.digirati.elucidate.common.infrastructure.util.ResultSetUtils;
import com.digirati.elucidate.model.annotation.history.AnnotationHistory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AnnotationHistoryRowMapper implements RowMapper<AnnotationHistory> {

    @Override
    public AnnotationHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
        AnnotationHistory annotationHistory = new AnnotationHistory();
        annotationHistory.setPk(ResultSetUtils.getInt(rs, "id"));
        annotationHistory.setAnnotationId(ResultSetUtils.getString(rs, "annotationid"));
        annotationHistory.setCollectionId(ResultSetUtils.getString(rs, "collectionid"));
        annotationHistory.setVersion(ResultSetUtils.getInt(rs, "version"));
        annotationHistory.setCreatedDateTime(ResultSetUtils.getDate(rs, "createddatetime"));
        annotationHistory.setJsonMap(ResultSetUtils.getJsonMap(rs, "json"));
        return annotationHistory;
    }
}
