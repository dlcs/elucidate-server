package com.digirati.elucidate.service.search.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationCollection;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationPage;
import com.digirati.elucidate.common.service.IRIBuilderService;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.enumeration.ClientPreference;
import com.digirati.elucidate.repository.AnnotationSearchRepository;
import com.digirati.elucidate.service.search.W3CAnnotationCollectionSearchService;
import com.digirati.elucidate.service.search.W3CAnnotationPageSearchService;

@Service(W3CAnnotationCollectionSearchServiceImpl.SERVICE_NAME)
public class W3CAnnotationCollectionSearchServiceImpl extends AbstractAnnotationCollectionSearchServiceImpl<W3CAnnotationPage, W3CAnnotationCollection> implements W3CAnnotationCollectionSearchService {

    public static final String SERVICE_NAME = "w3cAnnotationCollectionSearchServiceImpl";

    private IRIBuilderService iriBuilderService;
    private W3CAnnotationPageSearchService w3cAnnotationPageSearchService;

    @Autowired
    public W3CAnnotationCollectionSearchServiceImpl(AnnotationSearchRepository annotationSearchRepository, IRIBuilderService iriBuilderService, W3CAnnotationPageSearchService w3cAnnotationPageSearchService, @Value("${annotation.page.size}") int pageSize) {
        super(annotationSearchRepository, pageSize);
        this.iriBuilderService = iriBuilderService;
        this.w3cAnnotationPageSearchService = w3cAnnotationPageSearchService;
    }

    @Override
    protected W3CAnnotationCollection convertToAnnotationCollection(W3CAnnotationCollection w3cAnnotationCollection) {
        return w3cAnnotationCollection;
    }

    @Override
    protected ServiceResponse<W3CAnnotationPage> buildFirstAnnotationPage(String targetIri, boolean strict, ClientPreference clientPref) {
        if (clientPref.equals(ClientPreference.CONTAINED_IRIS)) {
            return w3cAnnotationPageSearchService.searchAnnotationPage(targetIri, strict, 0, false);
        } else {
            return w3cAnnotationPageSearchService.searchAnnotationPage(targetIri, strict, 0, true);
        }
    }

    @Override
    protected String buildCollectionIri(String targetIri, boolean strict) {
        return iriBuilderService.buildW3CCollectionSearchIri(targetIri, strict);
    }

    @Override
    protected String buildPageIri(String targetIri, boolean strict, int page, boolean embeddedDescriptions) {
        return iriBuilderService.buildW3CPageSearchIri(targetIri, strict, page, embeddedDescriptions);
    }
}
