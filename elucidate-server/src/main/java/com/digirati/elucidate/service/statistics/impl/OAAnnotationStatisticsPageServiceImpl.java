package com.digirati.elucidate.service.statistics.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.digirati.elucidate.common.service.IRIBuilderService;
import com.digirati.elucidate.converter.OAToW3CAnnotationPageConverter;
import com.digirati.elucidate.model.statistics.OAStatisticsPage;
import com.digirati.elucidate.repository.AnnotationStatisticsRepository;
import com.digirati.elucidate.service.statistics.OAAnnotationStatisticsPageService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service(OAAnnotationStatisticsPageServiceImpl.SERVICE_NAME)
public class OAAnnotationStatisticsPageServiceImpl extends AbstractAnnotationStatisticsPageServiceImpl<OAStatisticsPage> implements OAAnnotationStatisticsPageService {

    public static final String SERVICE_NAME = "oaStatisticsPageServiceImpl";

    private IRIBuilderService iriBuilderService;

    @Autowired
    public OAAnnotationStatisticsPageServiceImpl(IRIBuilderService iriBuilderService, AnnotationStatisticsRepository statisticsRepository, @Value("${annotation.page.size}") int pageSize) {
        super(statisticsRepository, pageSize);
        this.iriBuilderService = iriBuilderService;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected OAStatisticsPage convertToStatisticsPage(Map<String, Object> w3cAnnotationPageMap) {

        JsonNode w3cAnnotationPageNode = new ObjectMapper().convertValue(w3cAnnotationPageMap, JsonNode.class);

        JsonNode oaAnnotationPageNode = new OAToW3CAnnotationPageConverter().convert(w3cAnnotationPageNode);
        Map<String, Object> oaAnnotationPageMap = new ObjectMapper().convertValue(oaAnnotationPageNode, Map.class);

        OAStatisticsPage oaStatisticsPage = new OAStatisticsPage();
        oaStatisticsPage.setJsonMap(oaAnnotationPageMap);
        return oaStatisticsPage;
    }

    @Override
    protected String buildPageIri(String field, int page) {
        return iriBuilderService.buildOAStatisticsPageBodyIri(field, page);
    }
}
