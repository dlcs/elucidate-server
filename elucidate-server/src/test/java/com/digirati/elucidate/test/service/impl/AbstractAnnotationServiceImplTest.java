package com.digirati.elucidate.test.service.impl;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotationCollection;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.common.service.IRIBuilderService;
import com.digirati.elucidate.common.test.AbstractTest;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.ServiceResponse.Status;
import com.digirati.elucidate.repository.AnnotationStoreRepository;
import com.digirati.elucidate.service.AbstractAnnotationService;

public abstract class AbstractAnnotationServiceImplTest<A extends AbstractAnnotation, C extends AbstractAnnotationCollection> extends AbstractTest {

    private IRIBuilderService iriBuilderService;
    private AnnotationStoreRepository annotationStoreRepository;
    private AbstractAnnotationService<A, C> annotationService;

    protected abstract AbstractAnnotationService<A, C> createAnnotationService(IRIBuilderService iriBuilderService, AnnotationStoreRepository annotationStoreRepository);

    protected abstract void validateConversionToAnnotation(W3CAnnotation w3cAnnotation, A targetAnnotation);

    @Before
    public void before() {
        this.iriBuilderService = mock(IRIBuilderService.class);
        this.annotationStoreRepository = mock(AnnotationStoreRepository.class);
        this.annotationService = createAnnotationService(iriBuilderService, annotationStoreRepository);
    }

    @Test
    public void testGetAnnotationOk() {

        W3CAnnotation w3cAnnotation = generateRandomW3CAnnotation();
        String collectionId = w3cAnnotation.getCollectionId();
        String annotationId = w3cAnnotation.getAnnotationId();

        when(annotationStoreRepository.getAnnotationByCollectionIdAndAnnotationId(collectionId, annotationId)).thenReturn(w3cAnnotation);

        ServiceResponse<A> serviceResponse = annotationService.getAnnotation(collectionId, annotationId);
        assertThat(serviceResponse, is(not(nullValue())));
        assertThat(serviceResponse.getStatus(), is(equalTo(Status.OK)));

        validateConversionToAnnotation(w3cAnnotation, serviceResponse.getObj());
    }

    @Test
    public void testGetAnnotationNotFound() {

        W3CAnnotation w3cAnnotation = generateRandomW3CAnnotation();
        String collectionId = w3cAnnotation.getCollectionId();
        String annotationId = w3cAnnotation.getAnnotationId();

        when(annotationStoreRepository.getAnnotationByCollectionIdAndAnnotationId(collectionId, annotationId)).thenReturn(null);

        ServiceResponse<A> serviceResponse = annotationService.getAnnotation(collectionId, annotationId);
        assertThat(serviceResponse, is(not(nullValue())));
        assertThat(serviceResponse.getStatus(), is(equalTo(Status.NOT_FOUND)));
        assertThat(serviceResponse.getObj(), is(nullValue()));
    }

    @Test
    public void testGetAnnotationDeleted() {

        W3CAnnotation w3cAnnotation = generateRandomW3CAnnotation();
        String collectionId = w3cAnnotation.getCollectionId();
        String annotationId = w3cAnnotation.getAnnotationId();

        when(annotationStoreRepository.countDeletedAnnotations(collectionId, annotationId)).thenReturn(1);
        when(annotationStoreRepository.getAnnotationByCollectionIdAndAnnotationId(collectionId, annotationId)).thenReturn(null);

        ServiceResponse<A> serviceResponse = annotationService.getAnnotation(collectionId, annotationId);
        assertThat(serviceResponse, is(not(nullValue())));
        assertThat(serviceResponse.getStatus(), is(equalTo(Status.DELETED)));
        assertThat(serviceResponse.getObj(), is(nullValue()));
    }

    @Test
    public void testGetAnnotations() {

        String collectionId = generateRandomId();
        List<W3CAnnotation> w3cAnnotations = generateRandomW3CAnnotations(10);

        when(annotationStoreRepository.getAnnotationsByCollectionId(collectionId)).thenReturn(w3cAnnotations);

        ServiceResponse<List<A>> serviceResponse = annotationService.getAnnotations(collectionId);
        assertThat(serviceResponse, is(not(nullValue())));
        assertThat(serviceResponse.getStatus(), is(equalTo(Status.OK)));

        List<A> targetAnnotations = serviceResponse.getObj();
        assertThat(targetAnnotations, is(not(nullValue())));
        assertThat(targetAnnotations.size(), is(equalTo(10)));

        for (int i = 0; i < 10; i++) {
            validateConversionToAnnotation(w3cAnnotations.get(i), targetAnnotations.get(i));
        }
    }

    public void testCreateAnnotation() {

    }

    public void testUpdateAnnotation() {

    }

    public void testDeleteAnnotation() {

    }
}
