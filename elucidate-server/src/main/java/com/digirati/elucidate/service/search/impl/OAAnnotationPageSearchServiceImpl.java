package com.digirati.elucidate.service.search.impl;

import com.digirati.elucidate.common.model.annotation.oa.OAAnnotation;
import com.digirati.elucidate.common.model.annotation.oa.OAAnnotationPage;
import com.digirati.elucidate.common.service.IRIBuilderService;
import com.digirati.elucidate.converter.OAToW3CAnnotationPageConverter;
import com.digirati.elucidate.service.search.OAAnnotationPageSearchService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service(OAAnnotationPageSearchServiceImpl.SERVICE_NAME)
public class OAAnnotationPageSearchServiceImpl extends AbstractAnnotationPageSearchServiceImpl<OAAnnotation, OAAnnotationPage> implements OAAnnotationPageSearchService {

    public static final String SERVICE_NAME = "oaAnnotationPageSearchServiceImpl";

    private IRIBuilderService iriBuilderService;

    @Autowired
    public OAAnnotationPageSearchServiceImpl(IRIBuilderService iriBuilderService, @Value("${annotation.page.size}") int pageSize) {
        super(pageSize);
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
    protected String buildBodySearchCollectionIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, String generatorIri) {
        return iriBuilderService.buildOACollectionBodySearchIri(fields, value, strict, xywh, t, creatorIri, generatorIri);
    }

    @Override
    protected String buildBodySearchPageIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, String generatorIri, int page, boolean embeddedDescriptions) {
        return iriBuilderService.buildOAPageBodySearchIri(fields, value, strict, xywh, t, creatorIri, generatorIri, page, embeddedDescriptions);
    }

    @Override
    protected String buildTargetSearchCollectionIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, String generatorIri) {
        return iriBuilderService.buildOACollectionTargetSearchIri(fields, value, strict, xywh, t, creatorIri, generatorIri);
    }

    @Override
    protected String buildTargetSearchPageIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, String generatorIri, int page, boolean embeddedDescriptions) {
        return iriBuilderService.buildOAPageTargetSearchIri(fields, value, strict, xywh, t, creatorIri, generatorIri, page, embeddedDescriptions);
    }

    @Override
    protected String buildCreatorSearchCollectionIri(List<String> levels, String type, String value) {
        return iriBuilderService.buildOACollectionCreatorSearchIri(levels, type, value);
    }

    @Override
    protected String buildCreatorSearchPageIri(List<String> levels, String type, String value, int page, boolean embeddedDescriptions) {
        return iriBuilderService.buildOAPageCreatorSearchIri(levels, type, value, page, embeddedDescriptions);
    }

    @Override
    protected String buildGeneratorSearchCollectionIri(List<String> levels, String type, String value) {
        return iriBuilderService.buildOACollectionGeneratorSearchIri(levels, type, value);
    }

    @Override
    protected String buildGeneratorSearchPageIri(List<String> levels, String type, String value, int page, boolean embeddedDescriptions) {
        return iriBuilderService.buildOAPageGeneratorSearchIri(levels, type, value, page, embeddedDescriptions);
    }
}
