package com.digirati.elucidate.common.infrastructure.util;

import org.apache.http.client.utils.URIBuilder;

import java.util.HashMap;
import java.util.Map;

public final class URIUtils {
    @SuppressWarnings("serial")
    private static final Map<String, Integer> DEFAULT_PORTS = new HashMap<String, Integer>() {
        {
            put("http", 80);
            put("https", 443);
        }
    };

    public static String buildBaseUrl(String baseScheme, String baseHost, int basePort, String basePath) {
        URIBuilder builder = new URIBuilder();
        builder.setScheme(baseScheme);
        builder.setHost(baseHost);

        if (!DEFAULT_PORTS.containsKey(baseScheme.toLowerCase()) || DEFAULT_PORTS.get(baseScheme) != basePort) {
            builder.setPort(basePort);
        }

        builder.setPath(basePath);
        return builder.toString();
    }
}
