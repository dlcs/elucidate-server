package com.digirati.elucidate.web.controller.w3c;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationCollection;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationPage;
import com.digirati.elucidate.service.search.W3CAnnotationCollectionSearchService;
import com.digirati.elucidate.service.search.W3CAnnotationPageSearchService;
import com.digirati.elucidate.service.search.W3CAnnotationSearchService;
import com.digirati.elucidate.web.controller.AbstractAnnotationSearchController;

@Controller(W3CAnnotationSearchController.CONTROLLER_NAME)
@RequestMapping(value = "/w3c")
public class W3CAnnotationSearchController extends AbstractAnnotationSearchController<W3CAnnotation, W3CAnnotationPage, W3CAnnotationCollection> {

    public static final String CONTROLLER_NAME = "w3cAnnotationSearchController";

    public W3CAnnotationSearchController(W3CAnnotationSearchService w3cAnnotationSearchService, W3CAnnotationPageSearchService w3cAnnotationPageSearchService, W3CAnnotationCollectionSearchService w3cAnnotationCollectionSearchService) {
        super(w3cAnnotationSearchService, w3cAnnotationPageSearchService, w3cAnnotationCollectionSearchService);
    }
}
