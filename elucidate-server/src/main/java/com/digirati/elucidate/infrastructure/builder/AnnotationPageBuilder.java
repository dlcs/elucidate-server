package com.digirati.elucidate.infrastructure.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.digirati.elucidate.common.infrastructure.constants.ActivityStreamConstants;
import com.digirati.elucidate.common.infrastructure.constants.JSONLDConstants;
import com.digirati.elucidate.common.infrastructure.constants.XMLSchemaConstants;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotationPage;
import com.digirati.elucidate.infrastructure.builder.function.AnnotationCollectionIRIBuilder;
import com.digirati.elucidate.infrastructure.builder.function.AnnotationPageConverter;
import com.digirati.elucidate.infrastructure.builder.function.AnnotationPageIRIBuilder;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.ServiceResponse.Status;

public class AnnotationPageBuilder<A extends AbstractAnnotation, P extends AbstractAnnotationPage> {

    private AnnotationPageConverter<P> annotationPageConverter;
    private AnnotationCollectionIRIBuilder annotationCollectionIriBuilder;
    private AnnotationPageIRIBuilder annotationPageIriBuilder;

    public AnnotationPageBuilder(AnnotationPageConverter<P> annotationPageConverter, AnnotationCollectionIRIBuilder annotationCollectionIriBuilder, AnnotationPageIRIBuilder annotationPageIriBuilder) {
        this.annotationPageConverter = annotationPageConverter;
        this.annotationCollectionIriBuilder = annotationCollectionIriBuilder;
        this.annotationPageIriBuilder = annotationPageIriBuilder;
    }

    @SuppressWarnings("serial")
    public ServiceResponse<P> buildAnnotationPage(List<A> annotations, int page, boolean embeddedDescriptions, int pageSize) {

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

        String partOfIri = annotationCollectionIriBuilder.buildAnnotationCollectionIri();
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
            String prevIri = annotationPageIriBuilder.buildAnnotationPageIri(page - 1, embeddedDescriptions);
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
            String nextIri = annotationPageIriBuilder.buildAnnotationPageIri(page + 1, embeddedDescriptions);
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

        P annotationPage = annotationPageConverter.convertToAnnotationPage(jsonMap);
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
