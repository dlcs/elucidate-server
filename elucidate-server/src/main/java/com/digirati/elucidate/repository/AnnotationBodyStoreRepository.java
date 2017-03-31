package com.digirati.elucidate.repository;

import com.digirati.elucidate.model.annotation.body.AnnotationBody;

import java.util.List;

public interface AnnotationBodyStoreRepository {

    public AnnotationBody createAnnotationBody(int annotationPK, String bodyIri, String sourceIri, String bodyJson);

    public List<AnnotationBody> deletedAnnotationBodies(int annotationPK);
}
