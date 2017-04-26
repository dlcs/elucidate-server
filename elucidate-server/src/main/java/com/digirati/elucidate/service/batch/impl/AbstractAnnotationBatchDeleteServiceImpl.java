package com.digirati.elucidate.service.batch.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.digirati.elucidate.common.infrastructure.constants.ActivityStreamConstants;
import com.digirati.elucidate.common.infrastructure.constants.JSONLDConstants;
import com.digirati.elucidate.common.infrastructure.constants.OAConstants;
import com.digirati.elucidate.common.infrastructure.constants.SearchConstants;
import com.digirati.elucidate.common.infrastructure.constants.XMLSchemaConstants;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;
import com.digirati.elucidate.infrastructure.batch.function.AnnotationBatchSearch;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.ServiceResponse.Status;
import com.digirati.elucidate.model.batch.AbstractBatchOperation;
import com.digirati.elucidate.service.batch.AbstractAnnotationBatchDeleteService;
import com.digirati.elucidate.service.query.AbstractAnnotationService;
import com.digirati.elucidate.service.search.AbstractAnnotationSearchService;

public abstract class AbstractAnnotationBatchDeleteServiceImpl<A extends AbstractAnnotation, B extends AbstractBatchOperation> implements AbstractAnnotationBatchDeleteService<B> {

    protected final Logger LOGGER = Logger.getLogger(getClass());

    private AbstractAnnotationService<A> annotationService;
    private AbstractAnnotationSearchService<A> annotationSearchService;

    protected AbstractAnnotationBatchDeleteServiceImpl(AbstractAnnotationService<A> annotationService, AbstractAnnotationSearchService<A> annotationSearchService) {
        this.annotationService = annotationService;
        this.annotationSearchService = annotationSearchService;
    }

    @Override
    public ServiceResponse<B> processBatchDelete(B batchOperation) {

        Map<String, Object> jsonMap = batchOperation.getJsonMap();

        processBatchDeletes(jsonMap, OAConstants.URI_HAS_BODY, (List<String> _searchFields, String _searchValue) -> {
            ServiceResponse<List<A>> serviceResponse = annotationSearchService.searchAnnotationsByBody(_searchFields, _searchValue, true, null, null, null);
            return serviceResponse.getObj();
        });

        processBatchDeletes(jsonMap, OAConstants.URI_HAS_TARGET, (List<String> _searchFields, String _searchValue) -> {
            ServiceResponse<List<A>> serviceResponse = annotationSearchService.searchAnnotationsByTarget(_searchFields, _searchValue, true, null, null, null);
            return serviceResponse.getObj();
        });

        return new ServiceResponse<B>(Status.OK, batchOperation);
    }

    @SuppressWarnings({"unchecked", "serial"})
    private void processBatchDeletes(Map<String, Object> batchJsonMap, String jsonKey, AnnotationBatchSearch<A> annotationBatchSearch) {

        List<Map<String, Object>> jsonMaps = (List<Map<String, Object>>) batchJsonMap.get(jsonKey);
        if (jsonMaps != null && !jsonMaps.isEmpty()) {

            for (Map<String, Object> jsonMap : jsonMaps) {

                String id = (String) jsonMap.get(JSONLDConstants.ATTRIBUTE_ID);
                if (StringUtils.isNotBlank(id)) {
                    int totalDeleted = processBatchDelete(jsonMap, annotationBatchSearch, new ArrayList<String>() {
                        {
                            add(SearchConstants.FIELD_ID);
                        }
                    }, id);
                    insertTotal(jsonMap, totalDeleted);
                }

                List<Map<String, Object>> sourceJsonMaps = (List<Map<String, Object>>) jsonMap.get(OAConstants.URI_HAS_SOURCE);
                if (sourceJsonMaps != null && !sourceJsonMaps.isEmpty()) {

                    for (Map<String, Object> sourceJsonMap : sourceJsonMaps) {

                        String sourceIri = (String) sourceJsonMap.get(JSONLDConstants.ATTRIBUTE_ID);
                        if (StringUtils.isNotBlank(sourceIri)) {
                            int totalDeleted = processBatchDelete(jsonMap, annotationBatchSearch, new ArrayList<String>() {
                                {
                                    add(SearchConstants.FIELD_SOURCE);
                                }
                            }, sourceIri);
                            insertTotal(jsonMap, totalDeleted);
                        }
                    }
                }
            }
        }
    }

    private int processBatchDelete(Map<String, Object> jsonMap, AnnotationBatchSearch<A> annotationBatchSearch, List<String> searchFields, String searchValue) {

        List<A> annotations = annotationBatchSearch.searchForAnnotations(searchFields, searchValue);
        if (annotations != null && !annotations.isEmpty()) {
            for (A annotation : annotations) {
                annotationService.deleteAnnotation(annotation.getCollectionId(), annotation.getAnnotationId(), annotation.getCacheKey());
            }
        }

        return annotations.size();
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
