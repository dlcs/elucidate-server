package com.digirati.elucidate.web.converter.validationerror;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.digirati.elucidate.model.ValidationError;

@Component
public class JSONLDValidationErrorMessageConverter extends AbstractValidationErrorMessageConverter {

    public JSONLDValidationErrorMessageConverter() {
        super(APPLICATION_JSON_LD);
    }

    @Override
    protected String getStringRepresentation(ValidationError validationError, MediaType contentType) throws Exception {
        return validationError.getJsonError();
    }

    @Override
    protected ValidationError getObjectRepresentation(String str, MediaType contentType) throws Exception {
        throw new UnsupportedOperationException(String.format("Conversion from [%s] not supported", ValidationError.class));
    }
}
