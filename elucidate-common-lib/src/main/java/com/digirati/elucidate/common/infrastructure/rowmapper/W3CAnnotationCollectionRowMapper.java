package com.digirati.elucidate.common.infrastructure.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationCollection;

public class W3CAnnotationCollectionRowMapper extends AbstractRowMapper<W3CAnnotationCollection> {

    @Override
    public W3CAnnotationCollection mapRow(ResultSet rs, int rowNum) throws SQLException {
        W3CAnnotationCollection w3cAnnotationCollection = new W3CAnnotationCollection();
        w3cAnnotationCollection.setId(getInt(rs, "id"));
        w3cAnnotationCollection.setCacheKey(getString(rs, "cachekey"));
        w3cAnnotationCollection.setCollectionId(getString(rs, "collectionid"));
        w3cAnnotationCollection.setCreatedDateTime(getDate(rs, "createddatetime"));
        w3cAnnotationCollection.setDeleted(getBoolean(rs, "deleted"));
        w3cAnnotationCollection.setJsonMap(getJsonMap(rs, "json"));
        w3cAnnotationCollection.setModifiedDateTime(getDate(rs, "modifieddatetime"));
        return w3cAnnotationCollection;
    }
}
