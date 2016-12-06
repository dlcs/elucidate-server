package com.digirati.elucidate.test.model.enumeration;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.digirati.elucidate.model.enumeration.ClientPreference;

public class ClientPreferenceTest {

    @Test
    public void testClientPreferenceMinimalContainer() {
        ClientPreference clientPreference = ClientPreference.MINIMAL_CONTAINER;
        assertNotNull(clientPreference);
        assertThat(clientPreference, is(equalTo(ClientPreference.MINIMAL_CONTAINER)));
    }

    @Test
    public void testClientPreferenceContainedIris() {
        ClientPreference clientPreference = ClientPreference.CONTAINED_IRIS;
        assertNotNull(clientPreference);
        assertThat(clientPreference, is(equalTo(ClientPreference.CONTAINED_IRIS)));
    }

    @Test
    public void testClientPreferenceContainedDescriptions() {
        ClientPreference clientPreference = ClientPreference.CONTAINED_DESCRIPTIONS;
        assertNotNull(clientPreference);
        assertThat(clientPreference, is(equalTo(ClientPreference.CONTAINED_DESCRIPTIONS)));
    }
}
