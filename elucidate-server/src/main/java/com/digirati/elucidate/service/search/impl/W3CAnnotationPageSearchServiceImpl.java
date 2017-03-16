package com.digirati.elucidate.service.search.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationPage;
import com.digirati.elucidate.common.service.IRIBuilderService;
import com.digirati.elucidate.service.search.W3CAnnotationPageSearchService;
import com.digirati.elucidate.service.search.W3CAnnotationSearchService;

@Service(W3CAnnotationPageSearchServiceImpl.SERVICE_NAME)
public class W3CAnnotationPageSearchServiceImpl extends AbstractAnnotationPageSearchServiceImpl<W3CAnnotation, W3CAnnotationPage> implements W3CAnnotationPageSearchService {

    public static final String SERVICE_NAME = "w3cAnnotationPageSearchServiceImpl";

    private IRIBuilderService iriBuilderService;

    @Autowired
    public W3CAnnotationPageSearchServiceImpl(IRIBuilderService iriBuilderService, W3CAnnotationSearchService w3cAnnotationSearchService, @Value("${annotation.page.size}") int pageSize) {
        super(w3cAnnotationSearchService, pageSize);
        this.iriBuilderService = iriBuilderService;
    }

    @Override
    protected W3CAnnotationPage convertToAnnotationPage(Map<String, Object> w3cAnnotationPageMap) {

        W3CAnnotationPage w3cAnnotationPage = new W3CAnnotationPage();
        w3cAnnotationPage.setJsonMap(w3cAnnotationPageMap);
        return w3cAnnotationPage;
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
