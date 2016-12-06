package com.digirati.elucidate.common.test.model.annotation.w3c;

import org.junit.Test;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationCollection;
import com.digirati.elucidate.common.test.model.annotation.AbstractAnnotationCollectionTest;

public class W3CAnnotationCollectionTest extends AbstractAnnotationCollectionTest<W3CAnnotationCollection> {

    @Test
    public void testW3CAnnotationCollection() {
        W3CAnnotationCollection w3cAnnotationCollection = new W3CAnnotationCollection();
        testAbstractAnnotationCollection(w3cAnnotationCollection);
    }
}
