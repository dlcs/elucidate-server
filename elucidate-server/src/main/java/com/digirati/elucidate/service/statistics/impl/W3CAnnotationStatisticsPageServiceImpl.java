package com.digirati.elucidate.service.statistics.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.digirati.elucidate.common.service.IRIBuilderService;
import com.digirati.elucidate.model.statistics.W3CStatisticsPage;
import com.digirati.elucidate.repository.AnnotationStatisticsRepository;
import com.digirati.elucidate.service.statistics.W3CAnnotationStatisticsPageService;

@Service(W3CAnnotationStatisticsPageServiceImpl.SERVICE_NAME)
public class W3CAnnotationStatisticsPageServiceImpl extends AbstractAnnotationStatisticsPageServiceImpl<W3CStatisticsPage> implements W3CAnnotationStatisticsPageService {

    public static final String SERVICE_NAME = "w3CStatisticsPageServiceImpl";

    private IRIBuilderService iriBuilderService;

    @Autowired
    public W3CAnnotationStatisticsPageServiceImpl(IRIBuilderService iriBuilderService, AnnotationStatisticsRepository statisticsRepository, @Value("${annotation.page.size}") int pageSize) {
        super(statisticsRepository, pageSize);
        this.iriBuilderService = iriBuilderService;
    }

    @Override
    protected W3CStatisticsPage convertToStatisticsPage(Map<String, Object> w3cAnnotationPageMap) {

        W3CStatisticsPage w3cStatisticsPage = new W3CStatisticsPage();
        w3cStatisticsPage.setJsonMap(w3cAnnotationPageMap);
        return w3cStatisticsPage;
    }

    @Override
    protected String buildPageIri(String type, String field, int page) {
        return iriBuilderService.buildW3CStatisticsPageIri(type, field, page);
    }
}
