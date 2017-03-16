package com.digirati.elucidate.web.controller.oa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digirati.elucidate.common.model.annotation.oa.OAAnnotation;
import com.digirati.elucidate.common.model.annotation.oa.OAAnnotationCollection;
import com.digirati.elucidate.service.query.OAAnnotationService;
import com.digirati.elucidate.web.controller.AbstractAnnotationWriteController;

@RestController(OAAnnotationWriteController.CONTROLLER_NAME)
@RequestMapping(value = "/oa")
public class OAAnnotationWriteController extends AbstractAnnotationWriteController<OAAnnotation, OAAnnotationCollection> {

    public static final String CONTROLLER_NAME = "oaAnnotationWriteController";

    @Autowired
    public OAAnnotationWriteController(OAAnnotationService oaAnnotationService) {
        super(oaAnnotationService);
    }
}
