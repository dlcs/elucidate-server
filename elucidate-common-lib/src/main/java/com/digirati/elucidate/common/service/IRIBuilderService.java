package com.digirati.elucidate.common.service;

import java.util.List;

public interface IRIBuilderService {

    public String buildW3CAnnotationIri(String collectionId, String annotationId);

    public String buildW3CCollectionIri(String collectionId);

    public String buildW3CPageIri(String collectionId, int page, boolean embeddedDescriptions);

    public String buildW3CCollectionBodySearchIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri);

    public String buildW3CPageBodySearchIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, int page, boolean embeddedDescriptions);

    public String buildW3CCollectionTargetSearchIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri);

    public String buildW3CPageTargetSearchIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, int page, boolean embeddedDescriptions);

    public String buildW3CCollectionCreatorSearchIri(List<String> levels, String type, String value);

    public String buildW3CPageCreatorSearchIri(List<String> levels, String type, String value, int page, boolean embeddedDescriptions);

    public String buildW3CStatisticsPageBodyIri(String field, int page);

    public String buildOAAnnotationIri(String collectionId, String annotationId);

    public String buildOACollectionIri(String collectionId);

    public String buildOAPageIri(String collectionId, int page, boolean embeddedDescriptions);

    public String buildOACollectionBodySearchIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri);

    public String buildOAPageBodySearchIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, int page, boolean embeddedDescriptions);

    public String buildOACollectionTargetSearchIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri);

    public String buildOAPageTargetSearchIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, int page, boolean embeddedDescriptions);

    public String buildOACollectionCreatorSearchIri(List<String> levels, String type, String value);

    public String buildOAPageCreatorSearchIri(List<String> levels, String type, String value, int page, boolean embeddedDescriptions);

    public String buildOAStatisticsPageBodyIri(String field, int page);
}
