package com.digirati.elucidate.common.test.model.annotation.oa;

import org.junit.Test;

import com.digirati.elucidate.common.model.annotation.oa.OAAnnotationCollection;
import com.digirati.elucidate.common.test.model.annotation.AbstractAnnotationCollectionTest;

public class OAAnnotationCollectionTest extends AbstractAnnotationCollectionTest<OAAnnotationCollection> {

    @Test
    public void testOAAnnotationCollection() {
        OAAnnotationCollection oaAnnotationCollection = new OAAnnotationCollection();
        testAbstractAnnotationCollection(oaAnnotationCollection);
    }
}
