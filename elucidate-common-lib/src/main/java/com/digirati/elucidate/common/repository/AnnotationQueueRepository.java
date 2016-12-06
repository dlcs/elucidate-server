package com.digirati.elucidate.common.repository;

import java.util.Map;

import com.digirati.elucidate.common.model.enumeration.QueueOperation;

public interface AnnotationQueueRepository {

    public void sendMessage(QueueOperation queueOperation, String iri, Map<String, Object> w3cAnnotationMap, Map<String, Object> oaAnnotationMap);
}
