package com.digirati.elucidate.web.controller.w3c;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.model.annotation.history.W3CAnnotationHistory;
import com.digirati.elucidate.service.history.W3CAnnotationHistoryService;
import com.digirati.elucidate.web.controller.AbstractAnnotationHistoryController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller(W3CAnnotationHistoryController.CONTROLLER_NAME)
@RequestMapping(value = "/w3c")
public class W3CAnnotationHistoryController extends AbstractAnnotationHistoryController<W3CAnnotation, W3CAnnotationHistory> {

    public static final String CONTROLLER_NAME = "w3cAnnotationHistoryController";

    @Autowired
    public W3CAnnotationHistoryController(W3CAnnotationHistoryService w3CAnnotationHistoryService) {
        super(w3CAnnotationHistoryService);
    }
}
