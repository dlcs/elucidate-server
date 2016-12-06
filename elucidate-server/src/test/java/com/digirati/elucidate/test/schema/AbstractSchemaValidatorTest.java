package com.digirati.elucidate.test.schema;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.main.JsonValidator;
import com.github.jsonldjava.core.JsonLdError;
import com.github.jsonldjava.core.JsonLdProcessor;
import com.github.jsonldjava.utils.JsonUtils;

public abstract class AbstractSchemaValidatorTest {

    protected abstract String getSchemaFileName();

    protected void validateJson(String jsonFileName) throws IOException, ProcessingException, JsonLdError {

        JsonNode schema = getSchema();
        assertNotNull(schema);

        String jsonStr = getJson(jsonFileName);
        assertNotNull(jsonStr);
        
        Object jsonObj = JsonUtils.fromString(jsonStr);
        List<Object> expandedJson = JsonLdProcessor.expand(jsonObj);
        jsonStr = JsonUtils.toString(expandedJson);
        JsonNode json = JsonLoader.fromString(jsonStr);
        
        JsonValidator jsonValidator = JsonSchemaFactory.byDefault().getValidator();
        ProcessingReport processingReport = jsonValidator.validate(schema, json);
        assertNotNull(processingReport);
        if (!processingReport.isSuccess()) {

            ArrayNode jsonArray = JsonNodeFactory.instance.arrayNode();
            assertNotNull(jsonArray);

            Iterator<ProcessingMessage> iterator = processingReport.iterator();
            while (iterator.hasNext()) {
                ProcessingMessage processingMessage = iterator.next();
                jsonArray.add(processingMessage.asJson());
            }

            String errorJson = JsonUtils.toPrettyString(jsonArray);
            assertNotNull(errorJson);

            fail(errorJson);
        }
    }

    private String getJson(String fileName) throws IOException {
        InputStream inputStream = getClass().getResourceAsStream(fileName);
        return IOUtils.toString(inputStream, "UTF-8");
    }

    private JsonNode getSchema() throws IOException {
        String jsonStr = getJson(getSchemaFileName());
        return JsonLoader.fromString(jsonStr);
    }
}
