package com.digirati.elucidate.service.statistics;

import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.statistics.AbstractStatisticsPage;

public interface AbstractAnnotationStatisticsPageService<S extends AbstractStatisticsPage> {

    ServiceResponse<S> buildBodyStatisticsPage(String field, int page);

    ServiceResponse<S> buildTargetStatisticsPage(String field, int page);
}
