package com.digirati.elucidate.common.test.model.annotation.w3c;

import org.junit.Test;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.common.test.model.annotation.AbstractAnnotationTest;

public class W3CAnnotationTest extends AbstractAnnotationTest<W3CAnnotation> {

    @Test
    public void testW3CAnnotation() {
        W3CAnnotation w3cAnnotation = new W3CAnnotation();
        testAbstractAnnotation(w3cAnnotation);
    }
}
