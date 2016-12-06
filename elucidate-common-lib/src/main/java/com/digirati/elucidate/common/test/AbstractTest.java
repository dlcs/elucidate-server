package com.digirati.elucidate.common.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationCollection;

public abstract class AbstractTest {

    protected W3CAnnotation generateRandomW3CAnnotation() {

        W3CAnnotation w3cAnnotation = new W3CAnnotation();
        w3cAnnotation.setAnnotationId(generateRandomId());
        w3cAnnotation.setCacheKey(generateRandomCacheKey());
        w3cAnnotation.setCollectionId(generateRandomId());
        w3cAnnotation.setCreatedDateTime(generateRandomDate());
        w3cAnnotation.setDeleted(generateRandomBoolean());
        w3cAnnotation.setJsonMap(generateRandomJsonMap());
        w3cAnnotation.setModifiedDateTime(generateRandomDate());
        return w3cAnnotation;
    }

    protected List<W3CAnnotation> generateRandomW3CAnnotations(int quantity) {
        List<W3CAnnotation> w3cAnnotations = new ArrayList<W3CAnnotation>();
        for (int i = 0; i < quantity; i++) {
            w3cAnnotations.add(generateRandomW3CAnnotation());
        }
        return w3cAnnotations;
    }

    protected W3CAnnotationCollection generateRandomW3CAnnotationCollection() {

        W3CAnnotationCollection w3cAnnotationCollection = new W3CAnnotationCollection();
        w3cAnnotationCollection.setCacheKey(generateRandomCacheKey());
        w3cAnnotationCollection.setCollectionId(generateRandomId());
        w3cAnnotationCollection.setCreatedDateTime(generateRandomDate());
        w3cAnnotationCollection.setDeleted(generateRandomBoolean());
        w3cAnnotationCollection.setJsonMap(generateRandomJsonMap());
        w3cAnnotationCollection.setModifiedDateTime(generateRandomDate());
        return w3cAnnotationCollection;
    }

    protected String generateRandomId() {
        return RandomStringUtils.randomAlphanumeric(64);
    }

    protected String generateRandomCacheKey() {
        return RandomStringUtils.randomAlphanumeric(32);
    }

    protected Date generateRandomDate() {
        long beginTime = Timestamp.valueOf("2000-01-01 00:00:00").getTime();
        long endTime = Timestamp.valueOf("2016-12-31 23:59:59").getTime();
        long diff = endTime - beginTime + 1;
        return new Date(beginTime + (long) (Math.random() * diff));
    }

    protected boolean generateRandomBoolean() {
        return Math.random() < 0.5;
    }

    @SuppressWarnings("serial")
    protected Map<String, Object> generateRandomJsonMap() {
        return new HashMap<String, Object>() {
            {
                for (int i = 0; i < 100; i++) {
                    put(RandomStringUtils.randomAlphanumeric(5), RandomStringUtils.randomAlphanumeric(10));
                }
            }
        };
    }
}
