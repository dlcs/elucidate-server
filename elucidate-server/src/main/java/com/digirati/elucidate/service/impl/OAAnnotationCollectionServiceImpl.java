package com.digirati.elucidate.service.impl;

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
import com.digirati.elucidate.model.enumeration.SearchType;
import com.digirati.elucidate.repository.AnnotationCollectionStoreRepository;
import com.digirati.elucidate.repository.AnnotationSearchRepository;
import com.digirati.elucidate.repository.AnnotationStoreRepository;
import com.digirati.elucidate.service.OAAnnotationCollectionService;
import com.digirati.elucidate.service.OAAnnotationPageService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service(OAAnnotationCollectionServiceImpl.SERVICE_NAME)
public class OAAnnotationCollectionServiceImpl extends AbstractAnnotationCollectionServiceImpl<OAAnnotation, OAAnnotationPage, OAAnnotationCollection> implements OAAnnotationCollectionService {

    public static final String SERVICE_NAME = "oaAnnotationCollectionServiceImpl";

    private OAAnnotationPageService oaAnnotationPageService;
    private IRIBuilderService iriBuilderService;

    @Autowired
    public OAAnnotationCollectionServiceImpl(AnnotationStoreRepository annotationStoreRepository, AnnotationCollectionStoreRepository annotationCollectionStoreRepository, AnnotationSearchRepository annotationSearchRepository, IRIBuilderService iriBuilderService, OAAnnotationPageService oaAnnotationPageService, @Qualifier("collectionIdGenerator") IDGenerator idGenerator, @Value("${annotation.page.size}") int pageSize) {
        super(annotationStoreRepository, annotationCollectionStoreRepository, annotationSearchRepository, idGenerator, pageSize);
        this.oaAnnotationPageService = oaAnnotationPageService;
        this.iriBuilderService = iriBuilderService;
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
    protected ServiceResponse<OAAnnotationPage> buildFirstAnnotationPage(SearchType searchType, String searchQuery, ClientPreference clientPref) {
        if (searchType.equals(SearchType.COLLECTION_ID)) {
            if (clientPref.equals(ClientPreference.CONTAINED_IRIS)) {
                return oaAnnotationPageService.getAnnotationPage(searchQuery, false, 0);
            } else {
                return oaAnnotationPageService.getAnnotationPage(searchQuery, true, 0);
            }
        } else if (searchType.equals(SearchType.TARGET_IRI)) {
            if (clientPref.equals(ClientPreference.CONTAINED_IRIS)) {
                return oaAnnotationPageService.searchAnnotationPage(searchQuery, false, 0);
            } else {
                return oaAnnotationPageService.searchAnnotationPage(searchQuery, true, 0);
            }
        } else {
            throw new IllegalArgumentException(String.format("Unexpected SearchType [%s]", searchType));
        }
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
