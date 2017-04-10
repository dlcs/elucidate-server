package com.digirati.elucidate.common.test.infrastructure.rowmapper;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.digirati.elucidate.common.infrastructure.database.rowmapper.W3CAnnotationRowMapper;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.common.test.AbstractTest;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.github.jsonldjava.utils.JsonUtils;

@RunWith(MockitoJUnitRunner.class)
public class W3CAnnotationRowMapperTest extends AbstractTest {

    @Test
    public void testRowMapper() throws SQLException, JsonGenerationException, IOException {

        // Build up our fake data
        String annotationId = generateRandomId();
        String cacheKey = generateRandomCacheKey();
        String collectionId = generateRandomId();
        Date createdDateTime = generateRandomDate();
        boolean deleted = generateRandomBoolean();
        Map<String, Object> jsonMap = generateRandomJsonMap();
        String jsonStr = JsonUtils.toString(jsonMap);
        Date modifiedDateTime = generateRandomDate();;

        // Stub out the result set
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getString("annotationid")).thenReturn(annotationId);
        when(resultSet.getString("cachekey")).thenReturn(cacheKey);
        when(resultSet.getString("collectionid")).thenReturn(collectionId);
        when(resultSet.getTimestamp("createddatetime")).thenReturn(new Timestamp(createdDateTime.getTime()));
        when(resultSet.getBoolean("deleted")).thenReturn(deleted);
        when(resultSet.getString("json")).thenReturn(jsonStr);
        when(resultSet.getTimestamp("modifieddatetime")).thenReturn(new Timestamp(modifiedDateTime.getTime()));

        // Execute the row mapper
        W3CAnnotation w3cAnnotation = new W3CAnnotationRowMapper().mapRow(resultSet, 0);

        // Verify the W3CAnnotation fields
        assertThat(annotationId, is(equalTo(w3cAnnotation.getAnnotationId())));
        assertThat(cacheKey, is(equalTo(w3cAnnotation.getCacheKey())));
        assertThat(collectionId, is(equalTo(w3cAnnotation.getCollectionId())));
        assertThat(createdDateTime, is(equalTo(w3cAnnotation.getCreatedDateTime())));
        assertThat(deleted, is(equalTo(w3cAnnotation.isDeleted())));
        assertThat(jsonMap, is(equalTo(w3cAnnotation.getJsonMap())));
        assertThat(modifiedDateTime, is(equalTo(w3cAnnotation.getModifiedDateTime())));
    }
}
