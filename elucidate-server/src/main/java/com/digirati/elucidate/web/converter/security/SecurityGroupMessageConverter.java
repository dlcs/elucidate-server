package com.digirati.elucidate.web.converter.security;

import com.digirati.elucidate.model.security.SecurityGroup;
import com.digirati.elucidate.web.converter.AbstractMessageConverter;
import com.github.jsonldjava.utils.JsonUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SecurityGroupMessageConverter extends AbstractMessageConverter<SecurityGroup> {

    public SecurityGroupMessageConverter() {
        super(MediaType.APPLICATION_JSON);
    }

    @Override
    protected void decorateHeaders(SecurityGroup obj, HttpOutputMessage outputMessage) {
        HttpHeaders headers = outputMessage.getHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.ALLOW, "GET,OPTIONS,HEAD");
    }

    @Override
    protected String getStringRepresentation(SecurityGroup obj, MediaType contentType) throws Exception {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("id", obj.getId());
        jsonMap.put("label", obj.getLabel());

        return JsonUtils.toPrettyString(jsonMap);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected SecurityGroup getObjectRepresentation(String str, MediaType contentType) throws Exception {
        Map<String, ?> jsonMap = (Map<String, ?>) JsonUtils.fromString(str);
        SecurityGroup group = new SecurityGroup();
        group.setId((String) jsonMap.get("id"));
        group.setLabel((String) jsonMap.get("label"));

        return group;
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return clazz == SecurityGroup.class;
    }
}
