package com.digirati.elucidate.service.search.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.digirati.elucidate.common.model.annotation.oa.OAAnnotation;
import com.digirati.elucidate.common.model.annotation.oa.OAAnnotationPage;
import com.digirati.elucidate.common.service.IRIBuilderService;
import com.digirati.elucidate.converter.OAToW3CAnnotationPageConverter;
import com.digirati.elucidate.service.search.OAAnnotationPageSearchService;
import com.digirati.elucidate.service.search.OAAnnotationSearchService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service(OAAnnotationPageSearchServiceImpl.SERVICE_NAME)
public class OAAnnotationPageSearchServiceImpl extends AbstractAnnotationPageSearchServiceImpl<OAAnnotation, OAAnnotationPage> implements OAAnnotationPageSearchService {

    public static final String SERVICE_NAME = "oaAnnotationPageSearchServiceImpl";

    private IRIBuilderService iriBuilderService;

    @Autowired
    public OAAnnotationPageSearchServiceImpl(IRIBuilderService iriBuilderService, OAAnnotationSearchService oaAnnotationSearchService, @Value("${annotation.page.size}") int pageSize) {
        super(oaAnnotationSearchService, pageSize);
        this.iriBuilderService = iriBuilderService;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected OAAnnotationPage convertToAnnotationPage(Map<String, Object> w3cAnnotationPageMap) {

        JsonNode w3cAnnotationPageNode = new ObjectMapper().convertValue(w3cAnnotationPageMap, JsonNode.class);

        JsonNode oaAnnotationPageNode = new OAToW3CAnnotationPageConverter().convert(w3cAnnotationPageNode);
        Map<String, Object> oaAnnotationPageMap = new ObjectMapper().convertValue(oaAnnotationPageNode, Map.class);

        OAAnnotationPage oaAnnotationPage = new OAAnnotationPage();
        oaAnnotationPage.setJsonMap(oaAnnotationPageMap);
        return oaAnnotationPage;
    }

    @Override
    protected String buildCollectionIri(String targetIri, boolean strict) {
        return iriBuilderService.buildOACollectionSearchIri(targetIri, strict);
    }

    @Override
    protected String buildPageIri(String targetIri, boolean strict, int page, boolean embeddedDescriptions) {
        return iriBuilderService.buildOAPageSearchIri(targetIri, strict, page, embeddedDescriptions);
    }
}
