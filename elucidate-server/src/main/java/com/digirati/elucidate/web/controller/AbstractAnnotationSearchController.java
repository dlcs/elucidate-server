package com.digirati.elucidate.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.digirati.elucidate.common.infrastructure.constants.URLConstants;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotation;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotationCollection;
import com.digirati.elucidate.common.model.annotation.AbstractAnnotationPage;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.ServiceResponse.Status;
import com.digirati.elucidate.model.enumeration.ClientPreference;
import com.digirati.elucidate.service.AbstractAnnotationCollectionService;
import com.digirati.elucidate.service.AbstractAnnotationPageService;

public abstract class AbstractAnnotationSearchController<A extends AbstractAnnotation, P extends AbstractAnnotationPage, C extends AbstractAnnotationCollection> {

    private static final String REQUEST_PATH = "/search";
    private static final String PREFER_MINIMAL_CONTAINER = "http://www.w3.org/ns/ldp#preferminimalcontainer";
    private static final String PREFER_CONTAINED_IRIS = "http://www.w3.org/ns/oa#prefercontainediris";
    private static final String PREFER_CONTAINED_DESCRIPTIONS = "http://www.w3.org/ns/oa#prefercontaineddescriptions";

    private AbstractAnnotationCollectionService<A, C> annotationCollectionService;
    private AbstractAnnotationPageService<A, P, C> annotationPageService;

    @Autowired
    public AbstractAnnotationSearchController(AbstractAnnotationCollectionService<A, C> annotationCollectionService, AbstractAnnotationPageService<A, P, C> annotationPageService) {
        this.annotationCollectionService = annotationCollectionService;
        this.annotationPageService = annotationPageService;
    }

    @RequestMapping(value = REQUEST_PATH, method = RequestMethod.GET)
    public ResponseEntity<?> get(@RequestParam(value = URLConstants.PARAM_TARGET, required = true) String targetId, @RequestParam(value = URLConstants.PARAM_PAGE, required = false) Integer page, @RequestParam(value = URLConstants.PARAM_IRIS, required = false, defaultValue = "false") boolean iris, @RequestParam(value = URLConstants.PARAM_DESC, required = false, defaultValue = "false") boolean descriptions, HttpServletRequest request) {
        if (page == null) {
            return processCollectionRequest(targetId, request);
        } else {
            return processPageRequest(targetId, page, iris, descriptions);
        }
    }

    private ResponseEntity<?> processCollectionRequest(String targetId, HttpServletRequest request) {

        ClientPreference clientPref = determineClientPreference(request);
        if (clientPref == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        ServiceResponse<C> serviceResponse = annotationCollectionService.searchAnnotationCollection(targetId, clientPref);
        Status status = serviceResponse.getStatus();

        if (status.equals(Status.NOT_FOUND)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(serviceResponse.getObj());
    }

    private ResponseEntity<?> processPageRequest(String targetId, int page, boolean iris, boolean descs) {

        if (iris && descs) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        ServiceResponse<P> serviceResponse;
        if (!iris) {
            serviceResponse = annotationPageService.searchAnnotationPage(targetId, true, page);
        } else {
            serviceResponse = annotationPageService.searchAnnotationPage(targetId, false, page);
        }
        Status status = serviceResponse.getStatus();

        if (status.equals(Status.NOT_FOUND)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(serviceResponse.getObj());
    }

    private ClientPreference determineClientPreference(HttpServletRequest request) {

        String preferHeader = request.getHeader("Prefer");

        preferHeader = StringUtils.lowerCase(preferHeader);
        if (!StringUtils.startsWith(preferHeader, "return=representation")) {
            return ClientPreference.CONTAINED_DESCRIPTIONS;
        }
        preferHeader = StringUtils.stripStart(preferHeader, "return=representation;");
        preferHeader = StringUtils.strip(preferHeader);
        preferHeader = StringUtils.stripStart(preferHeader, "include=");
        preferHeader = StringUtils.strip(preferHeader, "\"");

        String[] preferences = StringUtils.split(preferHeader);
        if (preferences.length == 0) {
            return ClientPreference.CONTAINED_DESCRIPTIONS;
        }

        if (ArrayUtils.contains(preferences, PREFER_CONTAINED_IRIS) && ArrayUtils.contains(preferences, PREFER_CONTAINED_DESCRIPTIONS)) {
            return null;
        }

        for (String preference : preferences) {
            if (StringUtils.equals(preference, PREFER_CONTAINED_DESCRIPTIONS)) {
                return ClientPreference.CONTAINED_DESCRIPTIONS;
            } else if (StringUtils.equals(preference, PREFER_MINIMAL_CONTAINER)) {
                return ClientPreference.MINIMAL_CONTAINER;
            } else if (StringUtils.equals(preference, PREFER_CONTAINED_IRIS)) {
                return ClientPreference.CONTAINED_IRIS;
            }
        }

        return null;
    }
}