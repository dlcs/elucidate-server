package com.digirati.elucidate.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.digirati.elucidate.common.infrastructure.constants.ActivityStreamConstants;
import com.digirati.elucidate.common.infrastructure.constants.JSONLDConstants;
import com.digirati.elucidate.common.infrastructure.constants.XMLSchemaConstants;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotationCollection;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotationPage;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationCollection;
import com.digirati.elucidate.infrastructure.generator.IDGenerator;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.ServiceResponse.Status;
import com.digirati.elucidate.model.enumeration.ClientPreference;
import com.digirati.elucidate.repository.AnnotationCollectionStoreRepository;
import com.digirati.elucidate.repository.AnnotationSearchRepository;
import com.digirati.elucidate.repository.AnnotationStoreRepository;
import com.digirati.elucidate.service.AbstractAnnotationCollectionService;
import com.digirati.elucidate.service.AbstractAnnotationPageService;
import com.github.jsonldjava.utils.JsonUtils;

public abstract class AbstractAnnotationCollectionServiceImpl<A extends AbstractAnnotation, P extends AbstractAnnotationPage, C extends AbstractAnnotationCollection> implements AbstractAnnotationCollectionService<A, C> {

    protected final Logger LOGGER = Logger.getLogger(getClass());

    private AbstractAnnotationPageService<A, P, C> annotationPageService;
    private AnnotationStoreRepository annotationStoreRepository;
    private AnnotationCollectionStoreRepository annotationCollectionStoreRepository;
    private AnnotationSearchRepository annotationSearchRepository;
    private IDGenerator idGenerator;
    private int pageSize;

    public AbstractAnnotationCollectionServiceImpl(AbstractAnnotationPageService<A, P, C> annotationPageService, AnnotationStoreRepository annotationStoreRepository, AnnotationCollectionStoreRepository annotationCollectionStoreRepository, AnnotationSearchRepository annotationSearchRepository, IDGenerator idGenerator, int pageSize) {
        this.annotationPageService = annotationPageService;
        this.annotationStoreRepository = annotationStoreRepository;
        this.annotationCollectionStoreRepository = annotationCollectionStoreRepository;
        this.annotationSearchRepository = annotationSearchRepository;
        this.idGenerator = idGenerator;
        this.pageSize = pageSize;
    }

    protected abstract C convertToAnnotationCollection(W3CAnnotationCollection w3cAnnotationCollection);

    protected abstract W3CAnnotationCollection convertFromAnnotationCollection(C annotationCollection);

    protected abstract String buildCollectionIri(String collectionId);

    protected abstract String buildCollectionSearchIri(String targetId);

    protected abstract String buildPageIri(String collectionId, int page, boolean embeddedDescriptions);

    protected abstract String buildPageSearchIri(String targetId, int page, boolean embeddedDescriptions);

    @Override
    @SuppressWarnings("serial")
    @Transactional(readOnly = true)
    public ServiceResponse<C> getAnnotationCollection(String collectionId, ClientPreference clientPref) {

        W3CAnnotationCollection w3cAnnotationCollection = annotationCollectionStoreRepository.getAnnotationCollectionById(collectionId);
        if (w3cAnnotationCollection == null) {
            return new ServiceResponse<C>(Status.NOT_FOUND, null);
        }

        List<W3CAnnotation> w3cAnnotations = annotationStoreRepository.getAnnotationsByCollectionId(collectionId);
        int totalAnnotations = w3cAnnotations.size();
        int totalPages = (int) Math.floor((double) totalAnnotations / pageSize);

        C annotationCollection = convertToAnnotationCollection(w3cAnnotationCollection);
        Map<String, Object> jsonMap = annotationCollection.getJsonMap();

        String iri = buildCollectionIri(collectionId);
        jsonMap.put(JSONLDConstants.ATTRIBUTE_ID, iri);

        Object firstObject;
        Object lastObject;

        if (clientPref.equals(ClientPreference.MINIMAL_CONTAINER)) {

            firstObject = new ArrayList<Map<String, Object>>() {
                {
                    add(new HashMap<String, Object>() {
                        {
                            put(JSONLDConstants.ATTRIBUTE_ID, buildPageIri(collectionId, 0, false));
                        }
                    });
                }
            };

            lastObject = new ArrayList<Map<String, Object>>() {
                {
                    add(new HashMap<String, Object>() {
                        {
                            put(JSONLDConstants.ATTRIBUTE_ID, buildPageIri(collectionId, totalPages, false));
                        }
                    });
                }
            };

        } else {
            ServiceResponse<P> serviceResponse;
            if (clientPref.equals(ClientPreference.CONTAINED_IRIS)) {
                serviceResponse = annotationPageService.getAnnotationPage(collectionId, false, 0);
            } else {
                serviceResponse = annotationPageService.getAnnotationPage(collectionId, true, 0);
            }

            Status status = serviceResponse.getStatus();
            if (!status.equals(Status.OK)) {
                return new ServiceResponse<C>(status, null);
            }

            firstObject = serviceResponse.getObj().getJsonMap();

            lastObject = new ArrayList<Map<String, Object>>() {
                {
                    add(new HashMap<String, Object>() {
                        {
                            put(JSONLDConstants.ATTRIBUTE_ID, buildPageIri(collectionId, totalPages, true));
                        }
                    });
                }
            };
        }

        jsonMap.put(ActivityStreamConstants.URI_FIRST, firstObject);
        jsonMap.put(ActivityStreamConstants.URI_LAST, lastObject);
        jsonMap.put(ActivityStreamConstants.URI_TOTAL_ITEMS, new ArrayList<Map<String, Object>>() {
            {
                add(new HashMap<String, Object>() {
                    {
                        put(JSONLDConstants.ATTRBUTE_TYPE, XMLSchemaConstants.URI_NON_NEGATIVE_INTEGER);
                        put(JSONLDConstants.ATTRIBUTE_VALUE, totalAnnotations);
                    }
                });
            }
        });

        return new ServiceResponse<C>(Status.OK, annotationCollection);
    }

