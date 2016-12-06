package com.digirati.elucidate.converter.node;

import com.digirati.elucidate.common.infrastructure.constants.JSONLDConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class ItemsNodeConverterImpl implements JSONNodeConverter {

    @Override
    public JsonNode convertJsonNode(JsonNode inputNode) {
        return ((ArrayNode) inputNode).get(0).get(JSONLDConstants.ATTRIBUTE_LIST);
    }
}
