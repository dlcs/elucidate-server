package com.digirati.elucidate.infrastructure.extractor.agent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.digirati.elucidate.common.infrastructure.constants.DCTermsConstants;
import com.digirati.elucidate.common.infrastructure.constants.FOAFConstants;
import com.digirati.elucidate.common.infrastructure.constants.JSONLDConstants;
import com.digirati.elucidate.model.annotation.agent.AnnotationAgent;

public class AnnotationCreatorExtractor {

    @SuppressWarnings("unchecked")
    public List<AnnotationAgent> extractCreators(Map<String, Object> jsonMap) {

        List<AnnotationAgent> annotationAgents = new ArrayList<AnnotationAgent>();

        List<Map<String, Object>> creatorJsonMaps = (List<Map<String, Object>>) jsonMap.get(DCTermsConstants.URI_CREATOR);
        if (creatorJsonMaps != null && !creatorJsonMaps.isEmpty()) {

            for (Map<String, Object> creatorJsonMap : creatorJsonMaps) {

                AnnotationAgent annotationAgent = new AnnotationAgent();
                annotationAgent.setJsonMap(creatorJsonMap);

                String agentIri = (String) creatorJsonMap.get(JSONLDConstants.ATTRIBUTE_ID);
                annotationAgent.setAgentIri(agentIri);

                List<String> types = (List<String>) creatorJsonMap.get(JSONLDConstants.ATTRIBUTE_TYPE);
                annotationAgent.setTypes(types);
                annotationAgent.setTypesJsonList(types);

                List<Map<String, Object>> nicknameJsonMaps = (List<Map<String, Object>>) creatorJsonMap.get(FOAFConstants.URI_NICK);
                if (nicknameJsonMaps != null && nicknameJsonMaps.size() == 1) {

                    String nickname = (String) nicknameJsonMaps.get(0).get(JSONLDConstants.ATTRIBUTE_VALUE);
                    annotationAgent.setNickname(nickname);
                }

                List<Map<String, Object>> nameJsonMaps = (List<Map<String, Object>>) creatorJsonMap.get(FOAFConstants.URI_NAME);
                if (nameJsonMaps != null && !nameJsonMaps.isEmpty()) {

                    List<String> names = new ArrayList<String>();
                    List<Map<String, Object>> namesJsonMaps = new ArrayList<Map<String, Object>>();

                    for (Map<String, Object> nameJsonMap : nameJsonMaps) {

                        String name = (String) nameJsonMap.get(JSONLDConstants.ATTRIBUTE_VALUE);
                        names.add(name);
                        namesJsonMaps.add(nameJsonMap);
                    }

                    annotationAgent.setNames(names);
                    annotationAgent.setNameJsonMaps(namesJsonMaps);
                }

                List<Map<String, Object>> emailJsonMaps = (List<Map<String, Object>>) creatorJsonMap.get(FOAFConstants.URI_MBOX);
                if (emailJsonMaps != null && !emailJsonMaps.isEmpty()) {

                    List<String> emails = new ArrayList<String>();
                    List<Map<String, Object>> emailsJsonMaps = new ArrayList<Map<String, Object>>();

                    for (Map<String, Object> emailJsonMap : emailJsonMaps) {

                        String email = (String) emailJsonMap.get(JSONLDConstants.ATTRIBUTE_VALUE);
                        emails.add(email);
                        emailsJsonMaps.add(emailJsonMap);
                    }

                    annotationAgent.setEmails(emails);
                    annotationAgent.setEmailJsonMaps(emailsJsonMaps);
                }

                List<Map<String, Object>> emailSha1JsonMaps = (List<Map<String, Object>>) creatorJsonMap.get(FOAFConstants.UIRI_MBOX_SHA1SUM);
                if (emailSha1JsonMaps != null && !emailSha1JsonMaps.isEmpty()) {

                    List<String> emailSha1s = new ArrayList<String>();
                    List<Map<String, Object>> emailSha1sJsonMaps = new ArrayList<Map<String, Object>>();

                    for (Map<String, Object> emailSha1JsonMap : emailSha1JsonMaps) {

                        String emailSha1 = (String) emailSha1JsonMap.get(JSONLDConstants.ATTRIBUTE_VALUE);
                        emailSha1s.add(emailSha1);
                        emailSha1sJsonMaps.add(emailSha1JsonMap);
                    }

                    annotationAgent.setEmailSha1s(emailSha1s);
                    annotationAgent.setEmailSha1JsonMaps(emailSha1sJsonMaps);
                }

                List<Map<String, Object>> homepageJsonMaps = (List<Map<String, Object>>) creatorJsonMap.get(FOAFConstants.URI_HOMEPAGE);
                if (homepageJsonMaps != null && !homepageJsonMaps.isEmpty()) {

                    List<String> homepages = new ArrayList<String>();
                    List<Map<String, Object>> homepagesJsonMaps = new ArrayList<Map<String, Object>>();

                    for (Map<String, Object> homepageJsonMap : homepageJsonMaps) {

                        String homepage = (String) homepageJsonMap.get(JSONLDConstants.ATTRIBUTE_ID);
                        homepages.add(homepage);
                        homepagesJsonMaps.add(homepageJsonMap);
                    }

                    annotationAgent.setHomepages(homepages);
                    annotationAgent.setHomepageJsonMaps(homepagesJsonMaps);
                }

                annotationAgents.add(annotationAgent);
            }
        }

        return annotationAgents;
    }
}
