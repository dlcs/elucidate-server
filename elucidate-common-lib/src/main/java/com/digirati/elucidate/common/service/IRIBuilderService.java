package com.digirati.elucidate.common.service;

import java.util.List;

public interface IRIBuilderService {

    String buildW3CAnnotationIri(String collectionId, String annotationId);

    String buildW3CCollectionIri(String collectionId);

    String buildW3CPageIri(String collectionId, int page, boolean embeddedDescriptions);

    String buildW3CCollectionBodySearchIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri);

    String buildW3CPageBodySearchIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, int page, boolean embeddedDescriptions);

    String buildW3CCollectionTargetSearchIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri);

    String buildW3CPageTargetSearchIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, int page, boolean embeddedDescriptions);

    String buildW3CCollectionCreatorSearchIri(List<String> levels, String type, String value);

    String buildW3CPageCreatorSearchIri(List<String> levels, String type, String value, int page, boolean embeddedDescriptions);

    String buildW3CStatisticsPageBodyIri(String field, int page);

    String buildW3CAnnotationHistoryIri(String collectionId, String annotationId, int version);

    String buildOAAnnotationIri(String collectionId, String annotationId);

    String buildOACollectionIri(String collectionId);

    String buildOAPageIri(String collectionId, int page, boolean embeddedDescriptions);

    String buildOACollectionBodySearchIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri);

    String buildOAPageBodySearchIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, int page, boolean embeddedDescriptions);

    String buildOACollectionTargetSearchIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri);

    String buildOAPageTargetSearchIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, int page, boolean embeddedDescriptions);

    String buildOACollectionCreatorSearchIri(List<String> levels, String type, String value);

    String buildOAPageCreatorSearchIri(List<String> levels, String type, String value, int page, boolean embeddedDescriptions);

    String buildOAStatisticsPageBodyIri(String field, int page);

    String buildOAAnnotationHistoryIri(String collectionId, String annotationId, int version);
}
