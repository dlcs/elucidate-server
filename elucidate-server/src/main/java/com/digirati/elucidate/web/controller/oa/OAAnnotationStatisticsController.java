package com.digirati.elucidate.web.controller.oa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.digirati.elucidate.model.statistics.OAStatisticsPage;
import com.digirati.elucidate.service.statistics.OAAnnotationStatisticsPageService;
import com.digirati.elucidate.web.controller.AbstractAnnotationStatisticsController;

@Controller(OAAnnotationStatisticsController.CONTROLLER_NAME)
@RequestMapping(value = "/oa")
public class OAAnnotationStatisticsController extends AbstractAnnotationStatisticsController<OAStatisticsPage> {

    public static final String CONTROLLER_NAME = "oaStatisticsController";

    @Autowired
    public OAAnnotationStatisticsController(OAAnnotationStatisticsPageService statisticsPageService) {
        super(statisticsPageService);
    }
}
