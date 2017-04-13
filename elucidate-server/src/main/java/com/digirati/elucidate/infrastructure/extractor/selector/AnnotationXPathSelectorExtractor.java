package com.digirati.elucidate.infrastructure.extractor.selector;

import java.util.List;
import java.util.Map;

import com.digirati.elucidate.common.infrastructure.constants.JSONLDConstants;
import com.digirati.elucidate.common.infrastructure.constants.OAConstants;
import com.digirati.elucidate.common.infrastructure.constants.RDFConstants;
import com.digirati.elucidate.model.annotation.selector.xpath.AnnotationXPathSelector;

public class AnnotationXPathSelectorExtractor extends AbstractAnnotationSelectorExtractor<AnnotationXPathSelector> {

    @Override
    protected String getSelectorType() {
        return OAConstants.URI_XPATH_SELECTOR;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected AnnotationXPathSelector buildSelector(Map<String, Object> jsonMap) {

        AnnotationXPathSelector annotationXPathSelector = new AnnotationXPathSelector();

        List<Map<String, Object>> values = (List<Map<String, Object>>) jsonMap.get(RDFConstants.URI_VALUE);
        if (values != null && values.size() == 1) {
            Map<String, Object> valueMap = values.get(0);
            annotationXPathSelector.setValue((String) valueMap.get(JSONLDConstants.ATTRIBUTE_VALUE));
        }

        return annotationXPathSelector;
    }
}
