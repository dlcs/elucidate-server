package com.digirati.elucidate.common.test.model.annotation;

import static org.junit.Assert.assertNotNull;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotationCollection;

public class AbstractAnnotationCollectionTest<T extends AbstractAnnotationCollection> extends AbstractObjectTest<T> {

    protected void testAbstractAnnotationCollection(T abstractAnnotationCollection) {
        assertNotNull(abstractAnnotationCollection);
        testAbstractObject(abstractAnnotationCollection);
    }
}
