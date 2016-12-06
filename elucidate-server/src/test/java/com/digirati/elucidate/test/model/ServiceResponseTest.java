package com.digirati.elucidate.test.model;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.common.test.AbstractTest;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.ServiceResponse.Status;

public class ServiceResponseTest extends AbstractTest {

    @Test
    public void testOk() {
        Status status = Status.OK;
        assertNotNull(status);
        assertThat(status, is(equalTo(Status.OK)));
    }

    @Test
    public void testNotFound() {
        Status status = Status.NOT_FOUND;
        assertNotNull(status);
        assertThat(status, is(equalTo(Status.NOT_FOUND)));
    }

    @Test
    public void testCacheKeyMiss() {
        Status status = Status.CACHE_KEY_MISS;
        assertNotNull(status);
        assertThat(status, is(equalTo(Status.CACHE_KEY_MISS)));
    }

    @Test
    public void testIllegalModification() {
        Status status = Status.ILLEGAL_MODIFICATION;
        assertNotNull(status);
        assertThat(status, is(equalTo(Status.ILLEGAL_MODIFICATION)));
    }

    @Test
    public void testNonConformant() {
        Status status = Status.NON_CONFORMANT;
        assertNotNull(status);
        assertThat(status, is(equalTo(Status.NON_CONFORMANT)));
    }

    @Test
    public void testDeleted() {
        Status status = Status.DELETED;
        assertNotNull(status);
        assertThat(status, is(equalTo(Status.DELETED)));
    }

    @Test
    public void testAlreadyExists() {
        Status status = Status.ALREADY_EXISTS;
        assertNotNull(status);
        assertThat(status, is(equalTo(Status.ALREADY_EXISTS)));
    }

    @Test
    public void testServiceResponse() {

        for (Status status : Status.values()) {

            W3CAnnotation w3cAnnotation = generateRandomW3CAnnotation();

            ServiceResponse<W3CAnnotation> serviceResponse = new ServiceResponse<W3CAnnotation>(status, w3cAnnotation);

            assertThat(w3cAnnotation, is(equalTo(serviceResponse.getObj())));
            assertThat(status, is(equalTo(serviceResponse.getStatus())));
        }
    }
}
