package com.digirati.elucidate.common.service.impl;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.digirati.elucidate.common.infrastructure.constants.URLConstants;
import com.digirati.elucidate.common.infrastructure.exception.InvalidIRIException;
import com.digirati.elucidate.common.service.IRIBuilderService;

@Service(IRIBuilderServiceImpl.SERVICE_NAME)
public class IRIBuilderServiceImpl implements IRIBuilderService {

    public static final String SERVICE_NAME = "iriBuilderServiceImpl";

    @SuppressWarnings("serial")
    private static final Map<String, Integer> DEFAULT_PORTS = new HashMap<String, Integer>() {
        {
            put("http", 80);
            put("https", 443);
        }
    };

    private String baseUrl;

    @Autowired
    public IRIBuilderServiceImpl(@Value("${base.scheme}") String baseScheme, @Value("${base.host}") String baseHost, @Value("${base.port}") int basePort, @Value("${base.path}") String basePath) {
        this.baseUrl = buildBaseUrl(baseScheme, baseHost, basePort, basePath);
    }

    private String buildBaseUrl(String baseScheme, String baseHost, int basePort, String basePath) {
        URIBuilder builder = new URIBuilder();
        builder.setScheme(baseScheme);
        builder.setHost(baseHost);

        if (!DEFAULT_PORTS.containsKey(baseScheme.toLowerCase()) || DEFAULT_PORTS.get(baseScheme) != basePort) {
            builder.setPort(basePort);
        }

        builder.setPath(basePath);
        return builder.toString();
    }

    @Override
    public String buildW3CAnnotationIri(String collectionId, String annotationId) {
        return buildIri(String.format("w3c/%s/%s", collectionId, annotationId), null);
    }

    @Override
    public String buildW3CCollectionIri(String collectionId) {
        collectionId = String.format("w3c/%s/", collectionId);
        return buildIri(collectionId, null);
    }

    @Override
    @SuppressWarnings("serial")
    public String buildW3CPageIri(String collectionId, int page, boolean embeddedDescriptions) {
        collectionId = String.format("w3c/%s/", collectionId);
        return buildIri(collectionId, new HashMap<String, Object>() {
            {
                put(URLConstants.PARAM_PAGE, page);
                if (embeddedDescriptions) {
                    put(URLConstants.PARAM_DESC, 1);
                } else {
                    put(URLConstants.PARAM_IRIS, 1);
                }
            }
        });
    }

    @Override
    public String buildOAAnnotationIri(String collectionId, String annotationId) {
        return buildIri(String.format("oa/%s/%s", collectionId, annotationId), null);
    }

    @Override
    public String buildOACollectionIri(String collectionId) {
        collectionId = String.format("oa/%s/", collectionId);
        return buildIri(collectionId, null);
    }

    @Override
    @SuppressWarnings("serial")
    public String buildOAPageIri(String collectionId, int page, boolean embeddedDescriptions) {
        collectionId = String.format("oa/%s/", collectionId);
        return buildIri(collectionId, new HashMap<String, Object>() {
            {
                put(URLConstants.PARAM_PAGE, page);
                if (embeddedDescriptions) {
                    put(URLConstants.PARAM_DESC, 1);
                } else {
                    put(URLConstants.PARAM_IRIS, 1);
                }
            }
        });
    }

    private String buildIri(String id, Map<String, Object> params) {
        try {
            URIBuilder builder = new URIBuilder(baseUrl);
            builder.setPath(String.format("%s/%s", builder.getPath(), id));
            if (params != null && !params.isEmpty()) {
                for (Entry<String, Object> param : params.entrySet()) {
                    builder.addParameter(param.getKey(), String.valueOf(param.getValue()));
                }
            }
            return builder.toString();
        } catch (URISyntaxException e) {
            throw new InvalidIRIException(String.format("An error occurred building IRI with base URL [%s] with ID [%s] and parameters [%s]", baseUrl, id, params), e);
        }
    }
}
