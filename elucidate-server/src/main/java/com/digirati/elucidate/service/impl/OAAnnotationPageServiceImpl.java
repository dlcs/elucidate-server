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
import com.digirati.elucidate.model.enumeration.SearchType;
import com.digirati.elucidate.service.OAAnnotationPageService;
import com.digirati.elucidate.service.OAAnnotationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service(OAAnnotationPageServiceImpl.SERVICE_NAME)
public class OAAnnotationPageServiceImpl extends AbstractAnnotationPageServiceImpl<OAAnnotation, OAAnnotationPage, OAAnnotationCollection> implements OAAnnotationPageService {

    public static final String SERVICE_NAME = "oaAnnotationPageServiceImpl";

    private IRIBuilderService iriBuilderService;

    @Autowired
    public OAAnnotationPageServiceImpl(IRIBuilderService iriBuilderService, OAAnnotationService oaAnnotationService, @Value("${annotation.page.size}") int pageSize) {
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
    protected String buildCollectionIri(SearchType searchType, String searchQuery) {
        if (searchType.equals(SearchType.COLLECTION_ID)) {
            return iriBuilderService.buildOACollectionIri(searchQuery);
        } else if (searchType.equals(SearchType.TARGET_IRI)) {
            return iriBuilderService.buildOACollectionSearchIri(searchQuery);
        } else {
            throw new IllegalArgumentException(String.format("Unexpected SearchType [%s]", searchType));
        }
    }

    @Override
    protected String buildPageIri(SearchType searchType, String searchQuery, int page, boolean embeddedDescriptions) {
        if (searchType.equals(SearchType.COLLECTION_ID)) {
            return iriBuilderService.buildOAPageIri(searchQuery, page, embeddedDescriptions);
        } else if (searchType.equals(SearchType.TARGET_IRI)) {
            return iriBuilderService.buildOAPageSearchIri(searchQuery, page, embeddedDescriptions);
        } else {
            throw new IllegalArgumentException(String.format("Unexpected SearchType [%s]", searchType));
        }
    }
}
