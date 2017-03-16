package com.digirati.elucidate.web.controller.w3c;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationCollection;
import com.digirati.elucidate.service.query.W3CAnnotationService;
import com.digirati.elucidate.web.controller.AbstractAnnotationReadController;

@RestController(W3CAnnotationReadController.CONTROLLER_NAME)
@RequestMapping(value = "/w3c")
public class W3CAnnotationReadController extends AbstractAnnotationReadController<W3CAnnotation, W3CAnnotationCollection> {

    public static final String CONTROLLER_NAME = "w3cAnnotationReadController";

    @Autowired
    public W3CAnnotationReadController(W3CAnnotationService w3cAnnotationService) {
        super(w3cAnnotationService);
    }
}
