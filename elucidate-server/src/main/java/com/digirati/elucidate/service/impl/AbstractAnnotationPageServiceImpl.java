package com.digirati.elucidate.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.digirati.elucidate.common.infrastructure.constants.ActivityStreamConstants;
import com.digirati.elucidate.common.infrastructure.constants.JSONLDConstants;
import com.digirati.elucidate.common.infrastructure.constants.XMLSchemaConstants;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotationCollection;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotationPage;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.ServiceResponse.Status;
import com.digirati.elucidate.model.enumeration.SearchType;
import com.digirati.elucidate.service.AbstractAnnotationPageService;
import com.digirati.elucidate.service.AbstractAnnotationService;

public abstract class AbstractAnnotationPageServiceImpl<A extends AbstractAnnotation, P extends AbstractAnnotationPage, C extends AbstractAnnotationCollection> implements AbstractAnnotationPageService<A, P, C> {

    private AbstractAnnotationService<A, C> annotationService;
    private int pageSize;

    public AbstractAnnotationPageServiceImpl(AbstractAnnotationService<A, C> annotationService, int pageSize) {
        this.annotationService = annotationService;
        this.pageSize = pageSize;
    }

    protected abstract P convertToAnnotationPage(Map<String, Object> jsonMap);

    protected abstract String buildCollectionIri(SearchType searchType, String searchQuery);;

    protected abstract String buildPageIri(SearchType searchType, String searchQuery, int page, boolean embeddedDescriptions);

    @Override
    @Transactional(readOnly = true)
    public ServiceResponse<P> getAnnotationPage(String collectionId, boolean embeddedDescriptions, int page) {

        ServiceResponse<List<A>> serviceResponse = annotationService.getAnnotations(collectionId);
        Status status = serviceResponse.getStatus();

        if (!status.equals(Status.OK)) {
            return new ServiceResponse<P>(status, null);
        }

        List<A> annotations = serviceResponse.getObj();
        return buildAnnotationPage(SearchType.COLLECTION_ID, collectionId, annotations, embeddedDescriptions, page);
    }

    @Override
    @Transactional(readOnly = true)
    public ServiceResponse<P> searchAnnotationPage(String targetIri, boolean embeddedDescriptions, int page) {

        ServiceResponse<List<A>> serviceResponse = annotationService.searchAnnotations(targetIri);
        Status status = serviceResponse.getStatus();

        if (!status.equals(Status.OK)) {
            return new ServiceResponse<P>(status, null);
        }

        List<A> annotations = serviceResponse.getObj();
        return buildAnnotationPage(SearchType.TARGET_IRI, targetIri, annotations, embeddedDescriptions, page);
    }

    @SuppressWarnings("serial")
    private ServiceResponse<P> buildAnnotationPage(SearchType searchType, String searchQuery, List<A> annotations, boolean embeddedDescriptions, int page) {

        int totalPages = (int) Math.floor((double) annotations.size() / pageSize);
        int from = Math.min(annotations.size(), Math.max(0, page * pageSize));
        int to = Math.min(annotations.size(), (page + 1) * pageSize);
        annotations = annotations.subList(from, to);

        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put(JSONLDConstants.ATTRBUTE_TYPE, new ArrayList<String>() {
            {
                add(ActivityStreamConstants.URI_ORDERED_COLLECTION_PAGE);
            }
        });

        String partOfIri = buildCollectionIri(searchType, searchQuery);
        jsonMap.put(ActivityStreamConstants.URI_PART_OF, new ArrayList<Map<String, Object>>() {
            {
                add(new HashMap<String, Object>() {
                    {
                        put(JSONLDConstants.ATTRIBUTE_ID, partOfIri);

                    }
                });
            }
        });

        jsonMap.put(ActivityStreamConstants.URI_START_INDEX, new ArrayList<Map<String, Object>>() {
            {
                add(new HashMap<String, Object>() {
                    {
                        put(JSONLDConstants.ATTRBUTE_TYPE, XMLSchemaConstants.URI_NON_NEGATIVE_INTEGER);
                        put(JSONLDConstants.ATTRIBUTE_VALUE, from);
                    }
                });
            }
        });

        if (page > 0) {
            String prevIri = buildPageIri(searchType, searchQuery, page - 1, embeddedDescriptions);
            jsonMap.put(ActivityStreamConstants.URI_PREV, new ArrayList<Map<String, Object>>() {
                {
                    add(new HashMap<String, Object>() {
                        {
                            put(JSONLDConstants.ATTRIBUTE_ID, prevIri);
                        }
                    });
                }
            });
        }

        if (page < totalPages) {
            String nextIri = buildPageIri(searchType, searchQuery, page + 1, embeddedDescriptions);
            jsonMap.put(ActivityStreamConstants.URI_NEXT, new ArrayList<Map<String, Object>>() {
                {
                    add(new HashMap<String, Object>() {
                        {
                            put(JSONLDConstants.ATTRIBUTE_ID, nextIri);
                        }
                    });
                }
            });
        }

        if (embeddedDescriptions) {
            List<Map<String, Object>> annotationDescriptions = convertToDescriptions(annotations);
            jsonMap.put(ActivityStreamConstants.URI_ITEMS, new ArrayList<Map<String, Object>>() {
                {
                    add(new HashMap<String, Object>() {
                        {
                            put(JSONLDConstants.ATTRIBUTE_LIST, new ArrayList<Map<String, Object>>() {
                                {
                                    addAll(annotationDescriptions);
                                }
                            });
                        }
                    });

                }
            });

        } else {
            List<Map<String, Object>> annotationIris = convertToIris(annotations);
            jsonMap.put(ActivityStreamConstants.URI_ITEMS, new ArrayList<Map<String, Object>>() {
                {
                    add(new HashMap<String, Object>() {
                        {
                            put(JSONLDConstants.ATTRIBUTE_LIST, new ArrayList<Map<String, Object>>() {
                                {
                                    addAll(annotationIris);
                                }
                            });
                        }
                    });

                }
            });
        }

        P annotationPage = convertToAnnotationPage(jsonMap);
        return new ServiceResponse<P>(Status.OK, annotationPage);
    }

    private List<Map<String, Object>> convertToDescriptions(List<A> annotations) {
        List<Map<String, Object>> descriptions = new ArrayList<Map<String, Object>>();
        for (A annotation : annotations) {
            descriptions.add(annotation.getJsonMap());
        }
        return descriptions;
    }

    @SuppressWarnings("serial")
    private List<Map<String, Object>> convertToIris(List<A> annotations) {
        List<Map<String, Object>> iris = new ArrayList<Map<String, Object>>();
        for (A annotation : annotations) {
            iris.add(new HashMap<String, Object>() {
                {
                    put(JSONLDConstants.ATTRIBUTE_ID, annotation.getJsonMap().get(JSONLDConstants.ATTRIBUTE_ID));
                }
            });
        }
        return iris;
    }
}
