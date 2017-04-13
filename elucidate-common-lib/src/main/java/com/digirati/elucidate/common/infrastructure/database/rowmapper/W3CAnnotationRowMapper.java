package com.digirati.elucidate.common.infrastructure.database.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.digirati.elucidate.common.infrastructure.util.ResultSetUtils;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;

public class W3CAnnotationRowMapper implements RowMapper<W3CAnnotation> {

    @Override
    public W3CAnnotation mapRow(ResultSet rs, int rowNum) throws SQLException {
        W3CAnnotation w3cAnnotation = new W3CAnnotation();
        w3cAnnotation.setPk(ResultSetUtils.getInt(rs, "id"));
        w3cAnnotation.setAnnotationId(ResultSetUtils.getString(rs, "annotationid"));
        w3cAnnotation.setCacheKey(ResultSetUtils.getString(rs, "cachekey"));
        w3cAnnotation.setCollectionId(ResultSetUtils.getString(rs, "collectionid"));
        w3cAnnotation.setCreatedDateTime(ResultSetUtils.getDate(rs, "createddatetime"));
        w3cAnnotation.setDeleted(ResultSetUtils.getBoolean(rs, "deleted"));
        w3cAnnotation.setJsonMap(ResultSetUtils.getJsonMap(rs, "json"));
        w3cAnnotation.setModifiedDateTime(ResultSetUtils.getDate(rs, "modifieddatetime"));
        return w3cAnnotation;
    }
}
