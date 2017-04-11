package com.digirati.elucidate.service.search.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.digirati.elucidate.common.infrastructure.constants.JSONLDConstants;
import com.digirati.elucidate.common.infrastructure.constants.SearchConstants;
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
    public ServiceResponse<List<A>> searchAnnotationsByBody(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri) {

        boolean searchIds = fields.contains(SearchConstants.FIELD_ID);
        boolean searchSources = fields.contains(SearchConstants.FIELD_SOURCE);
        Integer[] xywhParameters = buildXywhParameters(xywh);
        Integer[] tParameters = buildTParameters(t);

        List<W3CAnnotation> w3cAnnotations = annotationSearchRepository.getAnnotationsByBody(searchIds, searchSources, value, strict, xywhParameters[0], xywhParameters[1], xywhParameters[2], xywhParameters[3], tParameters[0], tParameters[1], creatorIri);

        List<A> annotations = convertAnnotations(w3cAnnotations);
        return new ServiceResponse<List<A>>(Status.OK, annotations);
    }

    @Override
    public ServiceResponse<List<A>> searchAnnotationsByTarget(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri) {

        boolean searchIds = fields.contains(SearchConstants.FIELD_ID);
        boolean searchSources = fields.contains(SearchConstants.FIELD_SOURCE);
        Integer[] xywhParameters = buildXywhParameters(xywh);
        Integer[] tParameters = buildTParameters(t);

        List<W3CAnnotation> w3cAnnotations = annotationSearchRepository.getAnnotationsByTarget(searchIds, searchSources, value, strict, xywhParameters[0], xywhParameters[1], xywhParameters[2], xywhParameters[3], tParameters[0], tParameters[1], creatorIri);

        List<A> annotations = convertAnnotations(w3cAnnotations);
        return new ServiceResponse<List<A>>(Status.OK, annotations);
    }

    private Integer[] buildXywhParameters(String xywh) {

        if (StringUtils.isNotBlank(xywh)) {

            xywh = String.format("%s=%s", URLConstants.PARAM_XYWH, xywh);

            XYWHFragmentSelector xywhFragmentSelector = SelectorUtils.extractXywhFragmentSelector(xywh);
            if (xywhFragmentSelector != null) {
                return new Integer[] {xywhFragmentSelector.getX(), xywhFragmentSelector.getY(), xywhFragmentSelector.getW(), xywhFragmentSelector.getH()};
            }
        }

        return new Integer[] {null, null, null, null};
    }

    private Integer[] buildTParameters(String t) {

        if (StringUtils.isNotBlank(t)) {

            t = String.format("%s=%s", URLConstants.PARAM_T, t);

            TFragmentSelector tFragmentSelector = SelectorUtils.extractTFragmentSelector(t);
            if (tFragmentSelector != null) {
                return new Integer[] {tFragmentSelector.getStart(), tFragmentSelector.getEnd()};
            }
        }

        return new Integer[] {null, null};
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
