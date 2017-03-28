package com.digirati.elucidate.service.search.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationCollection;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationPage;
import com.digirati.elucidate.common.model.enumeration.SearchType;
import com.digirati.elucidate.common.service.IRIBuilderService;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.enumeration.ClientPreference;
import com.digirati.elucidate.service.search.W3CAnnotationCollectionSearchService;
import com.digirati.elucidate.service.search.W3CAnnotationPageSearchService;
import com.digirati.elucidate.service.search.W3CAnnotationSearchService;

@Service(W3CAnnotationCollectionSearchServiceImpl.SERVICE_NAME)
public class W3CAnnotationCollectionSearchServiceImpl extends AbstractAnnotationCollectionSearchServiceImpl<W3CAnnotation, W3CAnnotationPage, W3CAnnotationCollection> implements W3CAnnotationCollectionSearchService {

    public static final String SERVICE_NAME = "w3cAnnotationCollectionSearchServiceImpl";

    private IRIBuilderService iriBuilderService;
    private W3CAnnotationPageSearchService w3cAnnotationPageSearchService;

    @Autowired
    public W3CAnnotationCollectionSearchServiceImpl(IRIBuilderService iriBuilderService, W3CAnnotationSearchService w3cAnnotationSearchService, W3CAnnotationPageSearchService w3cAnnotationPageSearchService, @Value("${annotation.page.size}") int pageSize) {
        super(w3cAnnotationSearchService, pageSize);
        this.iriBuilderService = iriBuilderService;
        this.w3cAnnotationPageSearchService = w3cAnnotationPageSearchService;
    }

    @Override
    protected W3CAnnotationCollection convertToAnnotationCollection(W3CAnnotationCollection w3cAnnotationCollection) {
        return w3cAnnotationCollection;
    }

    @Override
    protected ServiceResponse<W3CAnnotationPage> buildFirstAnnotationPage(SearchType searchType, List<W3CAnnotation> w3cAnnotations, List<String> fields, String value, boolean strict, String xywh, String t, ClientPreference clientPref) {
        if (searchType.equals(SearchType.BODY)) {
            if (clientPref.equals(ClientPreference.CONTAINED_IRIS)) {
                return w3cAnnotationPageSearchService.buildAnnotationPageByBody(w3cAnnotations, fields, value, strict, 0, false);
            } else {
                return w3cAnnotationPageSearchService.buildAnnotationPageByBody(w3cAnnotations, fields, value, strict, 0, true);
            }
        } else if (searchType.equals(SearchType.TARGET)) {
            if (clientPref.equals(ClientPreference.CONTAINED_IRIS)) {
                return w3cAnnotationPageSearchService.buildAnnotationPageByTarget(w3cAnnotations, fields, value, strict, xywh, t, 0, false);
            } else {
                return w3cAnnotationPageSearchService.buildAnnotationPageByTarget(w3cAnnotations, fields, value, strict, xywh, t, 0, true);
            }
        } else {
            throw new IllegalArgumentException(String.format("Unexpected search type [%s]", searchType));
        }
    }

    @Override
    protected String buildCollectionIri(SearchType searchType, List<String> fields, String value, boolean strict, String xywh, String t) {
        return iriBuilderService.buildW3CCollectionSearchIri(searchType, fields, value, strict, xywh, t);
    }

    @Override
    protected String buildPageIri(SearchType searchType, List<String> fields, String value, boolean strict, String xywh, String t, int page, boolean embeddedDescriptions) {
        return iriBuilderService.buildW3CPageSearchIri(searchType, fields, value, strict, xywh, t, page, embeddedDescriptions);
    }
}
