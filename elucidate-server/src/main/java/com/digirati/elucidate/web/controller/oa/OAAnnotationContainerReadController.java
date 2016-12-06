package com.digirati.elucidate.web.controller.oa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.digirati.elucidate.common.model.annotation.oa.OAAnnotation;
import com.digirati.elucidate.common.model.annotation.oa.OAAnnotationCollection;
import com.digirati.elucidate.common.model.annotation.oa.OAAnnotationPage;
import com.digirati.elucidate.service.OAAnnotationCollectionService;
import com.digirati.elucidate.service.OAAnnotationPageService;
import com.digirati.elucidate.web.controller.AbstractAnnotationContainerReadController;

@Controller(OAAnnotationContainerReadController.CONTROLLER_NAME)
@RequestMapping(value = "/oa")
public class OAAnnotationContainerReadController extends AbstractAnnotationContainerReadController<OAAnnotation, OAAnnotationPage, OAAnnotationCollection> {

    public static final String CONTROLLER_NAME = "oaAnnotationContainerReadController";

    @Autowired
    public OAAnnotationContainerReadController(OAAnnotationCollectionService oaAnnotationCollectionService, OAAnnotationPageService oaAnnotationPageService) {
        super(oaAnnotationCollectionService, oaAnnotationPageService);
    }
}
