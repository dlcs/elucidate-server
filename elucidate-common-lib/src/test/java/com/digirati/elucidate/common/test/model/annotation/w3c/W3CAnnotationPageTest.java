package com.digirati.elucidate.common.test.model.annotation.w3c;

import org.junit.Test;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationPage;
import com.digirati.elucidate.common.test.model.annotation.AbstractAnnotationPageTest;

public class W3CAnnotationPageTest extends AbstractAnnotationPageTest<W3CAnnotationPage> {

    @Test
    public void testW3CAnnotationPage() {
        W3CAnnotationPage w3cAnnotationPage = new W3CAnnotationPage();
        testAbstractAnnotationPage(w3cAnnotationPage);
    }
}
