package com.digirati.elucidate.web.controller.w3c;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationCollection;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationPage;
import com.digirati.elucidate.service.W3CAnnotationCollectionService;
import com.digirati.elucidate.service.W3CAnnotationPageService;
import com.digirati.elucidate.web.controller.AbstractAnnotationSearchController;

@Controller(W3CAnnotationSearchController.CONTROLLER_NAME)
@RequestMapping(value = "/w3c")
public class W3CAnnotationSearchController extends AbstractAnnotationSearchController<W3CAnnotation, W3CAnnotationPage, W3CAnnotationCollection> {

    public static final String CONTROLLER_NAME = "w3cAnnotationSearchController";

    public W3CAnnotationSearchController(W3CAnnotationCollectionService w3cAnnotationCollectionService, W3CAnnotationPageService w3cAnnotationPageService) {
        super(w3cAnnotationCollectionService, w3cAnnotationPageService);
    }
}
