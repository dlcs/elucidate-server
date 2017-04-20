package com.digirati.elucidate.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.digirati.elucidate.common.infrastructure.constants.JSONLDConstants;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotationCollection;
import com.digirati.elucidate.infrastructure.exception.InvalidAnnotationCollectionException;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.ServiceResponse.Status;
import com.digirati.elucidate.model.ValidationError;
import com.digirati.elucidate.service.query.AbstractAnnotationCollectionService;

public abstract class AbstractAnnotationContainerWriteController<A extends AbstractAnnotation, C extends AbstractAnnotationCollection> {

    private static final String CREATE_REQUEST_PATH = "/";

    private AbstractAnnotationCollectionService<A, C> annotationCollectionService;

    protected AbstractAnnotationContainerWriteController(AbstractAnnotationCollectionService<A, C> annotationCollectionService) {
        this.annotationCollectionService = annotationCollectionService;
    }

    @RequestMapping(value = CREATE_REQUEST_PATH, method = RequestMethod.POST)
    public ResponseEntity<C> create(@RequestBody C annotationCollection, HttpServletRequest request, HttpServletResponse response) {

        String collectionId = request.getHeader("Slug");

        ServiceResponse<C> serviceResponse = annotationCollectionService.createAnnotationCollection(collectionId, annotationCollection);
        Status status = serviceResponse.getStatus();

        if (status.equals(Status.NOT_FOUND)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        if (status.equals(Status.ALREADY_EXISTS)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        if (status.equals(Status.NON_CONFORMANT)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (status.equals(Status.OK)) {
            annotationCollection = serviceResponse.getObj();
            response.setHeader(HttpHeaders.LOCATION, (String) annotationCollection.getJsonMap().get(JSONLDConstants.ATTRIBUTE_ID));
            return ResponseEntity.status(HttpStatus.CREATED).body(annotationCollection);
        }

        throw new IllegalArgumentException(String.format("Unexpected service response status [%s]", status));
    }

    @ExceptionHandler(InvalidAnnotationCollectionException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ValidationError handleException(InvalidAnnotationCollectionException invalidAnnotationCollectionException) {
        ValidationError validationError = new ValidationError();
        validationError.setJsonError(invalidAnnotationCollectionException.getErrorJson());
        return validationError;
    }
}
