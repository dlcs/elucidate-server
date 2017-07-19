package com.digirati.elucidate.service.history.impl;

import com.digirati.elucidate.common.model.annotation.oa.OAAnnotation;
import com.digirati.elucidate.common.service.IRIBuilderService;
import com.digirati.elucidate.converter.W3CToOAAnnotationCollectionConverter;
import com.digirati.elucidate.model.annotation.history.OAAnnotationHistory;
import com.digirati.elucidate.model.annotation.history.W3CAnnotationHistory;
import com.digirati.elucidate.repository.AnnotationHistoryRepository;
import com.digirati.elucidate.service.history.OAAnnotationHistoryService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service(OAAnnotationHistoryServiceImpl.SERVICE_NAME)
public class OAAnnotationHistoryServiceImpl extends AbstractAnnotationHistoryServiceImpl<OAAnnotation, OAAnnotationHistory> implements OAAnnotationHistoryService {

    public static final String SERVICE_NAME = "oaAnnotationHistoryServiceImpl";

    private IRIBuilderService iriBuilderService;

    @Autowired
    public OAAnnotationHistoryServiceImpl(AnnotationHistoryRepository annotationHistoryRepository, IRIBuilderService iriBuilderService) {
        super(annotationHistoryRepository);
        this.iriBuilderService = iriBuilderService;
    }

    @Override
    @SuppressWarnings("unchecked")
    public OAAnnotationHistory convertToAnnotationHistory(W3CAnnotationHistory w3CAnnotationHistory) {

        Map<String, Object> w3cAnnotationHistoryMap = w3CAnnotationHistory.getJsonMap();
        JsonNode w3cAnnotationHistoryNode = new ObjectMapper().convertValue(w3cAnnotationHistoryMap, JsonNode.class);

        JsonNode oaAnnotationHistoryNode = new W3CToOAAnnotationCollectionConverter().convert(w3cAnnotationHistoryNode);
        Map<String, Object> oaAnnotationHistoryMap = new ObjectMapper().convertValue(oaAnnotationHistoryNode, Map.class);

        OAAnnotationHistory oaAnnotationHistory = new OAAnnotationHistory();
        oaAnnotationHistory.setAnnotationId(w3CAnnotationHistory.getAnnotationId());
        oaAnnotationHistory.setCollectionId(w3CAnnotationHistory.getCollectionId());
        oaAnnotationHistory.setVersion(w3CAnnotationHistory.getVersion());
        oaAnnotationHistory.setCreatedDateTime(w3CAnnotationHistory.getCreatedDateTime());
        oaAnnotationHistory.setDeleted(w3CAnnotationHistory.isDeleted());
        oaAnnotationHistory.setModifiedDateTime(w3CAnnotationHistory.getModifiedDateTime());
        oaAnnotationHistory.setPk(w3CAnnotationHistory.getPk());
        oaAnnotationHistory.setJsonMap(oaAnnotationHistoryMap);

        return oaAnnotationHistory;
    }

    @Override
    protected String buildAnnotationIri(String collectionId, String annotationId) {
        return iriBuilderService.buildOAAnnotationIri(collectionId, annotationId);
    }
}
