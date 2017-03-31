package com.digirati.elucidate.service.search.impl;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.digirati.elucidate.common.infrastructure.constants.JSONLDConstants;
import com.digirati.elucidate.common.infrastructure.constants.OAConstants;
import com.digirati.elucidate.common.infrastructure.constants.RDFConstants;
import com.digirati.elucidate.common.infrastructure.constants.SearchConstants;
import com.digirati.elucidate.common.infrastructure.constants.SelectorConstants;
import com.digirati.elucidate.common.infrastructure.constants.URLConstants;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.infrastructure.util.SelectorUtils;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.ServiceResponse.Status;
import com.digirati.elucidate.model.annotation.selector.fragment.TFragmentSelector;
import com.digirati.elucidate.model.annotation.selector.fragment.XYWHFragmentSelector;
import com.digirati.elucidate.repository.AnnotationSearchRepository;
import com.digirati.elucidate.service.search.AbstractAnnotationSearchService;

public abstract class AbstractAnnotationSearchServiceImpl<A extends AbstractAnnotation> implements AbstractAnnotationSearchService<A> {

    protected final Logger LOGGER = Logger.getLogger(getClass());

    private AnnotationSearchRepository annotationSearchRepository;

    public AbstractAnnotationSearchServiceImpl(AnnotationSearchRepository annotationSearchRepository) {
        this.annotationSearchRepository = annotationSearchRepository;
    }

    protected abstract A convertToAnnotation(W3CAnnotation w3cAnnotation);

    protected abstract String buildAnnotationIri(String collectionId, String annotationId);

    @Override
    public ServiceResponse<List<A>> searchAnnotationsByBody(List<String> fields, String value, boolean strict) {

        boolean searchIds = fields.contains(SearchConstants.FIELD_ID);
        boolean searchSources = fields.contains(SearchConstants.FIELD_SOURCE);
        List<W3CAnnotation> w3cAnnotations = annotationSearchRepository.getAnnotationsByBody(searchIds, searchSources, value, strict);

        List<A> annotations = convertAnnotations(w3cAnnotations);
        return new ServiceResponse<List<A>>(Status.OK, annotations);
    }

    @Override
    public ServiceResponse<List<A>> searchAnnotationsByTarget(List<String> fields, String value, boolean strict, String xywh, String t) {

        boolean searchIds = fields.contains(SearchConstants.FIELD_ID);
        boolean searchSources = fields.contains(SearchConstants.FIELD_SOURCE);
        List<W3CAnnotation> w3cAnnotations = annotationSearchRepository.getAnnotationsByTarget(searchIds, searchSources, value, strict);

        if (StringUtils.isNotBlank(xywh) || StringUtils.isNotBlank(t)) {
            filterAnnotations(w3cAnnotations, searchIds, searchSources, value, strict, xywh, t);
        }

        List<A> annotations = convertAnnotations(w3cAnnotations);
        return new ServiceResponse<List<A>>(Status.OK, annotations);
    }

