package com.digirati.elucidate.converter;

import java.util.HashMap;
import java.util.Map;

import com.digirati.elucidate.common.infrastructure.constants.ActivityStreamConstants;
import com.digirati.elucidate.common.infrastructure.constants.DCTermsConstants;
import com.digirati.elucidate.common.infrastructure.constants.OAConstants;
import com.digirati.elucidate.common.infrastructure.constants.XMLSchemaConstants;
import com.digirati.elucidate.converter.node.ItemNodeConverterImpl;
import com.digirati.elucidate.converter.node.JSONNodeConverter;

public class OAToW3CAnnotationConverter extends AbstractConverter {

    @SuppressWarnings("serial")
    private static final Map<String, String> FIELD_MAPPINGS = new HashMap<String, String>() {
        {
            put(OAConstants.URI_ANNOTATED_BY, DCTermsConstants.URI_CREATOR);
            put(OAConstants.URI_ANNOTATED_AT, DCTermsConstants.URI_CREATED);

            put(OAConstants.URI_SERIALIZED_BY, ActivityStreamConstants.URI_GENERATOR);
            put(OAConstants.URI_SERIALIZED_AT, DCTermsConstants.URI_ISSUED);

            put(OAConstants.URI_ITEM, ActivityStreamConstants.URI_ITEMS);
        }
    };

    @SuppressWarnings("serial")
    private static final Map<String, String> TYPE_MAPPINGS = new HashMap<String, String>() {
        {
            put(XMLSchemaConstants.URI_DATE_TIME_STAMP, XMLSchemaConstants.URI_DATE_TIME);
        }
    };

    @SuppressWarnings("serial")
    private static final Map<String, JSONNodeConverter> NODE_CONVERTER_MAPPINGS = new HashMap<String, JSONNodeConverter>() {
        {
            put(OAConstants.URI_ITEM, new ItemNodeConverterImpl());
        }
    };

    public OAToW3CAnnotationConverter() {
        super(FIELD_MAPPINGS, TYPE_MAPPINGS, NODE_CONVERTER_MAPPINGS);
    }
}
