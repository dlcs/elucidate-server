package com.digirati.elucidate.infrastructure.extractor.selector;

import java.util.List;
import java.util.Map;

import com.digirati.elucidate.common.infrastructure.constants.JSONLDConstants;
import com.digirati.elucidate.common.infrastructure.constants.OAConstants;
import com.digirati.elucidate.model.annotation.selector.dataposition.AnnotationDataPositionSelector;

public class AnnotationDataPositionSelectorExtractor extends AbstractAnnotationSelectorExtractor<AnnotationDataPositionSelector> {

    @Override
    protected String getSelectorType() {
        return OAConstants.URI_DATA_POSITION_SELECTOR;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected AnnotationDataPositionSelector buildSelector(Map<String, Object> jsonMap) {

        AnnotationDataPositionSelector annotationDataPositionSelector = new AnnotationDataPositionSelector();

        List<Map<String, Object>> starts = (List<Map<String, Object>>) jsonMap.get(OAConstants.URI_START);
        if (starts != null && starts.size() == 1) {
            Map<String, Object> valueMap = starts.get(0);
            annotationDataPositionSelector.setStart((Integer) valueMap.get(JSONLDConstants.ATTRIBUTE_VALUE));
        }

        List<Map<String, Object>> ends = (List<Map<String, Object>>) jsonMap.get(OAConstants.URI_END);
        if (ends != null && ends.size() == 1) {
            Map<String, Object> valueMap = ends.get(0);
            annotationDataPositionSelector.setEnd((Integer) valueMap.get(JSONLDConstants.ATTRIBUTE_VALUE));
        }

        return annotationDataPositionSelector;
    }
}
