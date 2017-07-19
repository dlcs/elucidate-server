package com.digirati.elucidate.web.controller.oa;

import com.digirati.elucidate.common.model.annotation.oa.OAAnnotation;
import com.digirati.elucidate.model.annotation.history.OAAnnotationHistory;
import com.digirati.elucidate.service.history.OAAnnotationHistoryService;
import com.digirati.elucidate.web.controller.AbstractAnnotationHistoryController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller(OAAnnotationHistoryController.CONTROLLER_NAME)
@RequestMapping(value = "/oa")
public class OAAnnotationHistoryController extends AbstractAnnotationHistoryController<OAAnnotation, OAAnnotationHistory> {

    public static final String CONTROLLER_NAME = "oaAnnotationHistoryController";

    @Autowired
    public OAAnnotationHistoryController(OAAnnotationHistoryService oaAnnotationHistoryService) {
        super(oaAnnotationHistoryService);
    }
}
