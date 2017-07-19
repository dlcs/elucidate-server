package com.digirati.elucidate.service.search.impl;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationPage;
import com.digirati.elucidate.common.service.IRIBuilderService;
import com.digirati.elucidate.service.search.W3CAnnotationPageSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
    protected String buildBodySearchCollectionIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, String generatorIri) {
        return iriBuilderService.buildW3CCollectionBodySearchIri(fields, value, strict, xywh, t, creatorIri, generatorIri);
    }

    @Override
    protected String buildBodySearchPageIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, String generatorIri, int page, boolean embeddedDescriptions) {
        return iriBuilderService.buildW3CPageBodySearchIri(fields, value, strict, xywh, t, creatorIri, generatorIri, page, embeddedDescriptions);
    }

    @Override
    protected String buildTargetSearchCollectionIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, String generatorIri) {
        return iriBuilderService.buildW3CCollectionTargetSearchIri(fields, value, strict, xywh, t, creatorIri, generatorIri);
    }

    @Override
    protected String buildTargetSearchPageIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, String generatorIri, int page, boolean embeddedDescriptions) {
        return iriBuilderService.buildW3CPageTargetSearchIri(fields, value, strict, xywh, t, creatorIri, generatorIri, page, embeddedDescriptions);
    }

    @Override
    protected String buildCreatorSearchCollectionIri(List<String> levels, String type, String value) {
        return iriBuilderService.buildW3CCollectionCreatorSearchIri(levels, type, value);
    }

    @Override
    protected String buildCreatorSearchPageIri(List<String> levels, String type, String value, int page, boolean embeddedDescriptions) {
        return iriBuilderService.buildW3CPageCreatorSearchIri(levels, type, value, page, embeddedDescriptions);
    }

    @Override
    protected String buildGeneratorSearchCollectionIri(List<String> levels, String type, String value) {
        return iriBuilderService.buildW3CCollectionGeneratorSearchIri(levels, type, value);
    }

    @Override
    protected String buildGeneratorSearchPageIri(List<String> levels, String type, String value, int page, boolean embeddedDescriptions) {
        return iriBuilderService.buildW3CPageGeneratorSearchIri(levels, type, value, page, embeddedDescriptions);
    }
}
