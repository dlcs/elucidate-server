package com.digirati.elucidate.web.controller.oa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.digirati.elucidate.common.model.annotation.oa.OAAnnotation;
import com.digirati.elucidate.common.model.annotation.oa.OAAnnotationCollection;
import com.digirati.elucidate.service.OAAnnotationCollectionService;
import com.digirati.elucidate.web.controller.AbstractAnnotationContainerWriteController;

@Controller(OAAnnotationContainerWriteController.CONTROLLER_NAME)
@RequestMapping(value = "/oa")
public class OAAnnotationContainerWriteController extends AbstractAnnotationContainerWriteController<OAAnnotation, OAAnnotationCollection> {

    public static final String CONTROLLER_NAME = "oaAnnotationContainerWriteController";

    @Autowired
    public OAAnnotationContainerWriteController(OAAnnotationCollectionService oaAnnotationCollectionService) {
        super(oaAnnotationCollectionService);
    }
}
