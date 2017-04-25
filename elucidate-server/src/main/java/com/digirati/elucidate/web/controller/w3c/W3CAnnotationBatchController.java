package com.digirati.elucidate.web.controller.w3c;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.digirati.elucidate.model.batch.W3CBatchOperation;
import com.digirati.elucidate.service.batch.W3CAnnotationBatchDeleteService;
import com.digirati.elucidate.service.batch.W3CAnnotationBatchUpdateService;
import com.digirati.elucidate.web.controller.AbstractAnnotationBatchController;

@Controller(W3CAnnotationBatchController.CONTROLLER_NAME)
@RequestMapping(value = "/w3c")
public class W3CAnnotationBatchController extends AbstractAnnotationBatchController<W3CBatchOperation> {

    public static final String CONTROLLER_NAME = "w3cAnnotationBulkUpdateController";

    @Autowired
    public W3CAnnotationBatchController(W3CAnnotationBatchUpdateService w3cAnnotationBatchUpdateService, W3CAnnotationBatchDeleteService w3cAnnotationBatchDeleteService) {
        super(w3cAnnotationBatchUpdateService, w3cAnnotationBatchDeleteService);
    }
}
