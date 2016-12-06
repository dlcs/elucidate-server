package com.digirati.elucidate.test.model;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.digirati.elucidate.common.infrastructure.constants.JSONLDConstants;
import com.digirati.elucidate.model.JSONLDProfile;
import com.digirati.elucidate.model.JSONLDProfile.Format;

public class JSONLDProfileTest {

    private static final String TEST_CONTEXT_1 = "http://www.digirati.com/context1.jsonld";
    private static final String TEST_CONTEXT_2 = "http://www.digirati.com/context2.jsonld";

    @Test
    public void testFormatCompacted() {
        Format format = Format.COMPACTED;
        assertNotNull(format);
        assertThat(format, is(equalTo(Format.COMPACTED)));
    }

    @Test
    public void testFormatExpanded() {
        Format format = Format.EXPANDED;
        assertNotNull(format);
        assertThat(format, is(equalTo(Format.EXPANDED)));
    }

    @Test
    public void testFormatFlattened() {
        Format format = Format.FLATTENED;
        assertNotNull(format);
        assertThat(format, is(equalTo(Format.FLATTENED)));
    }

    @Test
    public void testJsonLdProfile() {

        Map<String, List<String>> contexts = buildContexts();
        List<Format> formats = buildFormats();

        JSONLDProfile jsonLdProfile = new JSONLDProfile();
        jsonLdProfile.setContexts(contexts);
        jsonLdProfile.setFormats(formats);

        assertThat(contexts, is(equalTo(jsonLdProfile.getContexts())));
        assertThat(formats, is(equalTo(jsonLdProfile.getFormats())));
    }

    @SuppressWarnings("serial")
    private Map<String, List<String>> buildContexts() {
        return new HashMap<String, List<String>>() {
            {
                put(JSONLDConstants.ATTRIBUTE_CONTEXT, new ArrayList<String>() {
                    {
                        add(TEST_CONTEXT_1);
                        add(TEST_CONTEXT_2);
                    }
                });
            }
        };
    }

    @SuppressWarnings("serial")
    private List<Format> buildFormats() {
        return new ArrayList<JSONLDProfile.Format>() {
            {
                for (Format format : Format.values()) {
                    add(format);
                }
            }
        };
    }
}
