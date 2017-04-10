package com.digirati.elucidate.test.repository.impl;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.AdditionalMatchers.aryEq;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.digirati.elucidate.common.infrastructure.database.rowmapper.W3CAnnotationRowMapper;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.common.test.AbstractTest;
import com.digirati.elucidate.repository.AnnotationStoreRepository;
import com.digirati.elucidate.repository.impl.AnnotationStoreRepositoryJDBCImpl;
import com.github.jsonldjava.utils.JsonUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AnnotationStoreRepositoryJDBCImpl.class)
public class AnnotationStoreRepositoryJDBCImplTest extends AbstractTest {

    private JdbcTemplate jdbcTemplate;
    private AnnotationStoreRepository annotationStoreRepository;

    @Before
    public void before() {
        this.jdbcTemplate = mock(JdbcTemplate.class);
        this.annotationStoreRepository = new AnnotationStoreRepositoryJDBCImpl(jdbcTemplate);
    }

    @Test
    public void testGetAnnotationByCollectionIdAndAnnotationIdEmpty() {

        String collectionId = generateRandomId();
        String annotationId = generateRandomId();
        Object[] params = {collectionId, annotationId, false};
        int[] sqlTypes = {Types.VARCHAR, Types.VARCHAR, Types.BOOLEAN};

        when(jdbcTemplate.query(anyString(), aryEq(params), aryEq(sqlTypes), (W3CAnnotationRowMapper) any())).thenReturn(Collections.emptyList());
        W3CAnnotation w3cAnnotation = annotationStoreRepository.getAnnotationByCollectionIdAndAnnotationId(collectionId, annotationId);
        verify(jdbcTemplate, times(1)).query(anyString(), aryEq(params), aryEq(sqlTypes), (W3CAnnotationRowMapper) any());

        assertThat(w3cAnnotation, is(nullValue()));
    }

    @Test
    @SuppressWarnings("serial")
    public void testGetAnnotationByCollectionIdAndAnnotationIdOne() {

        W3CAnnotation w3cAnnotation = generateRandomW3CAnnotation();

        String collectionId = generateRandomId();
        String annotationId = generateRandomId();
        Object[] params = {collectionId, annotationId, false};
        int[] sqlTypes = {Types.VARCHAR, Types.VARCHAR, Types.BOOLEAN};

        when(jdbcTemplate.query(anyString(), aryEq(params), aryEq(sqlTypes), (W3CAnnotationRowMapper) any())).thenReturn(new ArrayList<W3CAnnotation>() {
            {
                add(w3cAnnotation);
            }
        });
        W3CAnnotation returnedW3CAnnotation = annotationStoreRepository.getAnnotationByCollectionIdAndAnnotationId(collectionId, annotationId);
        verify(jdbcTemplate, times(1)).query(anyString(), aryEq(params), aryEq(sqlTypes), (W3CAnnotationRowMapper) any());

        assertThat(w3cAnnotation, is(equalTo(returnedW3CAnnotation)));
    }

    @SuppressWarnings("serial")
    @Test(expected = IncorrectResultSizeDataAccessException.class)
    public void testGetAnnotationByCollectionIdAndAnnotationIdTwo() {

        W3CAnnotation w3cAnnotation = generateRandomW3CAnnotation();

        String collectionId = generateRandomId();
        String annotationId = generateRandomId();
        Object[] params = {collectionId, annotationId, false};
        int[] sqlTypes = {Types.VARCHAR, Types.VARCHAR, Types.BOOLEAN};

        when(jdbcTemplate.query(anyString(), aryEq(params), aryEq(sqlTypes), (W3CAnnotationRowMapper) any())).thenReturn(new ArrayList<W3CAnnotation>() {
            {
                add(w3cAnnotation);
                add(w3cAnnotation);
            }
        });
        annotationStoreRepository.getAnnotationByCollectionIdAndAnnotationId(collectionId, annotationId);
    }

    @Test
    public void testGetAnnotationsByCollectionIdEmpty() {

        String collectionId = generateRandomId();
        Object[] params = {collectionId, false};
        int[] sqlTypes = {Types.VARCHAR, Types.BOOLEAN};

        when(jdbcTemplate.query(anyString(), aryEq(params), aryEq(sqlTypes), (W3CAnnotationRowMapper) any())).thenReturn(Collections.emptyList());
        List<W3CAnnotation> w3cAnnotations = annotationStoreRepository.getAnnotationsByCollectionId(collectionId);
        verify(jdbcTemplate, times(1)).query(anyString(), aryEq(params), aryEq(sqlTypes), (W3CAnnotationRowMapper) any());

        assertThat(w3cAnnotations.size(), is(equalTo(0)));
    }

