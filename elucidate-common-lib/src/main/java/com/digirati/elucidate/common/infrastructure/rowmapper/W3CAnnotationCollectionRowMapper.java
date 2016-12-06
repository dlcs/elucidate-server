package com.digirati.elucidate.common.infrastructure.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationCollection;

public class W3CAnnotationCollectionRowMapper extends AbstractRowMapper<W3CAnnotationCollection> {

    @Override
    public W3CAnnotationCollection mapRow(ResultSet rs, int rowNum) throws SQLException {
        W3CAnnotationCollection annotationCollection = new W3CAnnotationCollection();
        annotationCollection.setCacheKey(getString(rs, "cachekey"));
        annotationCollection.setCollectionId(getString(rs, "collectionid"));
        annotationCollection.setCreatedDateTime(getDate(rs, "createddatetime"));
        annotationCollection.setDeleted(getBoolean(rs, "deleted"));
        annotationCollection.setJsonMap(getJsonMap(rs, "json"));
        annotationCollection.setModifiedDateTime(getDate(rs, "modifieddatetime"));
        return annotationCollection;
    }
}
