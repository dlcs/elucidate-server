package com.digirati.elucidate.common.service;

import java.util.List;

import com.digirati.elucidate.common.model.enumeration.SearchType;

public interface IRIBuilderService {

    public String buildW3CAnnotationIri(String collectionId, String annotationId);

    public String buildW3CCollectionIri(String collectionId);

    public String buildW3CPageIri(String collectionId, int page, boolean embeddedDescriptions);

    public String buildW3CCollectionSearchIri(SearchType searchType, List<String> fields, String value, boolean strict, String xywh, String t);

    public String buildW3CPageSearchIri(SearchType searchType, List<String> fields, String value, boolean strict, String xywh, String t, int page, boolean embeddedDescriptions);

    public String buildOAAnnotationIri(String collectionId, String annotationId);

    public String buildOACollectionIri(String collectionId);

    public String buildOAPageIri(String collectionId, int page, boolean embeddedDescriptions);

    public String buildOACollectionSearchIri(SearchType searchType, List<String> fields, String value, boolean strict, String xywh, String t);

    public String buildOAPageSearchIri(SearchType searchType, List<String> fields, String value, boolean strict, String xywh, String t, int page, boolean embeddedDescriptions);
}
