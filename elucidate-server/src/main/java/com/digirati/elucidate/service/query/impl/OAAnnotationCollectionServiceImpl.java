package com.digirati.elucidate.service.query.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.digirati.elucidate.common.model.annotation.oa.OAAnnotation;
import com.digirati.elucidate.common.model.annotation.oa.OAAnnotationCollection;
import com.digirati.elucidate.common.model.annotation.oa.OAAnnotationPage;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationCollection;
import com.digirati.elucidate.common.service.IRIBuilderService;
import com.digirati.elucidate.converter.OAToW3CAnnotationCollectionConverter;
import com.digirati.elucidate.converter.W3CToOAAnnotationCollectionConverter;
import com.digirati.elucidate.infrastructure.generator.IDGenerator;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.enumeration.ClientPreference;
import com.digirati.elucidate.repository.AnnotationCollectionStoreRepository;
import com.digirati.elucidate.repository.AnnotationSearchRepository;
import com.digirati.elucidate.service.query.OAAnnotationCollectionService;
import com.digirati.elucidate.service.query.OAAnnotationPageService;
import com.digirati.elucidate.service.query.OAAnnotationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service(OAAnnotationCollectionServiceImpl.SERVICE_NAME)
public class OAAnnotationCollectionServiceImpl extends AbstractAnnotationCollectionServiceImpl<OAAnnotation, OAAnnotationPage, OAAnnotationCollection> implements OAAnnotationCollectionService {

    public static final String SERVICE_NAME = "oaAnnotationCollectionServiceImpl";

    private IRIBuilderService iriBuilderService;
    private OAAnnotationPageService oaAnnotationPageService;

    @Autowired
    public OAAnnotationCollectionServiceImpl(AnnotationCollectionStoreRepository annotationCollectionStoreRepository, AnnotationSearchRepository annotationSearchRepository, IRIBuilderService iriBuilderService, OAAnnotationService oaAnnotationService, OAAnnotationPageService oaAnnotationPageService, @Qualifier("collectionIdGenerator") IDGenerator idGenerator, @Value("${annotation.page.size}") int pageSize) {
        super(annotationCollectionStoreRepository, oaAnnotationService, idGenerator, pageSize);
        this.iriBuilderService = iriBuilderService;
        this.oaAnnotationPageService = oaAnnotationPageService;
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
    @SuppressWarnings("unchecked")
    protected W3CAnnotationCollection convertFromAnnotationCollection(OAAnnotationCollection oaAnnotationCollection) {

        Map<String, Object> oaAnnotationCollectionMap = oaAnnotationCollection.getJsonMap();
        JsonNode oaAnnotationCollectionNode = new ObjectMapper().convertValue(oaAnnotationCollectionMap, JsonNode.class);

        JsonNode w3cAnnotationCollectionNode = new OAToW3CAnnotationCollectionConverter().convert(oaAnnotationCollectionNode);
        Map<String, Object> w3cAnnotationCollectionMap = new ObjectMapper().convertValue(w3cAnnotationCollectionNode, Map.class);

        W3CAnnotationCollection w3cAnnotationCollection = new W3CAnnotationCollection();
        w3cAnnotationCollection.setCacheKey(oaAnnotationCollection.getCacheKey());
        w3cAnnotationCollection.setCreatedDateTime(oaAnnotationCollection.getCreatedDateTime());
        w3cAnnotationCollection.setDeleted(oaAnnotationCollection.isDeleted());
        w3cAnnotationCollection.setCollectionId(oaAnnotationCollection.getCollectionId());
        w3cAnnotationCollection.setJsonMap(w3cAnnotationCollectionMap);
        w3cAnnotationCollection.setModifiedDateTime(oaAnnotationCollection.getModifiedDateTime());
        return w3cAnnotationCollection;
    }

    @Override
    protected ServiceResponse<OAAnnotationPage> buildFirstAnnotationPage(List<OAAnnotation> oaAnnotations, String collectionId, ClientPreference clientPref) {
        if (clientPref.equals(ClientPreference.CONTAINED_IRIS)) {
            return oaAnnotationPageService.buildAnnotationPage(oaAnnotations, collectionId, 0, false);
        } else {
            return oaAnnotationPageService.buildAnnotationPage(oaAnnotations, collectionId, 0, true);
        }
    }

    @Override
    protected String buildCollectionIri(String searchQuery) {
        return iriBuilderService.buildOACollectionIri(searchQuery);
    }

    @Override
    protected String buildPageIri(String searchQuery, int page, boolean embeddedDescriptions) {
        return iriBuilderService.buildOAPageIri(searchQuery, page, embeddedDescriptions);
    }
}
