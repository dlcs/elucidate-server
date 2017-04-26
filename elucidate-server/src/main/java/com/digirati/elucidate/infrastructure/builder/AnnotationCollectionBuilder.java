package com.digirati.elucidate.infrastructure.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.digirati.elucidate.common.infrastructure.constants.ActivityStreamConstants;
import com.digirati.elucidate.common.infrastructure.constants.JSONLDConstants;
import com.digirati.elucidate.common.infrastructure.constants.XMLSchemaConstants;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotationCollection;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotationPage;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotationCollection;
import com.digirati.elucidate.infrastructure.builder.function.AnnotationCollectionConverter;
import com.digirati.elucidate.infrastructure.builder.function.AnnotationCollectionIRIBuilder;
import com.digirati.elucidate.infrastructure.builder.function.AnnotationPageIRIBuilder;
import com.digirati.elucidate.infrastructure.builder.function.FirstAnnotationPageBuilder;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.ServiceResponse.Status;
import com.digirati.elucidate.model.enumeration.ClientPreference;

public class AnnotationCollectionBuilder<A extends AbstractAnnotation, P extends AbstractAnnotationPage, C extends AbstractAnnotationCollection> {

    private AnnotationCollectionConverter<C> annotationCollectionConverter;
    private AnnotationCollectionIRIBuilder annotationCollectionIriBuilder;
    private AnnotationPageIRIBuilder annotationPageIriBuilder;
    private FirstAnnotationPageBuilder<P> firstAnnotationPageBuilder;

    public AnnotationCollectionBuilder(AnnotationCollectionConverter<C> annotationCollectionConverter, AnnotationCollectionIRIBuilder annotationCollectionIriBuilder, AnnotationPageIRIBuilder annotationPageIriBuilder, FirstAnnotationPageBuilder<P> firstAnnotationPageBuilder) {
        this.annotationCollectionConverter = annotationCollectionConverter;
        this.annotationCollectionIriBuilder = annotationCollectionIriBuilder;
        this.annotationPageIriBuilder = annotationPageIriBuilder;
        this.firstAnnotationPageBuilder = firstAnnotationPageBuilder;
    }

    @SuppressWarnings("serial")
    public ServiceResponse<C> buildAnnotationCollection(W3CAnnotationCollection w3cAnnotationCollection, List<A> annotations, int pageSize, ClientPreference clientPref) {

        int totalAnnotations = annotations.size();
        int totalPages = (int) Math.floor((double) totalAnnotations / pageSize);

        C annotationCollection = annotationCollectionConverter.convertToAnnotationCollection();
        Map<String, Object> jsonMap = annotationCollection.getJsonMap();
        jsonMap.put(JSONLDConstants.ATTRIBUTE_TYPE, new ArrayList<String>() {
            {
                add(ActivityStreamConstants.URI_ORDERED_COLLECTION);
            }
        });

        String iri = annotationCollectionIriBuilder.buildAnnotationCollectionIri();
        jsonMap.put(JSONLDConstants.ATTRIBUTE_ID, iri);

        Object firstObject;
        Object lastObject;

        if (clientPref.equals(ClientPreference.MINIMAL_CONTAINER)) {

            firstObject = new ArrayList<Map<String, Object>>() {
                {
                    add(new HashMap<String, Object>() {
                        {
                            put(JSONLDConstants.ATTRIBUTE_ID, annotationPageIriBuilder.buildAnnotationPageIri(0, false));
                        }
                    });
                }
            };

            lastObject = new ArrayList<Map<String, Object>>() {
                {
                    add(new HashMap<String, Object>() {
                        {
                            put(JSONLDConstants.ATTRIBUTE_ID, annotationPageIriBuilder.buildAnnotationPageIri(0, false));
                        }
                    });
                }
            };

        } else {

            ServiceResponse<P> serviceResponse = firstAnnotationPageBuilder.buildFirstAnnotationPage();

            Status status = serviceResponse.getStatus();
            if (!status.equals(Status.OK)) {
                return new ServiceResponse<C>(status, null);
            }

            firstObject = serviceResponse.getObj().getJsonMap();

            lastObject = new ArrayList<Map<String, Object>>() {
                {
                    add(new HashMap<String, Object>() {
                        {
                            put(JSONLDConstants.ATTRIBUTE_ID, annotationPageIriBuilder.buildAnnotationPageIri(totalPages, true));
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
                        put(JSONLDConstants.ATTRIBUTE_TYPE, XMLSchemaConstants.URI_NON_NEGATIVE_INTEGER);
                        put(JSONLDConstants.ATTRIBUTE_VALUE, totalAnnotations);
                    }
                });
            }
        });

        return new ServiceResponse<C>(Status.OK, annotationCollection);
    }
}
