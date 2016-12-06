package com.digirati.elucidate.common.test.model.annotation.oa;

import org.junit.Test;

import com.digirati.elucidate.common.model.annotation.oa.OAAnnotation;
import com.digirati.elucidate.common.test.model.annotation.AbstractAnnotationTest;

public class OAAnnotationTest extends AbstractAnnotationTest<OAAnnotation> {

    @Test
    public void testOAAnnotation() {
        OAAnnotation oaAnnotation = new OAAnnotation();
        testAbstractAnnotation(oaAnnotation);
    }
}
