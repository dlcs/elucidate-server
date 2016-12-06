package com.digirati.elucidate.web.converter.validationerror;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;

import com.digirati.elucidate.model.ValidationError;
import com.digirati.elucidate.web.converter.AbstractMessageConverter;

public abstract class AbstractValidationErrorMessageConverter extends AbstractMessageConverter<ValidationError> {

    protected AbstractValidationErrorMessageConverter(MediaType... supportedMediaTypes) {
        super(supportedMediaTypes);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return ValidationError.class.equals(clazz);
    }

    @Override
    protected void decorateHeaders(ValidationError validationError, HttpOutputMessage outputMessage) {}
}
