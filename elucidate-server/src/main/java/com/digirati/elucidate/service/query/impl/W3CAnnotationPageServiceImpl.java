package com.digirati.elucidate.service.query.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationPage;
import com.digirati.elucidate.common.service.IRIBuilderService;
import com.digirati.elucidate.service.query.W3CAnnotationPageService;
import com.digirati.elucidate.service.query.W3CAnnotationService;

@Service(W3CAnnotationPageServiceImpl.SERVICE_NAME)
public class W3CAnnotationPageServiceImpl extends AbstractAnnotationPageServiceImpl<W3CAnnotation, W3CAnnotationPage> implements W3CAnnotationPageService {

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
    protected String buildCollectionIri(String searchQuery) {
        return iriBuilderService.buildW3CCollectionIri(searchQuery);
    }

    @Override
    protected String buildPageIri(String searchQuery, int page, boolean embeddedDescriptions) {
        return iriBuilderService.buildW3CPageIri(searchQuery, page, embeddedDescriptions);
    }
}
