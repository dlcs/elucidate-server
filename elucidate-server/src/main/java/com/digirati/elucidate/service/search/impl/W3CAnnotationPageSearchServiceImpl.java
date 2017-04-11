package com.digirati.elucidate.service.search.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationPage;
import com.digirati.elucidate.common.model.enumeration.SearchType;
import com.digirati.elucidate.common.service.IRIBuilderService;
import com.digirati.elucidate.service.search.W3CAnnotationPageSearchService;

@Service(W3CAnnotationPageSearchServiceImpl.SERVICE_NAME)
public class W3CAnnotationPageSearchServiceImpl extends AbstractAnnotationPageSearchServiceImpl<W3CAnnotation, W3CAnnotationPage> implements W3CAnnotationPageSearchService {

    public static final String SERVICE_NAME = "w3cAnnotationPageSearchServiceImpl";

    private IRIBuilderService iriBuilderService;

    @Autowired
    public W3CAnnotationPageSearchServiceImpl(IRIBuilderService iriBuilderService, @Value("${annotation.page.size}") int pageSize) {
        super(pageSize);
        this.iriBuilderService = iriBuilderService;
    }

    @Override
    protected W3CAnnotationPage convertToAnnotationPage(Map<String, Object> w3cAnnotationPageMap) {

        W3CAnnotationPage w3cAnnotationPage = new W3CAnnotationPage();
        w3cAnnotationPage.setJsonMap(w3cAnnotationPageMap);
        return w3cAnnotationPage;
    }

    @Override
    protected String buildCollectionIri(SearchType searchType, List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri) {
        return iriBuilderService.buildW3CCollectionSearchIri(searchType, fields, value, strict, xywh, t, creatorIri);
    }

    @Override
    protected String buildPageIri(SearchType searchType, List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, int page, boolean embeddedDescriptions) {
        return iriBuilderService.buildW3CPageSearchIri(searchType, fields, value, strict, xywh, t, creatorIri, page, embeddedDescriptions);
    }
}
