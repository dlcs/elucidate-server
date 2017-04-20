package com.digirati.elucidate.service.statistics;

import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.statistics.AbstractStatisticsPage;

public abstract interface AbstractAnnotationStatisticsPageService<S extends AbstractStatisticsPage> {

    public ServiceResponse<S> buildBodyStatisticsPage(String field, int page);

    public ServiceResponse<S> buildTargetStatisticsPage(String field, int page);
}
