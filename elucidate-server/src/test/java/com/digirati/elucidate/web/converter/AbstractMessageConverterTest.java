package com.digirati.elucidate.web.converter;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.MediaType;

import com.digirati.elucidate.model.JSONLDProfile;
import com.digirati.elucidate.web.converter.oa.annotation.JSONLDOAAnnotationMessageConverter;

public class AbstractMessageConverterTest {

    AbstractMessageConverter<?> abstractMessageconverter;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
	abstractMessageconverter = new JSONLDOAAnnotationMessageConverter(new String[]{"test"});
    }

    @Test
    public void testGetJsonLdProfile() {
	Map <String,String> map = new HashMap<>();
	map.put("profile", "\"http://iiif.io/api/presentation/2/context.json\" \"http://iiif.io/api/search/1/context.json\"");
	MediaType contentType = new MediaType(MediaType.APPLICATION_JSON, map);
	 
	JSONLDProfile profile = abstractMessageconverter.getJsonLdProfile(contentType, null);
	Map contexts = profile.getContexts();
	List contextList = (List)contexts.get("@context");
	assertTrue(contextList.contains("http://iiif.io/api/presentation/2/context.json"));
	assertTrue(contextList.contains("http://iiif.io/api/search/1/context.json"));
    }

}
