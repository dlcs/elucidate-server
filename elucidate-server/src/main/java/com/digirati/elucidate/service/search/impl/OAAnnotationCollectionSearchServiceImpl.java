package com.digirati.elucidate.service.search.impl;

import com.digirati.elucidate.common.model.annotation.oa.OAAnnotation;
import com.digirati.elucidate.common.model.annotation.oa.OAAnnotationCollection;
import com.digirati.elucidate.common.model.annotation.oa.OAAnnotationPage;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationCollection;
import com.digirati.elucidate.common.service.IRIBuilderService;
import com.digirati.elucidate.converter.W3CToOAAnnotationCollectionConverter;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.enumeration.ClientPreference;
import com.digirati.elucidate.service.search.OAAnnotationCollectionSearchService;
import com.digirati.elucidate.service.search.OAAnnotationPageSearchService;
import com.digirati.elucidate.service.search.OAAnnotationSearchService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service(OAAnnotationCollectionSearchServiceImpl.SERVICE_NAME)
public class OAAnnotationCollectionSearchServiceImpl extends AbstractAnnotationCollectionSearchServiceImpl<OAAnnotation, OAAnnotationPage, OAAnnotationCollection> implements OAAnnotationCollectionSearchService {

    public static final String SERVICE_NAME = "oaAnnotationCollectionSearchServiceImpl";

    private IRIBuilderService iriBuilderService;
    private OAAnnotationPageSearchService oaAnnotationPageSearchService;

    @Autowired
    public OAAnnotationCollectionSearchServiceImpl(IRIBuilderService iriBuilderService, OAAnnotationSearchService oaAnnotationSearchService, OAAnnotationPageSearchService oaAnnotationPageSearchService, @Value("${annotation.page.size}") int pageSize) {
        super(oaAnnotationSearchService, pageSize);
        this.iriBuilderService = iriBuilderService;
        this.oaAnnotationPageSearchService = oaAnnotationPageSearchService;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected OAAnnotationCollection convertToAnnotationCollection(W3CAnnotationCollection w3cAnnotationCollection) {

        Map<String, Object> w3cAnnotationCollectionMap = w3cAnnotationCollection.getJsonMap();
        JsonNode w3cAnnotationCollectionNode = new ObjectMapper().convertValue(w3cAnnotationCollectionMap, JsonNode.class);

        JsonNode oaAnnotationCollectionNode = new W3CToOAAnnotationCollectionConverter().convert(w3cAnnotationCollectionNode);
        Map<String, Object> oaAnnotationCollectionMap = new ObjectMapper().convertValue(oaAnnotationCollectionNode, Map.class);

        OAAnnotationCollection oaAnnotationCollection = new OAAnnotationCollection();
        oaAnnotationCollection.setPk(w3cAnnotationCollection.getPk());
        oaAnnotationCollection.setCacheKey(w3cAnnotationCollection.getCacheKey());
        oaAnnotationCollection.setCreatedDateTime(w3cAnnotationCollection.getCreatedDateTime());
        oaAnnotationCollection.setDeleted(w3cAnnotationCollection.isDeleted());
        oaAnnotationCollection.setCollectionId(w3cAnnotationCollection.getCollectionId());
        oaAnnotationCollection.setJsonMap(oaAnnotationCollectionMap);
        oaAnnotationCollection.setModifiedDateTime(oaAnnotationCollection.getModifiedDateTime());
        return oaAnnotationCollection;
    }

    @Override
    protected ServiceResponse<OAAnnotationPage> buildBodySearchFirstAnnotationPage(List<OAAnnotation> oaAnnotations, List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, String generatorIri, ClientPreference clientPref) {
        if (clientPref.equals(ClientPreference.CONTAINED_IRIS)) {
            return oaAnnotationPageSearchService.buildAnnotationPageByBody(oaAnnotations, fields, value, strict, xywh, t, creatorIri, generatorIri, 0, false);
        } else {
            return oaAnnotationPageSearchService.buildAnnotationPageByBody(oaAnnotations, fields, value, strict, xywh, t, creatorIri, generatorIri, 0, true);
        }
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
    protected ServiceResponse<OAAnnotationPage> buildTargetSearchFirstAnnotationPage(List<OAAnnotation> oaAnnotations, List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, String generatorIri, ClientPreference clientPref) {
        if (clientPref.equals(ClientPreference.CONTAINED_IRIS)) {
            return oaAnnotationPageSearchService.buildAnnotationPageByTarget(oaAnnotations, fields, value, strict, xywh, t, creatorIri, generatorIri, 0, false);
        } else {
            return oaAnnotationPageSearchService.buildAnnotationPageByTarget(oaAnnotations, fields, value, strict, xywh, t, creatorIri, generatorIri, 0, true);
        }
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
    protected ServiceResponse<OAAnnotationPage> buildCreatorSearchFirstAnnotationPage(List<OAAnnotation> oaAnnotations, List<String> levels, String type, String value, boolean strict, ClientPreference clientPref) {
        if (clientPref.equals(ClientPreference.CONTAINED_IRIS)) {
            return oaAnnotationPageSearchService.buildAnnotationPageByCreator(oaAnnotations, levels, type, value, strict, 0, false);
        } else {
            return oaAnnotationPageSearchService.buildAnnotationPageByCreator(oaAnnotations, levels, type, value, strict, 0, true);
        }
    }

    @Override
    protected String buildCreatorSearchCollectionIri(List<String> levels, String type, String value, boolean strict) {
        return iriBuilderService.buildOACollectionCreatorSearchIri(levels, type, value, strict);
    }

    @Override
    protected String buildCreatorSearchPageIri(List<String> levels, String type, String value, boolean strict, int page, boolean embeddedDescriptions) {
        return iriBuilderService.buildOAPageCreatorSearchIri(levels, type, value, strict, page, embeddedDescriptions);
    }

    @Override
    protected ServiceResponse<OAAnnotationPage> buildGeneratorSearchFirstAnnotationPage(List<OAAnnotation> oaAnnotations, List<String> levels, String type, String value, boolean strict, ClientPreference clientPref) {
        if (clientPref.equals(ClientPreference.CONTAINED_IRIS)) {
            return oaAnnotationPageSearchService.buildAnnotationPageByGenerator(oaAnnotations, levels, type, value, strict, 0, false);
        } else {
            return oaAnnotationPageSearchService.buildAnnotationPageByGenerator(oaAnnotations, levels, type, value, strict, 0, true);
        }
    }

    @Override
    protected String buildGeneratorSearchCollectionIri(List<String> levels, String type, String value, boolean strict) {
        return iriBuilderService.buildOACollectionGeneratorSearchIri(levels, type, value, strict);
    }

    @Override
    protected String buildGeneratorSearchPageIri(List<String> levels, String type, String value, boolean strict, int page, boolean embeddedDescriptions) {
        return iriBuilderService.buildOAPageGeneratorSearchIri(levels, type, value, strict, page, embeddedDescriptions);
    }
}
