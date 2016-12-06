package com.digirati.elucidate.common.test.model.annotation;

import static org.junit.Assert.assertNotNull;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotationPage;

public class AbstractAnnotationPageTest<T extends AbstractAnnotationPage> extends AbstractObjectTest<T> {

    protected void testAbstractAnnotationPage(T abstractAnnotationPage) {
        assertNotNull(abstractAnnotationPage);
        testAbstractObject(abstractAnnotationPage);
    }
}
