package com.digirati.elucidate.infrastructure.extractor.targets;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.digirati.elucidate.common.infrastructure.constants.JSONLDConstants;
import com.digirati.elucidate.common.infrastructure.constants.OAConstants;
import com.digirati.elucidate.model.annotation.targets.AnnotationTarget;

public class AnnotationTargetExtractor {

    @SuppressWarnings("unchecked")
    public List<AnnotationTarget> extractTargets(Map<String, Object> jsonMap) {

        List<AnnotationTarget> annotationTargets = new ArrayList<AnnotationTarget>();

        List<Map<String, Object>> targetJsonMaps = (List<Map<String, Object>>) jsonMap.get(OAConstants.URI_HAS_TARGET);
        if (targetJsonMaps != null && !targetJsonMaps.isEmpty()) {

            for (Map<String, Object> targetJsonMap : targetJsonMaps) {

                AnnotationTarget annotationTarget = new AnnotationTarget();
                annotationTarget.setJsonMap(targetJsonMap);

                String targetIri = (String) targetJsonMap.get(JSONLDConstants.ATTRIBUTE_ID);
                annotationTarget.setTargetIri(stripFragment(targetIri));

                List<Map<String, Object>> sources = (List<Map<String, Object>>) targetJsonMap.get(OAConstants.URI_HAS_SOURCE);
                if (sources != null && sources.size() == 1) {

                    String sourceIri = (String) sources.get(0).get(JSONLDConstants.ATTRIBUTE_ID);
                    annotationTarget.setSourceIri(sourceIri);
                }

                annotationTargets.add(annotationTarget);
            }
        }

        return annotationTargets;
    }

    private String stripFragment(String targetIriStr) {
        if (StringUtils.isNotBlank(targetIriStr)) {
            try {
                URI targetIri = new URI(targetIriStr);
                return new URI(targetIri.getScheme(), targetIri.getUserInfo(), targetIri.getHost(), targetIri.getPort(), targetIri.getPath(), targetIri.getQuery(), null).toString();
            } catch (URISyntaxException e) {
                return targetIriStr;
            }
        }

        return null;
    }
}
