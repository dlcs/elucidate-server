package com.digirati.elucidate.infrastructure.database.rowmapper;

import com.digirati.elucidate.common.infrastructure.util.ResultSetUtils;
import com.digirati.elucidate.model.annotation.temporal.AnnotationTemporal;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AnnotationTemporalRowMapper implements RowMapper<AnnotationTemporal> {

    @Override
    public AnnotationTemporal mapRow(ResultSet rs, int rowNum) throws SQLException {
        AnnotationTemporal annotationTemporal = new AnnotationTemporal();
        annotationTemporal.setPk(ResultSetUtils.getInt(rs, "id"));
        annotationTemporal.setType(ResultSetUtils.getString(rs, "type"));
        annotationTemporal.setValue(ResultSetUtils.getDate(rs, "value"));
        annotationTemporal.setJsonMap(ResultSetUtils.getJsonMap(rs, "json"));
        annotationTemporal.setCreatedDateTime(ResultSetUtils.getDate(rs, "createddatetime"));
        annotationTemporal.setModifiedDateTime(ResultSetUtils.getDate(rs, "modifieddatetime"));
        annotationTemporal.setDeleted(ResultSetUtils.getBoolean(rs, "deleted"));
        return annotationTemporal;
    }
}
