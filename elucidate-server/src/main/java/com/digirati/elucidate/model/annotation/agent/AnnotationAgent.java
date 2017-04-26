package com.digirati.elucidate.model.annotation.agent;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.digirati.elucidate.common.model.annotation.AbstractObject;

@SuppressWarnings("serial")
public class AnnotationAgent extends AbstractObject implements Serializable {

    private String agentIri;
    private List<String> types;
    private List<String> typesJsonList;
    private List<String> names;
    private List<Map<String, Object>> nameJsonMaps;
    private String nickname;
    private List<String> emails;
    private List<Map<String, Object>> emailJsonMaps;
    private List<String> emailSha1s;
    private List<Map<String, Object>> emailSha1JsonMaps;
    private List<String> homepages;
    private List<Map<String, Object>> homepageJsonMaps;

    public String getAgentIri() {
        return agentIri;
    }

    public void setAgentIri(String agentIri) {
        this.agentIri = agentIri;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public List<String> getTypesJsonList() {
        return typesJsonList;
    }

    public void setTypesJsonList(List<String> typesJsonList) {
        this.typesJsonList = typesJsonList;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public List<Map<String, Object>> getNameJsonMaps() {
        return nameJsonMaps;
    }

    public void setNameJsonMaps(List<Map<String, Object>> nameJsonMaps) {
        this.nameJsonMaps = nameJsonMaps;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public List<Map<String, Object>> getEmailJsonMaps() {
        return emailJsonMaps;
    }

    public void setEmailJsonMaps(List<Map<String, Object>> emailJsonMaps) {
        this.emailJsonMaps = emailJsonMaps;
    }

    public List<String> getEmailSha1s() {
        return emailSha1s;
    }

    public void setEmailSha1s(List<String> emailSha1s) {
        this.emailSha1s = emailSha1s;
    }

    public List<Map<String, Object>> getEmailSha1JsonMaps() {
        return emailSha1JsonMaps;
    }

    public void setEmailSha1JsonMaps(List<Map<String, Object>> emailSha1JsonMaps) {
        this.emailSha1JsonMaps = emailSha1JsonMaps;
    }

    public List<String> getHomepages() {
        return homepages;
    }

    public void setHomepages(List<String> homepages) {
        this.homepages = homepages;
    }

    public List<Map<String, Object>> getHomepageJsonMaps() {
        return homepageJsonMaps;
    }

    public void setHomepageJsonMaps(List<Map<String, Object>> homepageJsonMaps) {
        this.homepageJsonMaps = homepageJsonMaps;
    }
}
