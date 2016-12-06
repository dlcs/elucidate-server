package com.digirati.elucidate.common.test.model.annotation.oa;

import org.junit.Test;

import com.digirati.elucidate.common.model.annotation.oa.OAAnnotationPage;
import com.digirati.elucidate.common.test.model.annotation.AbstractAnnotationPageTest;

public class OAAnnotationPageTest extends AbstractAnnotationPageTest<OAAnnotationPage> {

    @Test
    public void testOAAnnotationPage() {
        OAAnnotationPage oaAnnotationPage = new OAAnnotationPage();
        testAbstractAnnotationPage(oaAnnotationPage);
    }
}
