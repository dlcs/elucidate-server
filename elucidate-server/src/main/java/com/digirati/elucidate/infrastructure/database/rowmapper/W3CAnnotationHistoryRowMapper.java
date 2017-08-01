package com.digirati.elucidate.infrastructure.database.rowmapper;

import com.digirati.elucidate.common.infrastructure.util.ResultSetUtils;
import com.digirati.elucidate.model.annotation.history.W3CAnnotationHistory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class W3CAnnotationHistoryRowMapper implements RowMapper<W3CAnnotationHistory> {

    @Override
    public W3CAnnotationHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
        W3CAnnotationHistory w3cAnnotationHistory = new W3CAnnotationHistory();
        w3cAnnotationHistory.setPk(ResultSetUtils.getInt(rs, "id"));
        w3cAnnotationHistory.setAnnotationId(ResultSetUtils.getString(rs, "annotationid"));
        w3cAnnotationHistory.setCollectionId(ResultSetUtils.getString(rs, "collectionid"));
        w3cAnnotationHistory.setVersion(ResultSetUtils.getInt(rs, "version"));
        w3cAnnotationHistory.setCreatedDateTime(ResultSetUtils.getDate(rs, "createddatetime"));
        w3cAnnotationHistory.setModifiedDateTime(ResultSetUtils.getDate(rs, "modifieddatetime"));
        w3cAnnotationHistory.setJsonMap(ResultSetUtils.getJsonMap(rs, "json"));
        w3cAnnotationHistory.setDeleted(ResultSetUtils.getBoolean(rs, "deleted"));
        return w3cAnnotationHistory;
    }
}
