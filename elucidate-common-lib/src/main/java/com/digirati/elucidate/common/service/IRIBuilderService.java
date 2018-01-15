package com.digirati.elucidate.common.service;

import java.util.Date;
import java.util.List;

public interface IRIBuilderService {

    String buildW3CAnnotationIri(String collectionId, String annotationId);

    String buildW3CCollectionIri(String collectionId);

    String buildW3CPageIri(String collectionId, int page, boolean embeddedDescriptions);

    String buildW3CCollectionBodySearchIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, String generatorIri);

    String buildW3CPageBodySearchIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, String generatorIri, int page, boolean embeddedDescriptions);

    String buildW3CCollectionTargetSearchIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, String generatorIri);

    String buildW3CPageTargetSearchIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, String generatorIri, int page, boolean embeddedDescriptions);

    String buildW3CCollectionCreatorSearchIri(List<String> levels, String type, String value, boolean strict);

    String buildW3CPageCreatorSearchIri(List<String> levels, String type, String value, boolean strict, int page, boolean embeddedDescriptions);

    String buildW3CCollectionGeneratorSearchIri(List<String> levels, String type, String value, boolean strict);

    String buildW3CPageGeneratorSearchIri(List<String> levels, String type, String value, boolean strict, int page, boolean embeddedDescriptions);

    String buildW3CStatisticsPageIri(String type, String field, int page);

    String buildW3CAnnotationHistoryIri(String collectionId, String annotationId, int version);

    String buildW3CCollectionTemporalSearchIri(List<String> levels, List<String> types, Date since);

    String buildW3CPageTemporalSearchIri(List<String> levels, List<String> types, Date since, int page, boolean embeddedDescriptions);

    String buildOAAnnotationIri(String collectionId, String annotationId);

    String buildOACollectionIri(String collectionId);

    String buildOAPageIri(String collectionId, int page, boolean embeddedDescriptions);

    String buildOACollectionBodySearchIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, String generatorIri);

    String buildOAPageBodySearchIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, String generatorIri, int page, boolean embeddedDescriptions);

    String buildOACollectionTargetSearchIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, String generatorIri);

    String buildOAPageTargetSearchIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, String generatorIri, int page, boolean embeddedDescriptions);

    String buildOACollectionCreatorSearchIri(List<String> levels, String type, String value, boolean strict);

    String buildOAPageCreatorSearchIri(List<String> levels, String type, String value, boolean strict, int page, boolean embeddedDescriptions);

    String buildOACollectionGeneratorSearchIri(List<String> levels, String type, String value, boolean strict);

    String buildOAPageGeneratorSearchIri(List<String> levels, String type, String value, boolean strict, int page, boolean embeddedDescriptions);

    String buildOAStatisticsPageIri(String type, String field, int page);

    String buildOAAnnotationHistoryIri(String collectionId, String annotationId, int version);

    String buildOACollectionTemporalSearchIri(List<String> levels, List<String> types, Date since);

    String buildOAPageTemporalSearchIri(List<String> levels, List<String> types, Date since, int page, boolean embeddedDescriptions);
}