    @Override
    @Transactional(readOnly = false)
    public ServiceResponse<C> createAnnotationCollection(String collectionId, C annotationCollection) {

        if (StringUtils.isBlank(collectionId)) {
            collectionId = idGenerator.generateId();
        }

        if (!validateCollectionId(collectionId)) {
            return new ServiceResponse<C>(Status.NON_CONFORMANT, null);
        }

        ServiceResponse<C> serviceResponse = getAnnotationCollection(collectionId, ClientPreference.MINIMAL_CONTAINER);
        Status status = serviceResponse.getStatus();
        if (status.equals(Status.OK)) {
            return new ServiceResponse<C>(Status.ALREADY_EXISTS, null);
        }

        W3CAnnotationCollection w3cAnnotationCollection = convertFromAnnotationCollection(annotationCollection);

        String w3cAnnotationCollectionJson;
        try {
            Map<String, Object> w3cAnnotationCollectionMap = w3cAnnotationCollection.getJsonMap();
            w3cAnnotationCollectionJson = JsonUtils.toPrettyString(w3cAnnotationCollectionMap);
        } catch (IOException e) {
            LOGGER.debug(String.format("Detected invalid JSON on W3C Annotation Collection [%s]", w3cAnnotationCollection), e);
            return new ServiceResponse<C>(Status.NON_CONFORMANT, null);
        }

        annotationCollectionStoreRepository.createAnnotationCollection(collectionId, w3cAnnotationCollectionJson);
        return getAnnotationCollection(collectionId, ClientPreference.CONTAINED_DESCRIPTIONS);
    }

    @Override
    @SuppressWarnings("serial")
    @Transactional(readOnly = true)
    public ServiceResponse<C> searchAnnotationCollection(String targetId, ClientPreference clientPref) {

        W3CAnnotationCollection w3cAnnotationCollection = new W3CAnnotationCollection();
        w3cAnnotationCollection.setJsonMap(new HashMap<String, Object>());

        List<W3CAnnotation> w3cAnnotations = annotationSearchRepository.getAnnotationsByTargetId(targetId);
        int totalAnnotations = w3cAnnotations.size();
        int totalPages = (int) Math.floor((double) totalAnnotations / pageSize);

        C annotationCollection = convertToAnnotationCollection(w3cAnnotationCollection);
        Map<String, Object> jsonMap = annotationCollection.getJsonMap();
        jsonMap.put(JSONLDConstants.ATTRBUTE_TYPE, new ArrayList<String>() {
            {
                add(ActivityStreamConstants.URI_ORDERED_COLLECTION);
            }
        });

        String iri = buildCollectionSearchIri(targetId);
        jsonMap.put(JSONLDConstants.ATTRIBUTE_ID, iri);

        Object firstObject;
        Object lastObject;

        if (clientPref.equals(ClientPreference.MINIMAL_CONTAINER)) {

            firstObject = new ArrayList<Map<String, Object>>() {
                {
                    add(new HashMap<String, Object>() {
                        {
                            put(JSONLDConstants.ATTRIBUTE_ID, buildPageSearchIri(targetId, 0, false));
                        }
                    });
                }
            };

            lastObject = new ArrayList<Map<String, Object>>() {
                {
                    add(new HashMap<String, Object>() {
                        {
                            put(JSONLDConstants.ATTRIBUTE_ID, buildPageSearchIri(targetId, 0, false));
                        }
                    });
                }
            };

        } else {
            ServiceResponse<P> serviceResponse;
            if (clientPref.equals(ClientPreference.CONTAINED_IRIS)) {
                serviceResponse = annotationPageService.searchAnnotationPage(targetId, false, 0);
            } else {
                serviceResponse = annotationPageService.searchAnnotationPage(targetId, true, 0);
            }

            Status status = serviceResponse.getStatus();
            if (!status.equals(Status.OK)) {
                return new ServiceResponse<C>(status, null);
            }

            firstObject = serviceResponse.getObj().getJsonMap();

            lastObject = new ArrayList<Map<String, Object>>() {
                {
                    add(new HashMap<String, Object>() {
                        {
                            put(JSONLDConstants.ATTRIBUTE_ID, buildPageSearchIri(targetId, totalPages, true));
                        }
                    });
                }
            };
        }

        jsonMap.put(ActivityStreamConstants.URI_FIRST, firstObject);
        jsonMap.put(ActivityStreamConstants.URI_LAST, lastObject);
        jsonMap.put(ActivityStreamConstants.URI_TOTAL_ITEMS, new ArrayList<Map<String, Object>>() {
            {
                add(new HashMap<String, Object>() {
                    {
                        put(JSONLDConstants.ATTRBUTE_TYPE, XMLSchemaConstants.URI_NON_NEGATIVE_INTEGER);
                        put(JSONLDConstants.ATTRIBUTE_VALUE, totalAnnotations);
                    }
                });
            }
        });

        return new ServiceResponse<C>(Status.OK, annotationCollection);
    }

    private boolean validateCollectionId(String collectionId) {
        return StringUtils.isNotBlank(collectionId) && collectionId.length() <= 100;
    }
}
