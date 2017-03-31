package com.digirati.elucidate.common.infrastructure.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;

public class W3CAnnotationRowMapper extends AbstractRowMapper<W3CAnnotation> {

    @Override
    public W3CAnnotation mapRow(ResultSet rs, int rowNum) throws SQLException {
        W3CAnnotation w3cAnnotation = new W3CAnnotation();
        w3cAnnotation.setId(getInt(rs, "id"));
        w3cAnnotation.setAnnotationId(getString(rs, "annotationid"));
        w3cAnnotation.setCacheKey(getString(rs, "cachekey"));
        w3cAnnotation.setCollectionId(getString(rs, "collectionid"));
        w3cAnnotation.setCreatedDateTime(getDate(rs, "createddatetime"));
        w3cAnnotation.setDeleted(getBoolean(rs, "deleted"));
        w3cAnnotation.setJsonMap(getJsonMap(rs, "json"));
        w3cAnnotation.setModifiedDateTime(getDate(rs, "modifieddatetime"));
        return w3cAnnotation;
    }
}
