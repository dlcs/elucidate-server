package com.digirati.elucidate.web.controller.w3c;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationCollection;
import com.digirati.elucidate.service.query.W3CAnnotationCollectionService;
import com.digirati.elucidate.web.controller.AbstractAnnotationContainerWriteController;

@RestController(W3CAnnotationContainerWriteController.CONTROLLER_NAME)
@RequestMapping(value = "/w3c")
public class W3CAnnotationContainerWriteController extends AbstractAnnotationContainerWriteController<W3CAnnotation, W3CAnnotationCollection> {

    public static final String CONTROLLER_NAME = "w3cAnnotationContainerWriteController";

    @Autowired
    public W3CAnnotationContainerWriteController(W3CAnnotationCollectionService w3cAnnotationCollectionService) {
        super(w3cAnnotationCollectionService);
    }
}
