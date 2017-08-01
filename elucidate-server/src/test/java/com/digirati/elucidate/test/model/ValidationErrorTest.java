package com.digirati.elucidate.test.model;

import com.digirati.elucidate.common.test.AbstractTest;
import com.digirati.elucidate.model.ValidationError;
import com.github.jsonldjava.utils.JsonUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ValidationErrorTest extends AbstractTest {

    @Test
    public void testValidationError() throws IOException {

        Map<String, Object> jsonMap = generateRandomJsonMap();
        String jsonStr = JsonUtils.toString(jsonMap);

        ValidationError validationError = new ValidationError();
        validationError.setJsonError(jsonStr);

        assertThat(jsonStr, is(equalTo(validationError.getJsonError())));
    }
}
