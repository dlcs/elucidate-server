package com.digirati.elucidate.test.repository.impl;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.AdditionalMatchers.aryEq;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.digirati.elucidate.common.infrastructure.database.rowmapper.W3CAnnotationCollectionRowMapper;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationCollection;
import com.digirati.elucidate.common.test.AbstractTest;
import com.digirati.elucidate.repository.AnnotationCollectionStoreRepository;
import com.digirati.elucidate.repository.impl.AnnotationCollectionStoreRepositoryJDBCImpl;
import com.github.jsonldjava.utils.JsonUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AnnotationCollectionStoreRepositoryJDBCImpl.class)
public class AnnotationCollectionStoreRepositoryJDBCImplTest extends AbstractTest {

    private JdbcTemplate jdbcTemplate;
    private AnnotationCollectionStoreRepository annotationCollectionStoreRepository;

    @Before
    public void before() {
        this.jdbcTemplate = mock(JdbcTemplate.class);
        this.annotationCollectionStoreRepository = new AnnotationCollectionStoreRepositoryJDBCImpl(jdbcTemplate);
    }

    @Test
    public void testGetAnnotationCollectionByIdEmpty() {

        String collectionId = generateRandomId();
        Object[] params = {collectionId, false};
        int[] sqlTypes = {Types.VARCHAR, Types.BOOLEAN};

        when(jdbcTemplate.query(anyString(), aryEq(params), aryEq(sqlTypes), (W3CAnnotationCollectionRowMapper) any())).thenReturn(Collections.emptyList());
        W3CAnnotationCollection w3cAnnotationCollection = annotationCollectionStoreRepository.getAnnotationCollectionById(collectionId);
        verify(jdbcTemplate, times(1)).query(anyString(), aryEq(params), aryEq(sqlTypes), (W3CAnnotationCollectionRowMapper) any());

        assertThat(w3cAnnotationCollection, is(nullValue()));
    }

    @Test
    @SuppressWarnings("serial")
    public void testGetAnnotationCollectionByIdOne() {

        W3CAnnotationCollection w3cAnnotationCollection = generateRandomW3CAnnotationCollection();

        String collectionId = w3cAnnotationCollection.getCollectionId();
        Object[] params = {collectionId, false};
        int[] sqlTypes = {Types.VARCHAR, Types.BOOLEAN};

        when(jdbcTemplate.query(anyString(), aryEq(params), aryEq(sqlTypes), (W3CAnnotationCollectionRowMapper) any())).thenReturn(new ArrayList<W3CAnnotationCollection>() {
            {
                add(w3cAnnotationCollection);
            }
        });
        W3CAnnotationCollection returnedW3CAnnotationCollection = annotationCollectionStoreRepository.getAnnotationCollectionById(collectionId);
        verify(jdbcTemplate, times(1)).query(anyString(), aryEq(params), aryEq(sqlTypes), (W3CAnnotationCollectionRowMapper) any());

        assertThat(w3cAnnotationCollection, is(equalTo(returnedW3CAnnotationCollection)));
    }

    @Test(expected = IncorrectResultSizeDataAccessException.class)
    @SuppressWarnings("serial")
    public void testGetAnnotationCollectionByIdTwo() {

        W3CAnnotationCollection w3cAnnotationCollection = generateRandomW3CAnnotationCollection();

        String collectionId = w3cAnnotationCollection.getCollectionId();
        Object[] params = {collectionId, false};
        int[] sqlTypes = {Types.VARCHAR, Types.BOOLEAN};

        when(jdbcTemplate.query(anyString(), aryEq(params), aryEq(sqlTypes), (W3CAnnotationCollectionRowMapper) any())).thenReturn(new ArrayList<W3CAnnotationCollection>() {
            {
                add(w3cAnnotationCollection);
                add(w3cAnnotationCollection);
            }
        });
        annotationCollectionStoreRepository.getAnnotationCollectionById(collectionId);
    }

    @Test
    @SuppressWarnings("serial")
    public void testCreateAnnotationCollection() throws Exception {

        W3CAnnotationCollection w3cAnnotationCollection = generateRandomW3CAnnotationCollection();

        String collectionId = w3cAnnotationCollection.getCollectionId();
        Map<String, Object> jsonMap = w3cAnnotationCollection.getJsonMap();
        String jsonStr = JsonUtils.toString(jsonMap);

        Object[] params = {collectionId, jsonStr};
        int[] sqlTypes = {Types.VARCHAR, Types.OTHER};
        when(jdbcTemplate.query(anyString(), aryEq(params), aryEq(sqlTypes), (W3CAnnotationCollectionRowMapper) any())).thenReturn(new ArrayList<W3CAnnotationCollection>() {
            {
                add(w3cAnnotationCollection);
            }
        });
        W3CAnnotationCollection returnedW3CAnnotationCollection = annotationCollectionStoreRepository.createAnnotationCollection(collectionId, jsonStr);
        verify(jdbcTemplate, times(1)).query(anyString(), aryEq(params), aryEq(sqlTypes), (W3CAnnotationCollectionRowMapper) any());

        assertThat(w3cAnnotationCollection, is(equalTo(returnedW3CAnnotationCollection)));
    }
}
