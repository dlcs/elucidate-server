package com.digirati.elucidate.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.digirati.elucidate.common.infrastructure.constants.URLConstants;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.ServiceResponse.Status;
import com.digirati.elucidate.model.statistics.AbstractStatisticsPage;
import com.digirati.elucidate.service.statistics.AbstractAnnotationStatisticsPageService;

public abstract class AbstractAnnotationStatisticsController<S extends AbstractStatisticsPage> {

    private static final String REQUEST_PATH_BODY = "/services/stats/body";
    private static final String REQUEST_PATH_TARGET = "/services/stats/target";

    private AbstractAnnotationStatisticsPageService<S> statisticsPageService;

    protected AbstractAnnotationStatisticsController(AbstractAnnotationStatisticsPageService<S> statisticsPageService) {
        this.statisticsPageService = statisticsPageService;
    }

    @RequestMapping(value = REQUEST_PATH_BODY, method = RequestMethod.GET)
    public ResponseEntity<S> getBodyStatistics(@RequestParam(value = URLConstants.PARAM_FIELD, required = true) String field, @RequestParam(value = URLConstants.PARAM_PAGE, required = false, defaultValue = "0") Integer page) {

        ServiceResponse<S> serviceResponse = statisticsPageService.buildBodyStatisticsPage(field, page);
        return getStatistics(serviceResponse);
    }

    @RequestMapping(value = REQUEST_PATH_TARGET, method = RequestMethod.GET)
    public ResponseEntity<S> getTargetStatistics(@RequestParam(value = URLConstants.PARAM_FIELD, required = true) String field, @RequestParam(value = URLConstants.PARAM_PAGE, required = false, defaultValue = "0") Integer page) {

        ServiceResponse<S> serviceResponse = statisticsPageService.buildTargetStatisticsPage(field, page);
        return getStatistics(serviceResponse);
    }

    private ResponseEntity<S> getStatistics(ServiceResponse<S> serviceResponse) {

        Status status = serviceResponse.getStatus();

        if (status.equals(Status.NON_CONFORMANT)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (!status.equals(Status.OK)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        return ResponseEntity.ok(serviceResponse.getObj());
    }
}
