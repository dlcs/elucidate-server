package com.digirati.elucidate.common.service.impl;

import com.digirati.elucidate.common.infrastructure.constants.URLConstants;
import com.digirati.elucidate.common.infrastructure.exception.InvalidIRIException;
import com.digirati.elucidate.common.service.IRIBuilderService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
    @SuppressWarnings("serial")
    public String buildW3CCollectionBodySearchIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, String generatorIri) {
        return buildIri("w3c/services/search/body", new HashMap<String, Object>() {
            {
                put(URLConstants.PARAM_FIELDS, StringUtils.join(fields, ","));
                put(URLConstants.PARAM_VALUE, value);
                if (strict) {
                    put(URLConstants.PARAM_STRICT, strict);
                }
                if (StringUtils.isNotBlank(xywh)) {
                    put(URLConstants.PARAM_XYWH, xywh);
                }
                if (StringUtils.isNotBlank(t)) {
                    put(URLConstants.PARAM_T, t);
                }
                if (StringUtils.isNotBlank(creatorIri)) {
                    put(URLConstants.PARAM_CREATOR, creatorIri);
                }
                if (StringUtils.isNotBlank(generatorIri)) {
                    put(URLConstants.PARAM_GENERATOR, generatorIri);
                }
            }
        });
    }

    @Override
    @SuppressWarnings("serial")
    public String buildW3CPageBodySearchIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, String generatorIri, int page, boolean embeddedDescriptions) {
        return buildIri("w3c/services/search/body", new HashMap<String, Object>() {
            {
                put(URLConstants.PARAM_FIELDS, StringUtils.join(fields, ","));
                put(URLConstants.PARAM_VALUE, value);
                if (strict) {
                    put(URLConstants.PARAM_STRICT, strict);
                }
                if (StringUtils.isNotBlank(xywh)) {
                    put(URLConstants.PARAM_XYWH, xywh);
                }
                if (StringUtils.isNotBlank(t)) {
                    put(URLConstants.PARAM_T, t);
                }
                if (StringUtils.isNotBlank(creatorIri)) {
                    put(URLConstants.PARAM_CREATOR, creatorIri);
                }
                if (StringUtils.isNotBlank(generatorIri)) {
                    put(URLConstants.PARAM_GENERATOR, generatorIri);
                }
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
    @SuppressWarnings("serial")
    public String buildW3CCollectionTargetSearchIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, String generatorIri) {
        return buildIri("w3c/services/search/target", new HashMap<String, Object>() {
            {
                put(URLConstants.PARAM_FIELDS, StringUtils.join(fields, ","));
                put(URLConstants.PARAM_VALUE, value);
                if (strict) {
                    put(URLConstants.PARAM_STRICT, strict);
                }
                if (StringUtils.isNotBlank(xywh)) {
                    put(URLConstants.PARAM_XYWH, xywh);
                }
                if (StringUtils.isNotBlank(t)) {
                    put(URLConstants.PARAM_T, t);
                }
                if (StringUtils.isNotBlank(creatorIri)) {
                    put(URLConstants.PARAM_CREATOR, creatorIri);
                }
                if (StringUtils.isNotBlank(generatorIri)) {
                    put(URLConstants.PARAM_GENERATOR, generatorIri);
                }
            }
        });
    }

    @Override
    @SuppressWarnings("serial")
    public String buildW3CPageTargetSearchIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, String generatorIri, int page, boolean embeddedDescriptions) {
        return buildIri("w3c/services/search/target", new HashMap<String, Object>() {
            {
                put(URLConstants.PARAM_FIELDS, StringUtils.join(fields, ","));
                put(URLConstants.PARAM_VALUE, value);
                if (strict) {
                    put(URLConstants.PARAM_STRICT, strict);
                }
                if (StringUtils.isNotBlank(xywh)) {
                    put(URLConstants.PARAM_XYWH, xywh);
                }
                if (StringUtils.isNotBlank(t)) {
                    put(URLConstants.PARAM_T, t);
                }
                if (StringUtils.isNotBlank(creatorIri)) {
                    put(URLConstants.PARAM_CREATOR, creatorIri);
                }
                if (StringUtils.isNotBlank(generatorIri)) {
                    put(URLConstants.PARAM_GENERATOR, generatorIri);
                }
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
    @SuppressWarnings("serial")
    public String buildW3CCollectionCreatorSearchIri(List<String> levels, String type, String value, boolean strict) {
        return buildIri("w3c/services/search/creator", new HashMap<String, Object>() {
            {
                put(URLConstants.PARAM_LEVELS, StringUtils.join(levels, ","));
                put(URLConstants.PARAM_TYPE, type);
                put(URLConstants.PARAM_VALUE, value);
                if (strict) {
                    put(URLConstants.PARAM_STRICT, strict);
                }
            }
        });
    }

    @Override
    @SuppressWarnings("serial")
    public String buildW3CPageCreatorSearchIri(List<String> levels, String type, String value, boolean strict, int page, boolean embeddedDescriptions) {
        return buildIri("w3c/services/search/creator", new HashMap<String, Object>() {
            {
                put(URLConstants.PARAM_LEVELS, StringUtils.join(levels, ","));
                put(URLConstants.PARAM_TYPE, type);
                put(URLConstants.PARAM_VALUE, value);
                if (strict) {
                    put(URLConstants.PARAM_STRICT, strict);
                }
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
    public String buildW3CCollectionGeneratorSearchIri(List<String> levels, String type, String value, boolean strict) {
        return buildIri("w3c/services/search/generator", new HashMap<String, Object>() {
            {
                put(URLConstants.PARAM_LEVELS, StringUtils.join(levels, ","));
                put(URLConstants.PARAM_TYPE, type);
                put(URLConstants.PARAM_VALUE, value);
                if (strict) {
                    put(URLConstants.PARAM_STRICT, strict);
                }
            }
        });
    }

    @Override
    public String buildW3CPageGeneratorSearchIri(List<String> levels, String type, String value, boolean strict, int page, boolean embeddedDescriptions) {
        return buildIri("w3c/services/search/generator", new HashMap<String, Object>() {
            {
                put(URLConstants.PARAM_LEVELS, StringUtils.join(levels, ","));
                put(URLConstants.PARAM_TYPE, type);
                put(URLConstants.PARAM_VALUE, value);
                if (strict) {
                    put(URLConstants.PARAM_STRICT, strict);
                }
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
    @SuppressWarnings("serial")
    public String buildW3CStatisticsPageBodyIri(String field, int page) {
        return buildIri("w3c/services/stats/body", new HashMap<String, Object>() {
            {
                put(URLConstants.PARAM_FIELD, field);
                put(URLConstants.PARAM_PAGE, page);
            }
        });
    }

    @Override
    public String buildW3CAnnotationHistoryIri(String collectionId, String annotationId, int version) {
        return buildIri(String.format("w3c/services/history/%s/%s/%s", collectionId, annotationId, version), null);
    }

    @Override
    public String buildW3CPageTemporalSearchIri(List<String> levels, List<String> types, Date since, int page, boolean embeddedDescriptions) {
        return buildIri("w3c/services/search/temporal", new HashMap<String, Object>() {
            {
                put(URLConstants.PARAM_LEVELS, StringUtils.join(levels, ","));
                put(URLConstants.PARAM_TYPES, StringUtils.join(types, ","));
                put(URLConstants.PARAM_SINCE, since);
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

    @Override
    @SuppressWarnings("serial")
    public String buildOACollectionBodySearchIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, String generatorIri) {
        return buildIri("oa/services/search/body", new HashMap<String, Object>() {
            {
                put(URLConstants.PARAM_FIELDS, StringUtils.join(fields, ","));
                put(URLConstants.PARAM_VALUE, value);
                if (strict) {
                    put(URLConstants.PARAM_STRICT, strict);
                }
                if (StringUtils.isNotBlank(xywh)) {
                    put(URLConstants.PARAM_XYWH, xywh);
                }
                if (StringUtils.isNotBlank(t)) {
                    put(URLConstants.PARAM_T, t);
                }
                if (StringUtils.isNotBlank(creatorIri)) {
                    put(URLConstants.PARAM_CREATOR, creatorIri);
                }
                if (StringUtils.isNotBlank(generatorIri)) {
                    put(URLConstants.PARAM_GENERATOR, generatorIri);
                }
            }
        });
    }

    @Override
    @SuppressWarnings("serial")
    public String buildOAPageBodySearchIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, String generatorIri, int page, boolean embeddedDescriptions) {
        return buildIri("oa/services/search/body", new HashMap<String, Object>() {
            {
                put(URLConstants.PARAM_FIELDS, StringUtils.join(fields, ","));
                put(URLConstants.PARAM_VALUE, value);
                if (strict) {
                    put(URLConstants.PARAM_STRICT, strict);
                }
                if (StringUtils.isNotBlank(xywh)) {
                    put(URLConstants.PARAM_XYWH, xywh);
                }
                if (StringUtils.isNotBlank(t)) {
                    put(URLConstants.PARAM_T, t);
                }
                if (StringUtils.isNotBlank(creatorIri)) {
                    put(URLConstants.PARAM_CREATOR, creatorIri);
                }
                if (StringUtils.isNotBlank(generatorIri)) {
                    put(URLConstants.PARAM_GENERATOR, generatorIri);
                }
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
    @SuppressWarnings("serial")
    public String buildOACollectionTargetSearchIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, String generatorIri) {
        return buildIri("oa/services/search/target", new HashMap<String, Object>() {
            {
                put(URLConstants.PARAM_FIELDS, StringUtils.join(fields, ","));
                put(URLConstants.PARAM_VALUE, value);
                if (strict) {
                    put(URLConstants.PARAM_STRICT, strict);
                }
                if (StringUtils.isNotBlank(xywh)) {
                    put(URLConstants.PARAM_XYWH, xywh);
                }
                if (StringUtils.isNotBlank(t)) {
                    put(URLConstants.PARAM_T, t);
                }
                if (StringUtils.isNotBlank(creatorIri)) {
                    put(URLConstants.PARAM_CREATOR, creatorIri);
                }
                if (StringUtils.isNotBlank(generatorIri)) {
                    put(URLConstants.PARAM_GENERATOR, generatorIri);
                }
            }
        });
    }

    @Override
    @SuppressWarnings("serial")
    public String buildOAPageTargetSearchIri(List<String> fields, String value, boolean strict, String xywh, String t, String creatorIri, String generatorIri, int page, boolean embeddedDescriptions) {
        return buildIri("oa/services/search/target", new HashMap<String, Object>() {
            {
                put(URLConstants.PARAM_FIELDS, StringUtils.join(fields, ","));
                put(URLConstants.PARAM_VALUE, value);
                if (strict) {
                    put(URLConstants.PARAM_STRICT, strict);
                }
                if (StringUtils.isNotBlank(xywh)) {
                    put(URLConstants.PARAM_XYWH, xywh);
                }
                if (StringUtils.isNotBlank(t)) {
                    put(URLConstants.PARAM_T, t);
                }
                if (StringUtils.isNotBlank(creatorIri)) {
                    put(URLConstants.PARAM_CREATOR, creatorIri);
                }
                if (StringUtils.isNotBlank(generatorIri)) {
                    put(URLConstants.PARAM_GENERATOR, generatorIri);
                }
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
    @SuppressWarnings("serial")
    public String buildOACollectionCreatorSearchIri(List<String> levels, String type, String value, boolean strict) {
        return buildIri("oa/services/search/creator", new HashMap<String, Object>() {
            {
                put(URLConstants.PARAM_LEVELS, StringUtils.join(levels, ","));
                put(URLConstants.PARAM_TYPE, type);
                put(URLConstants.PARAM_VALUE, value);
                if (strict) {
                    put(URLConstants.PARAM_STRICT, strict);
                }
            }
        });
    }

    @Override
    @SuppressWarnings("serial")
    public String buildOAPageCreatorSearchIri(List<String> levels, String type, String value, boolean strict, int page, boolean embeddedDescriptions) {
        return buildIri("oa/services/search/creator", new HashMap<String, Object>() {
            {
                put(URLConstants.PARAM_LEVELS, StringUtils.join(levels, ","));
                put(URLConstants.PARAM_TYPE, type);
                put(URLConstants.PARAM_VALUE, value);
                if (strict) {
                    put(URLConstants.PARAM_STRICT, strict);
                }
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
    public String buildOACollectionGeneratorSearchIri(List<String> levels, String type, String value, boolean strict) {
        return buildIri("oa/services/search/generator", new HashMap<String, Object>() {
            {
                put(URLConstants.PARAM_LEVELS, StringUtils.join(levels, ","));
                put(URLConstants.PARAM_TYPE, type);
                put(URLConstants.PARAM_VALUE, value);
                if (strict) {
                    put(URLConstants.PARAM_STRICT, strict);
                }
            }
        });
    }

    @Override
    public String buildOAPageGeneratorSearchIri(List<String> levels, String type, String value, boolean strict, int page, boolean embeddedDescriptions) {
        return buildIri("oa/services/search/generator", new HashMap<String, Object>() {
            {
                put(URLConstants.PARAM_LEVELS, StringUtils.join(levels, ","));
                put(URLConstants.PARAM_TYPE, type);
                put(URLConstants.PARAM_VALUE, value);
                if (strict) {
                    put(URLConstants.PARAM_STRICT, strict);
                }
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
    @SuppressWarnings("serial")
    public String buildOAStatisticsPageBodyIri(String field, int page) {
        return buildIri("oa/services/stats/body", new HashMap<String, Object>() {
            {
                put(URLConstants.PARAM_FIELD, field);
                put(URLConstants.PARAM_PAGE, page);
            }
        });
    }

    @Override
    public String buildOAAnnotationHistoryIri(String collectionId, String annotationId, int version) {
        return buildIri(String.format("oa/services/history/%s/%s/%s", collectionId, annotationId, version), null);
    }

    @Override
    public String buildOACollectionTemporalSearchIri(List<String> levels, List<String> types, Date since) {
        return buildIri("oa/services/search/temporal", new HashMap<String, Object>() {
            {
                put(URLConstants.PARAM_LEVELS, StringUtils.join(levels, ","));
                put(URLConstants.PARAM_TYPES, StringUtils.join(types, ","));
                put(URLConstants.PARAM_SINCE, since);
            }
        });
    }

    @Override
    public String buildOAPageTemporalSearchIri(List<String> levels, List<String> types, Date since, int page, boolean embeddedDescriptions) {
        return buildIri("oa/services/search/temporal", new HashMap<String, Object>() {
            {
                put(URLConstants.PARAM_LEVELS, StringUtils.join(levels, ","));
                put(URLConstants.PARAM_TYPES, StringUtils.join(types, ","));
                put(URLConstants.PARAM_SINCE, since);
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
    public String buildW3CCollectionTemporalSearchIri(List<String> levels, List<String> types, Date since) {
        return buildIri("w3c/services/search/temporal", new HashMap<String, Object>() {
            {
                put(URLConstants.PARAM_LEVELS, StringUtils.join(levels, ","));
                put(URLConstants.PARAM_TYPES, StringUtils.join(types, ","));
                put(URLConstants.PARAM_SINCE, since);
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
