package com.digirati.elucidate.web.controller.oa;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.digirati.elucidate.common.model.annotation.oa.OAAnnotation;
import com.digirati.elucidate.common.model.annotation.oa.OAAnnotationCollection;
import com.digirati.elucidate.common.model.annotation.oa.OAAnnotationPage;
import com.digirati.elucidate.service.search.OAAnnotationCollectionSearchService;
import com.digirati.elucidate.service.search.OAAnnotationPageSearchService;
import com.digirati.elucidate.service.search.OAAnnotationSearchService;
import com.digirati.elucidate.web.controller.AbstractAnnotationSearchController;

@Controller(OAAnnotationSearchController.CONTROLLER_NAME)
@RequestMapping(value = "/oa")
public class OAAnnotationSearchController extends AbstractAnnotationSearchController<OAAnnotation, OAAnnotationPage, OAAnnotationCollection> {

    public static final String CONTROLLER_NAME = "oaAnnotationSearchController";

    public OAAnnotationSearchController(OAAnnotationSearchService oaAnnotationSearchService, OAAnnotationPageSearchService oaAnnotationPageSearchService, OAAnnotationCollectionSearchService oaAnnotationCollectionSearchService) {
        super(oaAnnotationSearchService, oaAnnotationPageSearchService, oaAnnotationCollectionSearchService);
    }
}
