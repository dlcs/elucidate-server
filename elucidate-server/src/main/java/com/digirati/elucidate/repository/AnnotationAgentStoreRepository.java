package com.digirati.elucidate.repository;

import com.digirati.elucidate.model.annotation.agent.AnnotationAgent;

public interface AnnotationAgentStoreRepository {

    AnnotationAgent createAnnotationCreator(Integer annotationPK, Integer bodyPK, Integer targetPK, String creatorIri, String creatorJson, String[] types, String[] typesJson, String[] names, String[] namesJson, String nickname, String[] emails, String[] emailsJson, String[] emailSha1s, String[] emailSha1sJson, String[] homepages, String[] homepagesJson);

    AnnotationAgent deleteAnnotationCreators(Integer annotationPK, Integer bodyPK, Integer targetPK);

    AnnotationAgent createAnnotationGenerator(Integer annotationPk, Integer bodyPk, Integer targetPk, String generatorIri, String generatorJson, String[] types, String[] typesJson, String[] names, String[] namesJson, String nickname, String[] emails, String[] emailsJson, String[] emailSha1s, String[] emailSha1sJson, String[] homepages, String[] homepagesJson);

    AnnotationAgent deleteAnnotationGenerators(Integer annotationPK, Integer bodyPK, Integer targetPK);
}
