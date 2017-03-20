package com.digirati.elucidate.service.search.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.digirati.elucidate.common.model.annotation.oa.OAAnnotationCollection;
import com.digirati.elucidate.common.model.annotation.oa.OAAnnotationPage;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationCollection;
import com.digirati.elucidate.common.service.IRIBuilderService;
import com.digirati.elucidate.converter.W3CToOAAnnotationCollectionConverter;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.enumeration.ClientPreference;
import com.digirati.elucidate.repository.AnnotationSearchRepository;
import com.digirati.elucidate.service.search.OAAnnotationCollectionSearchService;
import com.digirati.elucidate.service.search.OAAnnotationPageSearchService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service(OAAnnotationCollectionSearchServiceImpl.SERVICE_NAME)
public class OAAnnotationCollectionSearchServiceImpl extends AbstractAnnotationCollectionSearchServiceImpl<OAAnnotationPage, OAAnnotationCollection> implements OAAnnotationCollectionSearchService {

    public static final String SERVICE_NAME = "oaAnnotationCollectionSearchServiceImpl";

    private IRIBuilderService iriBuilderService;
    private OAAnnotationPageSearchService oaAnnotationPageSearchService;

    @Autowired
    public OAAnnotationCollectionSearchServiceImpl(AnnotationSearchRepository annotationSearchRepository, IRIBuilderService iriBuilderService, OAAnnotationPageSearchService oaAnnotationPageSearchService, @Value("${annotation.page.size}") int pageSize) {
        super(annotationSearchRepository, pageSize);
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
        oaAnnotationCollection.setCacheKey(w3cAnnotationCollection.getCacheKey());
        oaAnnotationCollection.setCreatedDateTime(w3cAnnotationCollection.getCreatedDateTime());
        oaAnnotationCollection.setDeleted(w3cAnnotationCollection.isDeleted());
        oaAnnotationCollection.setCollectionId(w3cAnnotationCollection.getCollectionId());
        oaAnnotationCollection.setJsonMap(oaAnnotationCollectionMap);
        oaAnnotationCollection.setModifiedDateTime(oaAnnotationCollection.getModifiedDateTime());
        return oaAnnotationCollection;
    }

    @Override
    protected ServiceResponse<OAAnnotationPage> buildFirstAnnotationPage(String targetIri, boolean strict, String box, ClientPreference clientPref) {
        if (clientPref.equals(ClientPreference.CONTAINED_IRIS)) {
            return oaAnnotationPageSearchService.searchAnnotationPage(targetIri, strict, box, 0, false);
        } else {
            return oaAnnotationPageSearchService.searchAnnotationPage(targetIri, strict, box, 0, true);
        }
    }

    @Override
    protected String buildCollectionIri(String targetIri, boolean strict, String box) {
        return iriBuilderService.buildOACollectionSearchIri(targetIri, strict, box);
    }

    @Override
    protected String buildPageIri(String targetIri, boolean strict, String box, int page, boolean embeddedDescriptions) {
        return iriBuilderService.buildOAPageSearchIri(targetIri, strict, box, page, embeddedDescriptions);
    }
}
