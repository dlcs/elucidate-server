package com.digirati.elucidate.common.infrastructure.database.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.digirati.elucidate.common.infrastructure.util.ResultSetUtils;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationCollection;

public class W3CAnnotationCollectionRowMapper implements RowMapper<W3CAnnotationCollection> {

    @Override
    public W3CAnnotationCollection mapRow(ResultSet rs, int rowNum) throws SQLException {
        W3CAnnotationCollection w3cAnnotationCollection = new W3CAnnotationCollection();
        w3cAnnotationCollection.setPk(ResultSetUtils.getInt(rs, "id"));
        w3cAnnotationCollection.setCacheKey(ResultSetUtils.getString(rs, "cachekey"));
        w3cAnnotationCollection.setCollectionId(ResultSetUtils.getString(rs, "collectionid"));
        w3cAnnotationCollection.setCreatedDateTime(ResultSetUtils.getDate(rs, "createddatetime"));
        w3cAnnotationCollection.setDeleted(ResultSetUtils.getBoolean(rs, "deleted"));
        w3cAnnotationCollection.setJsonMap(ResultSetUtils.getJsonMap(rs, "json"));
        w3cAnnotationCollection.setModifiedDateTime(ResultSetUtils.getDate(rs, "modifieddatetime"));
        return w3cAnnotationCollection;
    }
}
