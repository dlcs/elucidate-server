package com.digirati.elucidate.web.controller;

import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;
import com.digirati.elucidate.infrastructure.exception.InvalidServiceResponseException;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.ServiceResponse.Status;
import com.digirati.elucidate.model.annotation.history.AbstractAnnotationHistory;
import com.digirati.elucidate.service.history.AbstractAnnotationHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public abstract class AbstractAnnotationHistoryController<A extends AbstractAnnotation, H extends AbstractAnnotationHistory> {

    private static final String VARIABLE_COLLECTION_ID = "collectionId";
    private static final String VARIABLE_ANNOTATION_ID = "annotationId";
    private static final String VARIABLE_VERSION = "version";
    private static final String REQUEST_PATH = "/services/history/{" + VARIABLE_COLLECTION_ID + "}/{" + VARIABLE_ANNOTATION_ID + "}/{" + VARIABLE_VERSION + "}";

    private AbstractAnnotationHistoryService<A, H> annotationHistoryService;

    protected AbstractAnnotationHistoryController(AbstractAnnotationHistoryService<A, H> annotationHistoryService) {
        this.annotationHistoryService = annotationHistoryService;
    }

    @RequestMapping(value = REQUEST_PATH, method = RequestMethod.GET)
    public ResponseEntity<H> get(@PathVariable(VARIABLE_COLLECTION_ID) String collectionId, @PathVariable(VARIABLE_ANNOTATION_ID) String annotationId, @PathVariable(VARIABLE_VERSION) int version) {

        ServiceResponse<H> serviceResponse = annotationHistoryService.getVersionedAnnotation(collectionId, annotationId, version);
        Status status = serviceResponse.getStatus();

        if (status.equals(ServiceResponse.Status.NOT_FOUND)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        if (status.equals(ServiceResponse.Status.OK)) {
            return ResponseEntity.ok(serviceResponse.getObj());
        }

        throw new InvalidServiceResponseException(status);
    }
}
