package com.digirati.elucidate.common.test.model.annotation;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;

public abstract class AbstractAnnotationTest<T extends AbstractAnnotation> extends AbstractObjectTest<T> {

    protected void testAbstractAnnotation(T abstractAnnotation) {

        assertNotNull(abstractAnnotation);

        String annotationId = generateRandomId();
        abstractAnnotation.setAnnotationId(annotationId);
        assertThat(annotationId, is(equalTo(abstractAnnotation.getAnnotationId())));

        testAbstractObject(abstractAnnotation);
    }
}
