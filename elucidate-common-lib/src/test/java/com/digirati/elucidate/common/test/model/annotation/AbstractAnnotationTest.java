package com.digirati.elucidate.common.test.model.annotation;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;

public abstract class AbstractAnnotationTest<T extends AbstractAnnotation> extends AbstractObjectTest<T> {

    protected void testAbstractAnnotation(T abstractAnnotation) {

        assertNotNull(abstractAnnotation);

        String cacheKey = generateRandomCacheKey();
        abstractAnnotation.setCacheKey(cacheKey);
        assertThat(cacheKey, is(equalTo(abstractAnnotation.getCacheKey())));

        String collectionId = generateRandomId();
        abstractAnnotation.setCollectionId(collectionId);
        assertThat(collectionId, is(equalTo(abstractAnnotation.getCollectionId())));

        String annotationId = generateRandomId();
        abstractAnnotation.setAnnotationId(annotationId);
        assertThat(annotationId, is(equalTo(abstractAnnotation.getAnnotationId())));

        testAbstractObject(abstractAnnotation);
    }
}
