package com.digirati.elucidate.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.digirati.elucidate.common.model.annotation.oa.OAAnnotation;
import com.digirati.elucidate.common.model.annotation.oa.OAAnnotationCollection;
import com.digirati.elucidate.common.model.annotation.oa.OAAnnotationPage;
import com.digirati.elucidate.common.service.IRIBuilderService;
import com.digirati.elucidate.converter.OAToW3CAnnotationPageConverter;
import com.digirati.elucidate.service.OAAnnotationPageService;
import com.digirati.elucidate.service.OAAnnotationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service(OAAnnotationPageServiceImpl.SERVICE_NAME)
public class OAAnnotationPageServiceImpl extends AbstractAnnotationPageServiceImpl<OAAnnotation, OAAnnotationPage, OAAnnotationCollection> implements OAAnnotationPageService {

    public static final String SERVICE_NAME = "oaAnnotationPageServiceImpl";

    private IRIBuilderService iriBuilderService;

    @Autowired
    public OAAnnotationPageServiceImpl(OAAnnotationService oaAnnotationService, IRIBuilderService iriBuilderService, @Value("${annotation.page.size}") int pageSize) {
        super(oaAnnotationService, pageSize);
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
    protected String buildCollectionIri(String collectionId) {
        return iriBuilderService.buildOACollectionIri(collectionId);
    }

    @Override
    protected String buildCollectionSearchIri(String targetId) {
        return iriBuilderService.buildOACollectionSearchIri(targetId);
    }

    @Override
    protected String buildPageIri(String collectionId, int page, boolean embeddedDescriptions) {
        return iriBuilderService.buildOAPageIri(collectionId, page, embeddedDescriptions);
    }

    @Override
    protected String buildPageSearchIri(String targetId, int page, boolean embeddedDescriptions) {
        return iriBuilderService.buildOAPageSearchIri(targetId, page, embeddedDescriptions);
    }
}
