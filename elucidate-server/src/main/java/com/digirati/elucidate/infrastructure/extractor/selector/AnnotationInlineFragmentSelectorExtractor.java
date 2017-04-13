package com.digirati.elucidate.infrastructure.extractor.selector;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.digirati.elucidate.common.infrastructure.constants.JSONLDConstants;
import com.digirati.elucidate.common.infrastructure.constants.SelectorConstants;
import com.digirati.elucidate.infrastructure.util.SelectorUtils;
import com.digirati.elucidate.model.annotation.selector.fragment.AnnotationFragmentSelector;
import com.digirati.elucidate.model.annotation.selector.fragment.TFragmentSelector;
import com.digirati.elucidate.model.annotation.selector.fragment.XYWHFragmentSelector;

public class AnnotationInlineFragmentSelectorExtractor {

    @SuppressWarnings("serial")
    public AnnotationFragmentSelector extractAnnotationInlineFragmentSelector(Map<String, Object> jsonMap) {

        String iriStr = (String) jsonMap.get(JSONLDConstants.ATTRIBUTE_ID);
        if (StringUtils.isNotBlank(iriStr)) {

            URI iri = null;
            try {
                iri = new URI(iriStr);
            } catch (URISyntaxException e) {
                return null;
            }

            String fragmentStr = iri.getFragment();
            if (StringUtils.isNotBlank(fragmentStr)) {

                AnnotationFragmentSelector annotationFragmentSelector = new AnnotationFragmentSelector();
                annotationFragmentSelector.setConformsTo(SelectorConstants.MEDIA_FRAGS);
                annotationFragmentSelector.setValue(fragmentStr);

                XYWHFragmentSelector xywhFragmentSelector = SelectorUtils.extractXywhFragmentSelector(fragmentStr);
                if (xywhFragmentSelector != null) {
                    annotationFragmentSelector.setX(xywhFragmentSelector.getX());
                    annotationFragmentSelector.setY(xywhFragmentSelector.getY());
                    annotationFragmentSelector.setW(xywhFragmentSelector.getW());
                    annotationFragmentSelector.setH(xywhFragmentSelector.getH());
                }

                TFragmentSelector tFragmentSelector = SelectorUtils.extractTFragmentSelector(fragmentStr);
                if (tFragmentSelector != null) {
                    annotationFragmentSelector.setStart(tFragmentSelector.getStart());
                    annotationFragmentSelector.setEnd(tFragmentSelector.getEnd());
                }

                annotationFragmentSelector.setJsonMap(new HashMap<String, Object>() {
                    {
                        put(JSONLDConstants.ATTRIBUTE_ID, iriStr);
                    }
                });

                return annotationFragmentSelector;
            }
        }

        return null;
    }
}
