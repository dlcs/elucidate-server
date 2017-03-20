package com.digirati.elucidate.service.search.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.digirati.elucidate.common.infrastructure.constants.JSONLDConstants;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.ServiceResponse.Status;
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
    @Transactional(readOnly = false)
    public ServiceResponse<List<A>> searchAnnotations(String targetIri, boolean strict, String box) {

        List<W3CAnnotation> w3cAnnotations = annotationSearchRepository.getAnnotationsByTargetIri(targetIri, strict, box);

        List<A> annotations = new ArrayList<A>();
        for (W3CAnnotation w3cAnnotation : w3cAnnotations) {
            A annotation = convertToAnnotation(w3cAnnotation);
            annotation.getJsonMap().put(JSONLDConstants.ATTRIBUTE_ID, buildAnnotationIri(annotation.getCollectionId(), annotation.getAnnotationId()));
            annotations.add(annotation);
        }

        return new ServiceResponse<List<A>>(Status.OK, annotations);
    }
}
