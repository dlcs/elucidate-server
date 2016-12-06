package com.digirati.elucidate.converter;

import java.util.HashMap;
import java.util.Map;

import com.digirati.elucidate.common.infrastructure.constants.ActivityStreamConstants;
import com.digirati.elucidate.common.infrastructure.constants.DCTermsConstants;
import com.digirati.elucidate.common.infrastructure.constants.OAConstants;
import com.digirati.elucidate.common.infrastructure.constants.XMLSchemaConstants;
import com.digirati.elucidate.converter.node.ItemsNodeConverterImpl;
import com.digirati.elucidate.converter.node.JSONNodeConverter;

public class W3CToOAAnnotationConverter extends AbstractConverter {

    @SuppressWarnings("serial")
    private static final Map<String, String> FIELD_MAPPINGS = new HashMap<String, String>() {
        {
            put(DCTermsConstants.URI_CREATOR, OAConstants.URI_ANNOTATED_BY);
            put(DCTermsConstants.URI_CREATED, OAConstants.URI_ANNOTATED_AT);

            put(ActivityStreamConstants.URI_GENERATOR, OAConstants.URI_SERIALIZED_BY);
            put(DCTermsConstants.URI_ISSUED, OAConstants.URI_SERIALIZED_AT);

            put(ActivityStreamConstants.URI_ITEMS, OAConstants.URI_ITEM);
        }
    };

    @SuppressWarnings("serial")
    private static final Map<String, String> TYPE_MAPPINGS = new HashMap<String, String>() {
        {
            put(XMLSchemaConstants.URI_DATE_TIME, XMLSchemaConstants.URI_DATE_TIME_STAMP);
        }
    };

    @SuppressWarnings("serial")
    private static final Map<String, JSONNodeConverter> NODE_CONVERTER_MAPPINGS = new HashMap<String, JSONNodeConverter>() {
        {
            put(ActivityStreamConstants.URI_ITEMS, new ItemsNodeConverterImpl());
        }
    };

    public W3CToOAAnnotationConverter() {
        super(FIELD_MAPPINGS, TYPE_MAPPINGS, NODE_CONVERTER_MAPPINGS);
    }
}
