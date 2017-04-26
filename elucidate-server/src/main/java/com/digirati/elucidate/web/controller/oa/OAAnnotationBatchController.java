package com.digirati.elucidate.web.controller.oa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.digirati.elucidate.model.batch.OABatchOperation;
import com.digirati.elucidate.service.batch.OAAnnotationBatchDeleteService;
import com.digirati.elucidate.service.batch.OAAnnotationBatchUpdateService;
import com.digirati.elucidate.web.controller.AbstractAnnotationBatchController;

@Controller(OAAnnotationBatchController.CONTROLLER_NAME)
@RequestMapping(value = "/oa")
public class OAAnnotationBatchController extends AbstractAnnotationBatchController<OABatchOperation> {

    public static final String CONTROLLER_NAME = "oaAnnotationBulkUpdateController";

    @Autowired
    public OAAnnotationBatchController(OAAnnotationBatchUpdateService oaAnnotationBatchUpdateService, OAAnnotationBatchDeleteService oaAnnotationBatchDeleteService) {
        super(oaAnnotationBatchUpdateService, oaAnnotationBatchDeleteService);
    }
}
