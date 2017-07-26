package com.digirati.elucidate.infrastructure.extractor.selector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.digirati.elucidate.common.infrastructure.constants.JSONLDConstants;
import com.digirati.elucidate.common.infrastructure.constants.OAConstants;
import com.digirati.elucidate.model.annotation.selector.AbstractAnnotationSelector;

public abstract class AbstractAnnotationSelectorExtractor<T extends AbstractAnnotationSelector> {

    protected final Logger LOGGER = Logger.getLogger(getClass());

    protected abstract String getSelectorType();

    protected abstract T buildSelector(Map<String, Object> jsonMap);

    @SuppressWarnings("unchecked")
    public List<T> extractSelectors(Map<String, Object> jsonMap) {

        List<T> selectors = new ArrayList<T>();

        List<Map<String, Object>> jsonSelectors = (List<Map<String, Object>>) jsonMap.get(OAConstants.URI_HAS_SELECTOR);
        if (jsonSelectors != null && !jsonSelectors.isEmpty()) {
            for (Map<String, Object> jsonSelector : jsonSelectors) {

                List<String> types = (List<String>) jsonSelector.get(JSONLDConstants.ATTRIBUTE_TYPE);
                if (types == null || types.size() != 1) {
                    return null;
                }

                if (StringUtils.equalsIgnoreCase(types.get(0), getSelectorType())) {

                    T selector = buildSelector(jsonSelector);
                    selector.setJsonMap(jsonSelector);

                    String id = (String) jsonSelector.get(JSONLDConstants.ATTRIBUTE_ID);
                    if (StringUtils.isNotBlank(id)) {
                        selector.setSelectorIri(id);
                    }

                    selectors.add(selector);
                }
            }
        }

        return selectors;
    }
}
