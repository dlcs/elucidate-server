package com.digirati.elucidate.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.ServiceResponse.Status;
import com.digirati.elucidate.model.batch.AbstractBatchOperation;
import com.digirati.elucidate.service.batch.AbstractAnnotationBatchDeleteService;
import com.digirati.elucidate.service.batch.AbstractAnnotationBatchUpdateService;

public abstract class AbstractAnnotationBatchController<B extends AbstractBatchOperation> {

    public static final String REQUEST_PATH_UPDATE = "/services/batch/update";
    public static final String REQUEST_PATH_DELETE = "/services/batch/delete";

    private AbstractAnnotationBatchUpdateService<B> annotationBatchUpdateService;
    private AbstractAnnotationBatchDeleteService<B> annotationBatchDeleteService;

    protected AbstractAnnotationBatchController(AbstractAnnotationBatchUpdateService<B> annotationBatchUpdateService, AbstractAnnotationBatchDeleteService<B> annotationBatchDeleteService) {
        this.annotationBatchUpdateService = annotationBatchUpdateService;
        this.annotationBatchDeleteService = annotationBatchDeleteService;
    }

    @RequestMapping(value = REQUEST_PATH_UPDATE, method = RequestMethod.POST)
    public ResponseEntity<B> postUpdate(@RequestBody B batchOperation) {

        ServiceResponse<B> serviceResponse = annotationBatchUpdateService.processBatchUpdate(batchOperation);
        Status status = serviceResponse.getStatus();

        if (!status.equals(Status.OK)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        batchOperation = serviceResponse.getObj();
        return ResponseEntity.status(HttpStatus.OK).body(batchOperation);
    }

    @RequestMapping(value = REQUEST_PATH_DELETE, method = RequestMethod.POST)
    public ResponseEntity<B> postDelete(@RequestBody B batchOperation) {

        ServiceResponse<B> serviceResponse = annotationBatchDeleteService.processBatchDelete(batchOperation);
        Status status = serviceResponse.getStatus();

        if (!status.equals(Status.OK)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        batchOperation = serviceResponse.getObj();
        return ResponseEntity.status(HttpStatus.OK).body(batchOperation);
    }
}
