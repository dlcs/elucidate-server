package com.digirati.elucidate.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.digirati.elucidate.common.model.annotation.oa.OAAnnotation;
import com.digirati.elucidate.common.model.annotation.oa.OAAnnotationCollection;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.common.service.IRIBuilderService;
import com.digirati.elucidate.converter.OAToW3CAnnotationConverter;
import com.digirati.elucidate.converter.W3CToOAAnnotationConverter;
import com.digirati.elucidate.infrastructure.generator.IDGenerator;
import com.digirati.elucidate.repository.AnnotationSearchRepository;
import com.digirati.elucidate.repository.AnnotationStoreRepository;
import com.digirati.elucidate.service.OAAnnotationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service(OAAnnotationServiceImpl.SERVICE_NAME)
public class OAAnnotationServiceImpl extends AbstractAnnotationServiceImpl<OAAnnotation, OAAnnotationCollection> implements OAAnnotationService {

    public static final String SERVICE_NAME = "oaAnnotationServiceImpl";

    private IRIBuilderService iriBuilderService;

    @Autowired
    public OAAnnotationServiceImpl(IRIBuilderService iriBuilderService, AnnotationStoreRepository annotationRepository, AnnotationSearchRepository annotationSearchRepository, @Qualifier("annotationIdGenerator") IDGenerator idGenerator) {
        super(annotationRepository, annotationSearchRepository, idGenerator);
        this.iriBuilderService = iriBuilderService;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected OAAnnotation convertToAnnotation(W3CAnnotation w3cAnnotation) {

        Map<String, Object> w3cAnnotationMap = w3cAnnotation.getJsonMap();
        JsonNode w3cAnnotationNode = new ObjectMapper().convertValue(w3cAnnotationMap, JsonNode.class);

        JsonNode oaAnnotationNode = new W3CToOAAnnotationConverter().convert(w3cAnnotationNode);
        Map<String, Object> oaAnnotationMap = new ObjectMapper().convertValue(oaAnnotationNode, Map.class);

        OAAnnotation oaAnnotation = new OAAnnotation();
        oaAnnotation.setCacheKey(w3cAnnotation.getCacheKey());
        oaAnnotation.setCollectionId(w3cAnnotation.getCollectionId());
        oaAnnotation.setCreatedDateTime(w3cAnnotation.getCreatedDateTime());
        oaAnnotation.setDeleted(w3cAnnotation.isDeleted());
        oaAnnotation.setAnnotationId(w3cAnnotation.getAnnotationId());
        oaAnnotation.setJsonMap(oaAnnotationMap);
        oaAnnotation.setModifiedDateTime(w3cAnnotation.getModifiedDateTime());
        return oaAnnotation;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected W3CAnnotation convertFromAnnotation(OAAnnotation oaAnnotation) {

        Map<String, Object> oaAnnotationMap = oaAnnotation.getJsonMap();
        JsonNode oaAnnotationNode = new ObjectMapper().convertValue(oaAnnotationMap, JsonNode.class);

        JsonNode w3cAnnotationNode = new OAToW3CAnnotationConverter().convert(oaAnnotationNode);
        Map<String, Object> w3cAnnotationMap = new ObjectMapper().convertValue(w3cAnnotationNode, Map.class);

        W3CAnnotation w3cAnnotation = new W3CAnnotation();
        w3cAnnotation.setCacheKey(oaAnnotation.getCacheKey());
        w3cAnnotation.setCollectionId(oaAnnotation.getCollectionId());
        w3cAnnotation.setCreatedDateTime(oaAnnotation.getCreatedDateTime());
        w3cAnnotation.setDeleted(oaAnnotation.isDeleted());
        w3cAnnotation.setAnnotationId(oaAnnotation.getAnnotationId());
        w3cAnnotation.setJsonMap(w3cAnnotationMap);
        w3cAnnotation.setModifiedDateTime(oaAnnotation.getModifiedDateTime());
        return w3cAnnotation;
    }

    @Override
    protected String buildAnnotationIri(String collectionId, String annotationId) {
        return iriBuilderService.buildOAAnnotationIri(collectionId, annotationId);
    }
}
