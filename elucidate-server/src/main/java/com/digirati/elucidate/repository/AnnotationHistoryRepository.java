package com.digirati.elucidate.repository;

import com.digirati.elucidate.model.annotation.history.AnnotationHistory;
import org.springframework.transaction.annotation.Transactional;

public interface AnnotationHistoryRepository {

    public AnnotationHistory createAnnotationHistory(Integer annotationPK, String annotationJsonStr);
}