package com.digirati.elucidate.service.history.impl;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.model.annotation.history.AnnotationHistory;
import com.digirati.elucidate.repository.AnnotationHistoryRepository;
import com.digirati.elucidate.service.history.AnnotationHistoryService;
import com.github.jsonldjava.utils.JsonUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service(AnnotationHistoryServiceImpl.SERVICE_NAME)
public class AnnotationHistoryServiceImpl implements AnnotationHistoryService {

    private static final Logger LOGGER = Logger.getLogger(AnnotationHistoryServiceImpl.class);

    public static final String SERVICE_NAME = "annotationHistoryServiceImpl";

    private AnnotationHistoryRepository annotationHistoryRepository;

    @Autowired
    public AnnotationHistoryServiceImpl(AnnotationHistoryRepository annotationHistoryRepository) {
        this.annotationHistoryRepository = annotationHistoryRepository;
    }

    @Override
    public void recordAnnotationHistory(W3CAnnotation w3cAnnotation) {
        int annotationPK = w3cAnnotation.getPk();
        String jsonStr = convertJsonMapToString(w3cAnnotation);
        LOGGER.info(String.format("Recording history for Annotation with PK [%s] and JSON [%s]", annotationPK, jsonStr));
        annotationHistoryRepository.createAnnotationHistory(annotationPK, jsonStr);
    }

    private String convertJsonMapToString(W3CAnnotation w3cAnnotation) {
        Map<String, Object> jsonMap = w3cAnnotation.getJsonMap();
        try {
            return JsonUtils.toPrettyString(jsonMap);
        } catch (IOException e) {
            throw new RuntimeException(String.format("An error occurred converting JSON Map {%s] to String", jsonMap), e);
        }
    }
}
