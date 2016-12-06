package com.digirati.elucidate.converter.node;

import com.fasterxml.jackson.databind.JsonNode;

public interface JSONNodeConverter {

    public JsonNode convertJsonNode(JsonNode inputNode);
}
