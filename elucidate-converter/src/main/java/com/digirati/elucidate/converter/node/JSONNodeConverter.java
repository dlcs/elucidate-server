package com.digirati.elucidate.converter.node;

import com.fasterxml.jackson.databind.JsonNode;

public interface JSONNodeConverter {

    JsonNode convertJsonNode(JsonNode inputNode);
}
