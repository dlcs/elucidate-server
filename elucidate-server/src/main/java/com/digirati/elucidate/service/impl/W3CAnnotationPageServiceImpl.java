package com.digirati.elucidate.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationCollection;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationPage;
import com.digirati.elucidate.common.service.IRIBuilderService;
import com.digirati.elucidate.model.enumeration.SearchType;
import com.digirati.elucidate.service.W3CAnnotationPageService;
import com.digirati.elucidate.service.W3CAnnotationService;

@Service(W3CAnnotationPageServiceImpl.SERVICE_NAME)
public class W3CAnnotationPageServiceImpl extends AbstractAnnotationPageServiceImpl<W3CAnnotation, W3CAnnotationPage, W3CAnnotationCollection> implements W3CAnnotationPageService {

    public static final String SERVICE_NAME = "w3cAnnotationPageServiceImpl";

    private IRIBuilderService iriBuilderService;

    public W3CAnnotationPageServiceImpl(IRIBuilderService iriBuilderService, W3CAnnotationService w3cAnnotationService, @Value("${annotation.page.size}") int pageSize) {
        super(w3cAnnotationService, pageSize);
        this.iriBuilderService = iriBuilderService;
    }

    @Override
    protected W3CAnnotationPage convertToAnnotationPage(Map<String, Object> w3cAnnotationPageMap) {

        W3CAnnotationPage w3cAnnotationPage = new W3CAnnotationPage();
        w3cAnnotationPage.setJsonMap(w3cAnnotationPageMap);
        return w3cAnnotationPage;
    }

    @Override
    protected String buildCollectionIri(SearchType searchType, String searchQuery) {
        if (searchType.equals(SearchType.COLLECTION_ID)) {
            return iriBuilderService.buildW3CCollectionIri(searchQuery);
        } else if (searchType.equals(SearchType.TARGET_IRI)) {
            return iriBuilderService.buildW3CCollectionSearchIri(searchQuery);
        } else {
            throw new IllegalArgumentException(String.format("Unexpected SearchType [%s]", searchType));
        }
    }

    @Override
    protected String buildPageIri(SearchType searchType, String searchQuery, int page, boolean embeddedDescriptions) {
        if (searchType.equals(SearchType.COLLECTION_ID)) {
            return iriBuilderService.buildW3CPageIri(searchQuery, page, embeddedDescriptions);
        } else if (searchType.equals(SearchType.TARGET_IRI)) {
            return iriBuilderService.buildW3CPageSearchIri(searchQuery, page, embeddedDescriptions);
        } else {
            throw new IllegalArgumentException(String.format("Unexpected SearchType [%s]", searchType));
        }
    }
}
