package com.digirati.elucidate.web.controller.oa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digirati.elucidate.common.model.annotation.oa.OAAnnotation;
import com.digirati.elucidate.common.model.annotation.oa.OAAnnotationCollection;
import com.digirati.elucidate.service.OAAnnotationService;
import com.digirati.elucidate.web.controller.AbstractAnnotationReadController;

@RestController(OAAnnotationReadController.CONTROLLER_NAME)
@RequestMapping(value = "/oa")
public class OAAnnotationReadController extends AbstractAnnotationReadController<OAAnnotation, OAAnnotationCollection> {

    public static final String CONTROLLER_NAME = "oaAnnotationReadController";

    @Autowired
    public OAAnnotationReadController(OAAnnotationService oaAnnotationService) {
        super(oaAnnotationService);
    }
}
