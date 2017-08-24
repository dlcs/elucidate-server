package com.digirati.elucidate.service.batch.impl;

import com.digirati.elucidate.common.infrastructure.constants.*;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;
import com.digirati.elucidate.infrastructure.batch.function.AnnotationBatchSearch;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.ServiceResponse.Status;
import com.digirati.elucidate.model.batch.AbstractBatchOperation;
import com.digirati.elucidate.service.batch.AbstractAnnotationBatchUpdateService;
import com.digirati.elucidate.service.query.AbstractAnnotationService;
import com.digirati.elucidate.service.search.AbstractAnnotationSearchService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractAnnotationBatchUpdateServiceImpl<A extends AbstractAnnotation, B extends AbstractBatchOperation> implements AbstractAnnotationBatchUpdateService<B> {

    protected final Logger LOGGER = Logger.getLogger(getClass());

    private AbstractAnnotationService<A> annotationService;
    private AbstractAnnotationSearchService<A> annotationSearchService;

    protected AbstractAnnotationBatchUpdateServiceImpl(AbstractAnnotationService<A> annotationService, AbstractAnnotationSearchService<A> annotationSearchService) {
        this.annotationService = annotationService;
        this.annotationSearchService = annotationSearchService;
    }

    @Override
    public ServiceResponse<B> processBatchUpdate(B batchOperation) {

        Map<String, Object> jsonMap = batchOperation.getJsonMap();

        processBatchUpdates(jsonMap, OAConstants.URI_HAS_BODY, (List<String> _searchFields, String _searchValue) -> {
            ServiceResponse<List<A>> serviceResponse = annotationSearchService.searchAnnotationsByBody(_searchFields, _searchValue, true, null, null, null, null);
            return serviceResponse.getObj();
        });

        processBatchUpdates(jsonMap, OAConstants.URI_HAS_TARGET, (List<String> _searchFields, String _searchValue) -> {
            ServiceResponse<List<A>> serviceResponse = annotationSearchService.searchAnnotationsByTarget(_searchFields, _searchValue, true, null, null, null, null);
            return serviceResponse.getObj();
        });

        return new ServiceResponse<B>(Status.OK, batchOperation);
    }

    @SuppressWarnings("unchecked")
    private void processBatchUpdates(Map<String, Object> batchJsonMap, String jsonKey, AnnotationBatchSearch<A> annotationBatchSearch) {

        List<Map<String, Object>> jsonMaps = (List<Map<String, Object>>) batchJsonMap.get(jsonKey);
        if (jsonMaps != null && !jsonMaps.isEmpty()) {

            for (Map<String, Object> jsonMap : jsonMaps) {

                String id = (String) jsonMap.get(JSONLDConstants.ATTRIBUTE_ID);
                if (StringUtils.isNotBlank(id)) {
                    processBatchUpdate(jsonMap, jsonKey, annotationBatchSearch, SearchConstants.FIELD_ID, id);
                }

                List<Map<String, Object>> sourceJsonMaps = (List<Map<String, Object>>) jsonMap.get(OAConstants.URI_HAS_SOURCE);
                if (sourceJsonMaps != null && !sourceJsonMaps.isEmpty()) {

                    for (Map<String, Object> sourceJsonMap : sourceJsonMaps) {

                        String sourceIri = (String) sourceJsonMap.get(JSONLDConstants.ATTRIBUTE_ID);
                        if (StringUtils.isNotBlank(sourceIri)) {
                            processBatchUpdate(sourceJsonMap, jsonKey, annotationBatchSearch, SearchConstants.FIELD_SOURCE, sourceIri);
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings({"unchecked", "serial"})
    private void processBatchUpdate(Map<String, Object> jsonMap, String jsonSelector, AnnotationBatchSearch<A> annotationBatchSearch, String searchField, String searchValue) {

        List<Map<String, Object>> isReplacedBys = (List<Map<String, Object>>) jsonMap.get(OAConstants.URI_IS_REPLACED_BY);
        if (isReplacedBys != null && isReplacedBys.size() == 1) {

            String newValue = (String) isReplacedBys.get(0).get(JSONLDConstants.ATTRIBUTE_VALUE);
            if (StringUtils.isNotBlank(newValue)) {

                List<A> annotations = annotationBatchSearch.searchForAnnotations(new ArrayList<String>() {
                    {
                        add(searchField);
                    }
                }, searchValue);

                int totalUpdated = 0;
                if (StringUtils.equals(searchField, SearchConstants.FIELD_ID)) {
                    totalUpdated = replaceAnnotationIds(annotations, jsonSelector, searchValue, newValue);
                } else if (StringUtils.equals(searchField, SearchConstants.FIELD_SOURCE)) {
                    totalUpdated = replaceAnnotationSourceIris(annotations, jsonSelector, searchValue, newValue);
                }

                insertTotal(jsonMap, totalUpdated);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private int replaceAnnotationIds(List<A> annotations, String jsonSelector, String oldId, String newId) {

        int total = annotations.size();
        int processed = 0;

        for (A annotation : annotations) {

            boolean annotationUpdated = false;

            List<Map<String, Object>> jsonMaps = (List<Map<String, Object>>) annotation.getJsonMap().get(jsonSelector);
            if (jsonMaps != null && !jsonMaps.isEmpty()) {

                for (Map<String, Object> jsonMap : jsonMaps) {

                    if (StringUtils.equalsIgnoreCase((String) jsonMap.get(JSONLDConstants.ATTRIBUTE_ID), oldId)) {
                        jsonMap.put(JSONLDConstants.ATTRIBUTE_ID, newId);
                        annotationUpdated = true;
                    }
                }
            }

            if (annotationUpdated) {
                ServiceResponse<A> serviceResponse = annotationService.updateAnnotation(annotation.getCollectionId(), annotation.getAnnotationId(), annotation, annotation.getCacheKey());
                Status status = serviceResponse.getStatus();
                if (!status.equals(Status.OK)) {
                    LOGGER.error(String.format("Got unexpected service response during batch update for Annotation [%s]: [%s]", annotation, status));
                } else {
                    LOGGER.info(String.format("Batch processed [%s] / [%s] Annotations", ++processed, total));
                }
            }
        }

        return total;
    }

    @SuppressWarnings("unchecked")
    private int replaceAnnotationSourceIris(List<A> annotations, String jsonSelector, String oldSourceIri, String newSourceIri) {

        int total = annotations.size();
        int processed = 0;

        for (A annotation : annotations) {

            boolean annotationUpdated = false;

            List<Map<String, Object>> jsonMaps = (List<Map<String, Object>>) annotation.getJsonMap().get(jsonSelector);
            if (jsonMaps != null && !jsonMaps.isEmpty()) {

                for (Map<String, Object> jsonMap : jsonMaps) {

                    List<Map<String, Object>> sourceJsonMaps = (List<Map<String, Object>>) jsonMap.get(OAConstants.URI_HAS_SOURCE);
                    if (sourceJsonMaps != null && !sourceJsonMaps.isEmpty()) {

                        for (Map<String, Object> sourceJsonMap : sourceJsonMaps) {

                            if (StringUtils.equalsIgnoreCase((String) sourceJsonMap.get(JSONLDConstants.ATTRIBUTE_ID), oldSourceIri)) {
                                sourceJsonMap.put(JSONLDConstants.ATTRIBUTE_ID, newSourceIri);
                                annotationUpdated = true;
                            }
                        }
                    }
                }
            }

            if (annotationUpdated) {
                ServiceResponse<A> serviceResponse = annotationService.updateAnnotation(annotation.getCollectionId(), annotation.getAnnotationId(), annotation, annotation.getCacheKey());
                Status status = serviceResponse.getStatus();
                if (!status.equals(Status.OK)) {
                    LOGGER.error(String.format("Got unexpected service response during batch update for Annotation [%s]: [%s]", annotation, status));
                } else {
                    LOGGER.info(String.format("Batch processed [%s] / [%s] Annotations", ++processed, total));
                }
            }
        }

        return total;
    }

    @SuppressWarnings("serial")
    private void insertTotal(Map<String, Object> jsonMap, int total) {

        jsonMap.put(ActivityStreamConstants.URI_TOTAL_ITEMS, new ArrayList<Map<String, Object>>() {
            {
                add(new HashMap<String, Object>() {
                    {
                        put(JSONLDConstants.ATTRIBUTE_TYPE, XMLSchemaConstants.URI_NON_NEGATIVE_INTEGER);
                        put(JSONLDConstants.ATTRIBUTE_VALUE, total);
                    }
                });
            }
        });
    }
}
