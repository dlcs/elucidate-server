package com.digirati.elucidate.web.controller.oa;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.digirati.elucidate.common.model.annotation.oa.OAAnnotation;
import com.digirati.elucidate.common.model.annotation.oa.OAAnnotationCollection;
import com.digirati.elucidate.common.model.annotation.oa.OAAnnotationPage;
import com.digirati.elucidate.service.OAAnnotationCollectionService;
import com.digirati.elucidate.service.OAAnnotationPageService;
import com.digirati.elucidate.web.controller.AbstractAnnotationSearchController;

@Controller(OAAnnotationSearchController.CONTROLLER_NAME)
@RequestMapping(value = "/oa")
public class OAAnnotationSearchController extends AbstractAnnotationSearchController<OAAnnotation, OAAnnotationPage, OAAnnotationCollection> {

    public static final String CONTROLLER_NAME = "oaAnnotationSearchController";

    public OAAnnotationSearchController(OAAnnotationCollectionService oaAnnotationCollectionService, OAAnnotationPageService oaAnnotationPageService) {
        super(oaAnnotationCollectionService, oaAnnotationPageService);
    }
}
