package com.digirati.elucidate.web.controller.w3c;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.digirati.elucidate.model.statistics.W3CStatisticsPage;
import com.digirati.elucidate.service.statistics.W3CAnnotationStatisticsPageService;
import com.digirati.elucidate.web.controller.AbstractAnnotationStatisticsController;

@Controller(W3CAnnotationStatisticsController.CONTROLLER_NAME)
@RequestMapping(value = "/w3c")
public class W3CAnnotationStatisticsController extends AbstractAnnotationStatisticsController<W3CStatisticsPage> {

    public static final String CONTROLLER_NAME = "w3cStatisticsController";

    @Autowired
    public W3CAnnotationStatisticsController(W3CAnnotationStatisticsPageService statisticsPageService) {
        super(statisticsPageService);
    }
}
