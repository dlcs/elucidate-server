package com.digirati.elucidate.infrastructure.extractor.temportal;

import com.digirati.elucidate.common.infrastructure.constants.DCTermsConstants;
import com.digirati.elucidate.common.infrastructure.constants.JSONLDConstants;
import com.digirati.elucidate.common.infrastructure.constants.XMLSchemaConstants;
import com.digirati.elucidate.model.annotation.temporal.AnnotationTemporal;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.format.ISODateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AnnotationTemporalExtractor {

    private static final Logger LOGGER = Logger.getLogger(AnnotationTemporalExtractor.class);

    public List<AnnotationTemporal> extractTemporals(Map<String, Object> jsonMap) {

        List<AnnotationTemporal> annotationTemporals = new ArrayList<AnnotationTemporal>();

        List<Map<String, Object>> createdJsonMaps = (List<Map<String, Object>>) jsonMap.get(DCTermsConstants.URI_CREATED);
        if (createdJsonMaps != null && createdJsonMaps.size() == 1) {
            AnnotationTemporal annotationTemporal = createAnnotationTemporal("CREATED", createdJsonMaps.get(0));
            if (annotationTemporal != null) {
                annotationTemporals.add(annotationTemporal);
            }
        }

        List<Map<String, Object>> modifiedJsonMaps = (List<Map<String, Object>>) jsonMap.get(DCTermsConstants.URI_MODIFIED);
        if (modifiedJsonMaps != null && modifiedJsonMaps.size() == 1) {
            AnnotationTemporal annotationTemporal = createAnnotationTemporal("MODIFIED", modifiedJsonMaps.get(0));
            if (annotationTemporal != null) {
                annotationTemporals.add(annotationTemporal);
            }
        }

        List<Map<String, Object>> generatedJsonMaps = (List<Map<String, Object>>) jsonMap.get(DCTermsConstants.URI_ISSUED);
        if (generatedJsonMaps != null && generatedJsonMaps.size() == 1) {
            AnnotationTemporal annotationTemporal = createAnnotationTemporal("GENERATED", generatedJsonMaps.get(0));
            if (annotationTemporal != null) {
                annotationTemporals.add(annotationTemporal);
            }
        }

        return annotationTemporals;
    }

    private AnnotationTemporal createAnnotationTemporal(String type, Map<String, Object> jsonMap) {

        Date value = extractDate(jsonMap);
        if (value == null) {
            return null;
        }

        AnnotationTemporal annotationTemporal = new AnnotationTemporal();
        annotationTemporal.setType(type);
        annotationTemporal.setValue(value);
        annotationTemporal.setJsonMap(jsonMap);
        return annotationTemporal;
    }

    private Date extractDate(Map<String, Object> jsonMap) {

        String type = (String) jsonMap.get(JSONLDConstants.ATTRIBUTE_TYPE);
        if (!StringUtils.equalsIgnoreCase(type, XMLSchemaConstants.URI_DATE_TIME)) {
            LOGGER.warn(String.format("Unable to extract date from JSON map [%s], invalid '%s': [%s]", jsonMap, JSONLDConstants.ATTRIBUTE_TYPE, type));
            return null;
        }

        String value = (String) jsonMap.get(JSONLDConstants.ATTRIBUTE_VALUE);
        if (StringUtils.isBlank(value)) {
            LOGGER.warn(String.format("Got blank '%s' from JSON map [%s]", JSONLDConstants.ATTRIBUTE_VALUE, jsonMap));
            return null;
        }

        return ISODateTimeFormat.dateTime().parseDateTime(value).toDate();
    }
}
