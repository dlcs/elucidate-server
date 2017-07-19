package com.digirati.elucidate.web.converter.oa.history;

import com.digirati.elucidate.common.service.IRIBuilderService;
import com.digirati.elucidate.model.JSONLDProfile;
import com.digirati.elucidate.model.annotation.history.OAAnnotationHistory;
import com.digirati.elucidate.service.history.OAAnnotationHistoryService;
import com.github.jsonldjava.core.JsonLdProcessor;
import com.github.jsonldjava.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeNotSupportedException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class JSONLDOAAnnotationHistoryMessageConverter extends AbstractOAAnnotationHistoryMessageConverter {

    private String[] defaultContexts;

    @Autowired
    public JSONLDOAAnnotationHistoryMessageConverter(IRIBuilderService iriBuilderService, OAAnnotationHistoryService oaAnnotationHistoryService, @Value("${annotation.history.oa.contexts}") String[] defaultContexts) throws IOException {
        super(iriBuilderService, oaAnnotationHistoryService, APPLICATION_JSON_LD);
        this.defaultContexts = Arrays.copyOf(defaultContexts, defaultContexts.length);
    }

    @Override
    protected boolean canRead(MediaType mediaType) {
        if (mediaType == null) {
            return true;
        }
        for (MediaType supportedMediaType : getSupportedMediaTypes()) {
            if (supportedMediaType.isCompatibleWith(mediaType)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean canWrite(MediaType mediaType) {
        if (mediaType == null || MediaType.ALL.equals(mediaType)) {
            return true;
        }
        for (MediaType supportedMediaType : getSupportedMediaTypes()) {
            if (supportedMediaType.isCompatibleWith(mediaType)) {
                return true;
            }
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected String getStringRepresentation(OAAnnotationHistory oaAnnotationHistory, MediaType contentType) throws Exception {
        Map<String, Object> jsonMap = oaAnnotationHistory.getJsonMap();

        JSONLDProfile jsonLdProfile = getJsonLdProfile(contentType, defaultContexts);

        JSONLDProfile.Format format = jsonLdProfile.getFormats().get(0);
        if (format.equals(JSONLDProfile.Format.COMPACTED)) {
            jsonMap = JsonLdProcessor.compact(jsonMap, jsonLdProfile.getContexts(), jsonLdOptions);
        } else if (format.equals(JSONLDProfile.Format.EXPANDED)) {
            List<Object> jsonList = JsonLdProcessor.expand(jsonMap, jsonLdOptions);
            jsonMap = (Map<String, Object>) jsonList.get(0);
        } else if (format.equals(JSONLDProfile.Format.FLATTENED)) {
            jsonMap = (Map<String, Object>) JsonLdProcessor.flatten(jsonMap, jsonLdProfile.getContexts(), jsonLdOptions);
        } else {
            throw new HttpMediaTypeNotSupportedException(contentType, getSupportedMediaTypes());
        }

        jsonMap = reorderJsonAttributes(jsonMap);
        return JsonUtils.toPrettyString(jsonMap);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected OAAnnotationHistory getObjectRepresentation(String jsonStr, MediaType contentType) throws Exception {
        Map<String, Object> jsonMap = (Map<String, Object>) JsonUtils.fromString(jsonStr);
        List<Object> jsonList = JsonLdProcessor.expand(jsonMap, jsonLdOptions);
        jsonMap = (Map<String, Object>) jsonList.get(0);

        OAAnnotationHistory oaAnnotationHistory = new OAAnnotationHistory();
        oaAnnotationHistory.setJsonMap(jsonMap);
        return oaAnnotationHistory;
    }
}
