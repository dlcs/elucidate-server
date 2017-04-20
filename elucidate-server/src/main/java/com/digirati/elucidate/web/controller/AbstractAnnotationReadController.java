package com.digirati.elucidate.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotationCollection;
import com.digirati.elucidate.infrastructure.exception.InvalidServiceResponseException;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.ServiceResponse.Status;
import com.digirati.elucidate.service.query.AbstractAnnotationService;

public abstract class AbstractAnnotationReadController<A extends AbstractAnnotation, C extends AbstractAnnotationCollection> {

    private static final String VARIABLE_COLLECTION_ID = "collectionId";
    private static final String VARIABLE_ANNOTATION_ID = "annotationId";
    private static final String REQUEST_PATH = "/{" + VARIABLE_COLLECTION_ID + "}/{" + VARIABLE_ANNOTATION_ID + "}";

    private AbstractAnnotationService<A> annotationService;

    protected AbstractAnnotationReadController(AbstractAnnotationService<A> annotationService) {
        this.annotationService = annotationService;
    }

    @RequestMapping(value = REQUEST_PATH, method = {RequestMethod.GET, RequestMethod.HEAD})
    public ResponseEntity<A> get(@PathVariable(VARIABLE_COLLECTION_ID) String collectionId, @PathVariable(VARIABLE_ANNOTATION_ID) String annotationId) {

        ServiceResponse<A> serviceResponse = annotationService.getAnnotation(collectionId, annotationId);
        Status status = serviceResponse.getStatus();

        if (status.equals(Status.NOT_FOUND)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        if (status.equals(Status.DELETED)) {
            return ResponseEntity.status(HttpStatus.GONE).body(null);
        }

        if (status.equals(Status.OK)) {
            return ResponseEntity.ok(serviceResponse.getObj());
        }

        throw new InvalidServiceResponseException(status);
    }
}
