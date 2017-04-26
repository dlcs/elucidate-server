package com.digirati.elucidate.model;

import java.util.List;
import java.util.Map;

public class JSONLDProfile {

    public enum Format {
        COMPACTED, EXPANDED, FLATTENED
    }

    private Map<String, List<String>> contexts;
    private List<Format> formats;

    public Map<String, List<String>> getContexts() {
        return contexts;
    }

    public void setContexts(Map<String, List<String>> contexts) {
        this.contexts = contexts;
    }

    public List<Format> getFormats() {
        return formats;
    }

    public void setFormats(List<Format> formats) {
        this.formats = formats;
    }
}
