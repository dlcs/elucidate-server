package com.digirati.elucidate.infrastructure.extractor.selector;

import java.util.List;
import java.util.Map;

import com.digirati.elucidate.common.infrastructure.constants.JSONLDConstants;
import com.digirati.elucidate.common.infrastructure.constants.OAConstants;
import com.digirati.elucidate.model.annotation.selector.textposition.AnnotationTextPositionSelector;

public class AnnotationTextPositionSelectorExtractor extends AbstractAnnotationSelectorExtractor<AnnotationTextPositionSelector> {

    @Override
    protected String getSelectorType() {
        return OAConstants.URI_TEXT_POSITION_SELECTOR;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected AnnotationTextPositionSelector buildSelector(Map<String, Object> jsonMap) {

        AnnotationTextPositionSelector annotationTextPositionSelector = new AnnotationTextPositionSelector();

        List<Map<String, Object>> starts = (List<Map<String, Object>>) jsonMap.get(OAConstants.URI_START);
        if (starts != null && starts.size() == 1) {
            Map<String, Object> valueMap = starts.get(0);
            annotationTextPositionSelector.setStart((Integer) valueMap.get(JSONLDConstants.ATTRIBUTE_VALUE));
        }

        List<Map<String, Object>> ends = (List<Map<String, Object>>) jsonMap.get(OAConstants.URI_END);
        if (ends != null && ends.size() == 1) {
            Map<String, Object> valueMap = ends.get(0);
            annotationTextPositionSelector.setEnd((Integer) valueMap.get(JSONLDConstants.ATTRIBUTE_VALUE));
        }

        return annotationTextPositionSelector;
    }
}
