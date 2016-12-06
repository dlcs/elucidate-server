package com.digirati.elucidate.test.model;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;

import com.digirati.elucidate.common.test.AbstractTest;
import com.digirati.elucidate.model.ValidationError;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.github.jsonldjava.utils.JsonUtils;

public class ValidationErrorTest extends AbstractTest {

    @Test
    public void testValidationError() throws JsonGenerationException, IOException {

        Map<String, Object> jsonMap = generateRandomJsonMap();
        String jsonStr = JsonUtils.toString(jsonMap);

        ValidationError validationError = new ValidationError();
        validationError.setJsonError(jsonStr);

        assertThat(jsonStr, is(equalTo(validationError.getJsonError())));
    }
}
