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
import com.digirati.elucidate.repository.AnnotationCollectionStoreRepository;
import com.digirati.elucidate.repository.AnnotationStoreRepository;
import com.digirati.elucidate.service.OAAnnotationCollectionService;
import com.digirati.elucidate.service.OAAnnotationPageService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service(OAAnnotationCollectionServiceImpl.SERVICE_NAME)
public class OAAnnotationCollectionServiceImpl extends AbstractAnnotationCollectionServiceImpl<OAAnnotation, OAAnnotationPage, OAAnnotationCollection> implements OAAnnotationCollectionService {

    public static final String SERVICE_NAME = "oaAnnotationCollectionServiceImpl";

    private IRIBuilderService iriBuilderService;

    @Autowired
    public OAAnnotationCollectionServiceImpl(IRIBuilderService iriBuilderService, OAAnnotationPageService oaAnnotationPageService, AnnotationStoreRepository annotationRepository, AnnotationCollectionStoreRepository annotationCollectionRepository, @Qualifier("collectionIdGenerator") IDGenerator idGenerator, @Value("${annotation.page.size}") int pageSize) {
        super(oaAnnotationPageService, annotationRepository, annotationCollectionRepository, idGenerator, pageSize);
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
    protected String buildCollectionIri(String collectionId) {
        return iriBuilderService.buildOACollectionIri(collectionId);
    }

    @Override
    protected String buildPageIri(String collectionId, int page, boolean embeddedDescriptions) {
        return iriBuilderService.buildOAPageIri(collectionId, page, embeddedDescriptions);
    }
}