    @Test
    @SuppressWarnings("serial")
    public void testGetAnnotationsByCollectionIdMany() {

        W3CAnnotation w3cAnnotationOne = generateRandomW3CAnnotation();
        W3CAnnotation w3cAnnotationTwo = generateRandomW3CAnnotation();
        W3CAnnotation w3cAnnotationThree = generateRandomW3CAnnotation();

        String collectionId = generateRandomId();
        Object[] params = {collectionId, false};
        int[] sqlTypes = {Types.VARCHAR, Types.BOOLEAN};

        when(jdbcTemplate.query(anyString(), aryEq(params), aryEq(sqlTypes), (W3CAnnotationRowMapper) any())).thenReturn(new ArrayList<W3CAnnotation>() {
            {
                add(w3cAnnotationOne);
                add(w3cAnnotationTwo);
                add(w3cAnnotationThree);
            }
        });
        List<W3CAnnotation> w3cAnnotations = annotationStoreRepository.getAnnotationsByCollectionId(collectionId);
        verify(jdbcTemplate, times(1)).query(anyString(), aryEq(params), aryEq(sqlTypes), (W3CAnnotationRowMapper) any());

        assertThat(w3cAnnotations.size(), is(equalTo(3)));
        assertThat(w3cAnnotations.get(0), is(equalTo(w3cAnnotationOne)));
        assertThat(w3cAnnotations.get(1), is(equalTo(w3cAnnotationTwo)));
        assertThat(w3cAnnotations.get(2), is(equalTo(w3cAnnotationThree)));
    }

    @Test
    @SuppressWarnings("serial")
    public void testCreateAnnotation() throws Exception {

        W3CAnnotation w3cAnnotation = generateRandomW3CAnnotation();

        String collectionId = w3cAnnotation.getCollectionId();
        String annotationId = w3cAnnotation.getAnnotationId();
        Map<String, Object> jsonMap = w3cAnnotation.getJsonMap();
        String jsonStr = JsonUtils.toString(jsonMap);

        Object[] params = {collectionId, annotationId, jsonStr};
        int[] sqlTypes = {Types.VARCHAR, Types.VARCHAR, Types.OTHER};
        when(jdbcTemplate.query(anyString(), aryEq(params), aryEq(sqlTypes), (W3CAnnotationRowMapper) any())).thenReturn(new ArrayList<W3CAnnotation>() {
            {
                add(w3cAnnotation);
            }
        });
        W3CAnnotation returnedW3CAnnotation = annotationStoreRepository.createAnnotation(collectionId, annotationId, jsonStr);
        verify(jdbcTemplate, times(1)).query(anyString(), aryEq(params), aryEq(sqlTypes), (W3CAnnotationRowMapper) any());

        assertThat(w3cAnnotation, is(equalTo(returnedW3CAnnotation)));
    }

    @Test
    @SuppressWarnings("serial")
    public void testUpdateAnnotation() throws Exception {

        W3CAnnotation w3cAnnotation = generateRandomW3CAnnotation();

        String collectionId = w3cAnnotation.getCollectionId();
        String annotationId = w3cAnnotation.getAnnotationId();
        Map<String, Object> jsonMap = w3cAnnotation.getJsonMap();
        String jsonStr = JsonUtils.toString(jsonMap);

        Object[] params = {collectionId, annotationId, jsonStr};
        int[] sqlTypes = {Types.VARCHAR, Types.VARCHAR, Types.OTHER};
        when(jdbcTemplate.query(anyString(), aryEq(params), aryEq(sqlTypes), (W3CAnnotationRowMapper) any())).thenReturn(new ArrayList<W3CAnnotation>() {
            {
                add(w3cAnnotation);
            }
        });
        W3CAnnotation returnedW3CAnnotation = annotationStoreRepository.createAnnotation(collectionId, annotationId, jsonStr);
        verify(jdbcTemplate, times(1)).query(anyString(), aryEq(params), aryEq(sqlTypes), (W3CAnnotationRowMapper) any());

        assertThat(w3cAnnotation, is(equalTo(returnedW3CAnnotation)));
    }

    @Test
    @SuppressWarnings("serial")
    public void testDeleteAnnotation() throws Exception {

        W3CAnnotation w3cAnnotation = generateRandomW3CAnnotation();

        String collectionId = w3cAnnotation.getCollectionId();
        String annotationId = w3cAnnotation.getAnnotationId();

        Object[] params = {collectionId, annotationId};
        int[] sqlTypes = {Types.VARCHAR, Types.VARCHAR};
        when(jdbcTemplate.query(anyString(), aryEq(params), aryEq(sqlTypes), (W3CAnnotationRowMapper) any())).thenReturn(new ArrayList<W3CAnnotation>() {
            {
                add(w3cAnnotation);
            }
        });
        W3CAnnotation returnedW3CAnnotation = annotationStoreRepository.deleteAnnotation(collectionId, annotationId);
        verify(jdbcTemplate, times(1)).query(anyString(), aryEq(params), aryEq(sqlTypes), (W3CAnnotationRowMapper) any());

        assertThat(w3cAnnotation, is(equalTo(returnedW3CAnnotation)));
    }

    @Test
    public void testCountDeletedAnnotations() {

        String collectionId = generateRandomId();
        String annotationId = generateRandomId();

        Object[] params = {collectionId, annotationId, true};
        int[] sqlTypes = {Types.VARCHAR, Types.VARCHAR, Types.BOOLEAN};

        when(jdbcTemplate.queryForObject(anyString(), aryEq(params), aryEq(sqlTypes), eq(Integer.class))).thenReturn(3);
        int count = annotationStoreRepository.countDeletedAnnotations(collectionId, annotationId);
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), aryEq(params), aryEq(sqlTypes), eq(Integer.class));

        assertThat(count, is(equalTo(3)));
    }
}
