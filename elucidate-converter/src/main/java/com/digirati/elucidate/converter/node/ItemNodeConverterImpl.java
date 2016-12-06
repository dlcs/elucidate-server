package com.digirati.elucidate.converter.node;

import com.digirati.elucidate.common.infrastructure.constants.JSONLDConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ItemNodeConverterImpl implements JSONNodeConverter {

    @Override
    public JsonNode convertJsonNode(JsonNode inputNode) {

        ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
        objectNode.set(JSONLDConstants.ATTRIBUTE_LIST, inputNode);

        ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode();
        arrayNode.add(objectNode);

        return arrayNode;
    }
}
