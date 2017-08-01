package com.digirati.elucidate.repository;

import com.digirati.elucidate.model.annotation.body.AnnotationBody;

import java.util.List;

public interface AnnotationBodyStoreRepository {

    AnnotationBody createAnnotationBody(int annotationPK, String bodyIri, String sourceIri, String bodyJson);

    List<AnnotationBody> deletedAnnotationBodies(int annotationPK);
}
