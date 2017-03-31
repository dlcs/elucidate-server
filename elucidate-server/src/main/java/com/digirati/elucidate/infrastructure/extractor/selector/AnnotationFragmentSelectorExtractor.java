package com.digirati.elucidate.infrastructure.extractor.selector;

import java.util.List;
import java.util.Map;

import com.digirati.elucidate.common.infrastructure.constants.DCTermsConstants;
import com.digirati.elucidate.common.infrastructure.constants.JSONLDConstants;
import com.digirati.elucidate.common.infrastructure.constants.OAConstants;
import com.digirati.elucidate.common.infrastructure.constants.RDFConstants;
import com.digirati.elucidate.infrastructure.util.SelectorUtils;
import com.digirati.elucidate.model.annotation.selector.fragment.AnnotationFragmentSelector;
import com.digirati.elucidate.model.annotation.selector.fragment.TFragmentSelector;
import com.digirati.elucidate.model.annotation.selector.fragment.XYWHFragmentSelector;

public class AnnotationFragmentSelectorExtractor extends AbstractAnnotationSelectorExtractor<AnnotationFragmentSelector> {

    @Override
    protected String getSelectorType() {
        return OAConstants.URI_FRAGMENT_SELECTOR;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected AnnotationFragmentSelector buildSelector(Map<String, Object> jsonMap) {

        AnnotationFragmentSelector annotationFragmentSelector = new AnnotationFragmentSelector();

        List<Map<String, Object>> conformsTos = (List<Map<String, Object>>) jsonMap.get(DCTermsConstants.URI_CONFORMS_TO);
        if (conformsTos != null && conformsTos.size() == 1) {
            Map<String, Object> conformsToMap = conformsTos.get(0);
            annotationFragmentSelector.setConformsTo((String) conformsToMap.get(JSONLDConstants.ATTRIBUTE_ID));
        }

        List<Map<String, Object>> values = (List<Map<String, Object>>) jsonMap.get(RDFConstants.URI_VALUE);
        if (values != null && values.size() == 1) {
            Map<String, Object> valueMap = values.get(0);
            String value = (String) valueMap.get(JSONLDConstants.ATTRIBUTE_VALUE);
            annotationFragmentSelector.setValue(value);

            XYWHFragmentSelector xywhFragmentSelector = SelectorUtils.extractXywhSelector(value);
            if (xywhFragmentSelector != null) {
                annotationFragmentSelector.setX(xywhFragmentSelector.getX());
                annotationFragmentSelector.setY(xywhFragmentSelector.getY());
                annotationFragmentSelector.setW(xywhFragmentSelector.getW());
                annotationFragmentSelector.setH(xywhFragmentSelector.getH());
            }

            TFragmentSelector tFragmentSelector = SelectorUtils.extractTSelector(value);
            if (tFragmentSelector != null) {
                annotationFragmentSelector.setStart(tFragmentSelector.getStart());
                annotationFragmentSelector.setEnd(tFragmentSelector.getEnd());
            }
        }

        return annotationFragmentSelector;
    }
}
