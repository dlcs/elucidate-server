package com.digirati.elucidate.web.converter.security;

import com.digirati.elucidate.model.security.SecurityUserReference;
import com.digirati.elucidate.service.security.SecurityUserReferenceCollection;
import com.digirati.elucidate.web.converter.AbstractMessageConverter;
import com.github.jsonldjava.utils.JsonUtils;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class SecurityUserReferenceCollectionConverter extends AbstractMessageConverter<SecurityUserReferenceCollection> {

    public SecurityUserReferenceCollectionConverter() {
        super(MediaType.APPLICATION_JSON);
    }

    @Override
    protected void decorateHeaders(SecurityUserReferenceCollection obj, HttpOutputMessage outputMessage) {
        HttpHeaders headers = outputMessage.getHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.ALLOW, "GET,OPTIONS,HEAD");
    }

    @Override
    protected String getStringRepresentation(SecurityUserReferenceCollection obj, MediaType contentType) throws Exception {
        Map<String, Object> jsonMap = new HashMap<>();
        List<Map<String, String>> userJsonMaps = obj.getUsers().stream()
            .map(user -> ImmutableMap.of(
                "uid", user.getUid(),
                "id", user.getId()
            ))
            .collect(Collectors.toList());

        jsonMap.put("users", userJsonMaps);
        return JsonUtils.toString(jsonMap);
    }

    @Override
    protected SecurityUserReferenceCollection getObjectRepresentation(String str, MediaType contentType) throws Exception {
        throw new UnsupportedOperationException("Unable to demarshall a SecurityUserReferenceCollection object.");
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(SecurityUserReferenceCollection.class);
    }
}