    @SuppressWarnings("unchecked")
    private void filterAnnotations(List<W3CAnnotation> w3cAnnotations, boolean searchIds, boolean searchSources, String value, boolean strict, String xywh, String t) {

        XYWHFragmentSelector xywhSearch = SelectorUtils.extractXywhSelector(String.format("%s=%s", URLConstants.PARAM_XYWH, xywh));
        TFragmentSelector tSearch = SelectorUtils.extractTSelector(String.format("%s=%s", URLConstants.PARAM_T, t));

        if (xywhSearch != null || tSearch != null) {

            Iterator<W3CAnnotation> iterator = w3cAnnotations.iterator();
            while (iterator.hasNext()) {

                W3CAnnotation w3cAnnotation = iterator.next();
                Map<String, Object> jsonMap = w3cAnnotation.getJsonMap();

                List<Map<String, Object>> targetJsonMaps = (List<Map<String, Object>>) jsonMap.get(OAConstants.URI_HAS_TARGET);
                if (targetJsonMaps != null && !targetJsonMaps.isEmpty()) {
                    for (Map<String, Object> targetJsonMap : targetJsonMaps) {

                        String id = extractId(targetJsonMap);
                        String source = extractSource(targetJsonMap);

                        if ((searchIds && (strict ? StringUtils.equalsIgnoreCase(id, value) : StringUtils.startsWith(id, value))) || (searchSources && (strict ? StringUtils.equalsIgnoreCase(source, value) : StringUtils.startsWith(source, value)))) {

                            XYWHFragmentSelector xywhSelector = SelectorUtils.extractXywhSelector(id);
                            TFragmentSelector tSelector = SelectorUtils.extractTSelector(id);

                            if (selectorsFiltersAnnotation(xywhSearch, xywhSelector, tSearch, tSelector)) {
                                iterator.remove();
                                break;
                            }

                            List<Map<String, Object>> selectorJsonMaps = (List<Map<String, Object>>) targetJsonMap.get(OAConstants.URI_HAS_SELECTOR);
                            if (selectorJsonMaps == null || selectorJsonMaps.isEmpty()) {
                                break;
                            }

                            boolean hasSelector = false;
                            boolean isFiltered = true;

                            for (Map<String, Object> selectorJsonMap : selectorJsonMaps) {

                                String type = extractType(selectorJsonMap);
                                String conformsTo = extractConformsTo(selectorJsonMap);
                                if (!StringUtils.equalsIgnoreCase(type, OAConstants.URI_FRAGMENT_SELECTOR) || !StringUtils.equalsIgnoreCase(conformsTo, SelectorConstants.MEDIA_FRAGS)) {
                                    continue;
                                }

                                String selectorValue = extractValue(selectorJsonMap);
                                xywhSelector = SelectorUtils.extractXywhSelector(selectorValue);
                                tSelector = SelectorUtils.extractTSelector(selectorValue);

                                if (xywhSearch != null || tSelector != null) {
                                    hasSelector = true;
                                }

                                if (!selectorsFiltersAnnotation(xywhSearch, xywhSelector, tSearch, tSelector)) {
                                    isFiltered = false;
                                    break;
                                }
                            }

                            if (hasSelector && isFiltered) {
                                iterator.remove();
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    private String extractId(Map<String, Object> jsonMap) {

        String id = (String) jsonMap.get(JSONLDConstants.ATTRIBUTE_ID);
        if (StringUtils.isBlank(id)) {
            return null;
        }

        return id;
    }

    @SuppressWarnings("unchecked")
    private String extractSource(Map<String, Object> jsonMap) {

        List<Map<String, Object>> sourceJsonMaps = (List<Map<String, Object>>) jsonMap.get(OAConstants.URI_HAS_SOURCE);
        if (sourceJsonMaps == null || sourceJsonMaps.size() != 1) {
            return null;
        }

        Map<String, Object> sourceJsonMap = sourceJsonMaps.get(0);
        return extractId(sourceJsonMap);
    }

    @SuppressWarnings("unchecked")
    private String extractType(Map<String, Object> jsonMap) {

        List<String> types = (List<String>) jsonMap.get(JSONLDConstants.ATTRBUTE_TYPE);
        if (types == null || types.size() != 1) {
            return null;
        }

        String type = types.get(0);
        if (StringUtils.isBlank(type)) {
            return null;
        }

        return type;
    }

    @SuppressWarnings("unchecked")
    private String extractConformsTo(Map<String, Object> jsonMap) {

        List<Map<String, Object>> conformsToJsonMaps = (List<Map<String, Object>>) jsonMap.get(OAConstants.URI_CONFORMS_TO);
        if (conformsToJsonMaps == null || conformsToJsonMaps.size() != 1) {
            return null;
        }

        Map<String, Object> conformsToJsonMap = conformsToJsonMaps.get(0);
        return extractId(conformsToJsonMap);
    }

    @SuppressWarnings("unchecked")
    private String extractValue(Map<String, Object> jsonMap) {

        List<Map<String, Object>> valueJsonMaps = (List<Map<String, Object>>) jsonMap.get(RDFConstants.URI_VALUE);
        if (valueJsonMaps == null || valueJsonMaps.size() != 1) {
            return null;
        }

        Map<String, Object> valueJsonMap = valueJsonMaps.get(0);

        String value = (String) valueJsonMap.get(JSONLDConstants.ATTRIBUTE_VALUE);
        if (StringUtils.isBlank(value)) {
            return null;
        }

        return value;
    }

    private boolean selectorsFiltersAnnotation(XYWHFragmentSelector xywhSearch, XYWHFragmentSelector xywhTarget, TFragmentSelector tSearch, TFragmentSelector tValue) {
        return xywhSelectorsFilterAnnotation(xywhSearch, xywhTarget) || tSelectorFiltersAnnotation(tSearch, tValue);
    }

    private boolean xywhSelectorsFilterAnnotation(XYWHFragmentSelector xywhSearchValue, XYWHFragmentSelector xywhTargetValue) {
        if (xywhSearchValue != null && xywhTargetValue != null) {
            if ((xywhSearchValue.getX() + xywhSearchValue.getW()) <= xywhTargetValue.getX()) {
                return true;
            } else if (xywhSearchValue.getX() >= (xywhTargetValue.getX() + xywhTargetValue.getW())) {
                return true;
            } else if ((xywhSearchValue.getY() + xywhSearchValue.getH()) <= xywhTargetValue.getY()) {
                return true;
            } else if (xywhSearchValue.getY() >= (xywhTargetValue.getY() + xywhTargetValue.getH())) {
                return true;
            }
        }
        return false;
    }

    private boolean tSelectorFiltersAnnotation(TFragmentSelector tSearchValue, TFragmentSelector tTargetValue) {
        if (tSearchValue != null && tTargetValue != null) {
            if (tSearchValue.getStart() >= tTargetValue.getEnd()) {
                return true;
            } else if (tSearchValue.getEnd() <= tTargetValue.getStart()) {
                return true;
            }
        }
        return false;
    }

    private List<A> convertAnnotations(List<W3CAnnotation> w3cAnnotations) {
        List<A> annotations = new ArrayList<A>();
        for (W3CAnnotation w3cAnnotation : w3cAnnotations) {
            A annotation = convertToAnnotation(w3cAnnotation);
            annotation.getJsonMap().put(JSONLDConstants.ATTRIBUTE_ID, buildAnnotationIri(annotation.getCollectionId(), annotation.getAnnotationId()));
            annotations.add(annotation);
        }
        return annotations;
    }
}
