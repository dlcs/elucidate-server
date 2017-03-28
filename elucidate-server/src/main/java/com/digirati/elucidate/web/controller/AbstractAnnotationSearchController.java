package com.digirati.elucidate.web.controller;

import java.util.List;

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
import com.digirati.elucidate.infrastructure.search.function.AnnotationCollectionSearch;
import com.digirati.elucidate.infrastructure.search.function.AnnotationPageSearch;
import com.digirati.elucidate.model.ServiceResponse;
import com.digirati.elucidate.model.ServiceResponse.Status;
import com.digirati.elucidate.model.enumeration.ClientPreference;
import com.digirati.elucidate.service.search.AbstractAnnotationCollectionSearchService;
import com.digirati.elucidate.service.search.AbstractAnnotationPageSearchService;

public abstract class AbstractAnnotationSearchController<A extends AbstractAnnotation, P extends AbstractAnnotationPage, C extends AbstractAnnotationCollection> {

    private static final String REQUEST_PATH_BODY = "/search/body";
    private static final String REQUEST_PATH_TARGET = "/search/target";
    private static final String PREFER_MINIMAL_CONTAINER = "http://www.w3.org/ns/ldp#preferminimalcontainer";
    private static final String PREFER_CONTAINED_IRIS = "http://www.w3.org/ns/oa#prefercontainediris";
    private static final String PREFER_CONTAINED_DESCRIPTIONS = "http://www.w3.org/ns/oa#prefercontaineddescriptions";

    private AbstractAnnotationCollectionSearchService<C> annotationCollectionSearchService;
    private AbstractAnnotationPageSearchService<P> annotationPageSearchService;

    @Autowired
    public AbstractAnnotationSearchController(AbstractAnnotationCollectionSearchService<C> annotationCollectionSearchService, AbstractAnnotationPageSearchService<P> annotationPageSearchService) {
        this.annotationCollectionSearchService = annotationCollectionSearchService;
        this.annotationPageSearchService = annotationPageSearchService;
    }

    @RequestMapping(value = REQUEST_PATH_BODY, method = RequestMethod.GET)
    public ResponseEntity<?> getSearchBody(@RequestParam(value = URLConstants.PARAM_FIELDS, required = true) List<String> fields, @RequestParam(value = URLConstants.PARAM_VALUE, required = true) String value, @RequestParam(value = URLConstants.PARAM_STRICT, required = false, defaultValue = "false") boolean strict, @RequestParam(value = URLConstants.PARAM_PAGE, required = false) Integer page, @RequestParam(value = URLConstants.PARAM_IRIS, required = false, defaultValue = "false") boolean iris, @RequestParam(value = URLConstants.PARAM_DESC, required = false, defaultValue = "false") boolean descriptions, HttpServletRequest request) {
        if (page == null) {
            return processCollectionSearchRequest((ClientPreference clientPref) -> {
                return annotationCollectionSearchService.searchAnnotationCollectionByBody(fields, value, strict, clientPref);
            }, request);
        } else {
            return processPageSearchRequest((boolean embeddedDescriptions) -> {
                return annotationPageSearchService.searchAnnotationPageByBody(fields, value, strict, page, embeddedDescriptions);
            }, iris, descriptions);
        }
    }

    @RequestMapping(value = REQUEST_PATH_TARGET, method = RequestMethod.GET)
    public ResponseEntity<?> getSearchTarget(@RequestParam(value = URLConstants.PARAM_FIELDS, required = true) List<String> fields, @RequestParam(value = URLConstants.PARAM_VALUE, required = true) String value, @RequestParam(value = URLConstants.PARAM_STRICT, required = false, defaultValue = "false") boolean strict, @RequestParam(value = URLConstants.PARAM_XYWH, required = false) String xywh, @RequestParam(value = URLConstants.PARAM_T, required = false) String t, @RequestParam(value = URLConstants.PARAM_PAGE, required = false) Integer page, @RequestParam(value = URLConstants.PARAM_IRIS, required = false, defaultValue = "false") boolean iris, @RequestParam(value = URLConstants.PARAM_DESC, required = false, defaultValue = "false") boolean descriptions, HttpServletRequest request) {
        if (page == null) {
            return processCollectionSearchRequest((ClientPreference clientPref) -> {
                return annotationCollectionSearchService.searchAnnotationCollectionByTarget(fields, value, strict, xywh, t, clientPref);
            }, request);
        } else {
            return processPageSearchRequest((boolean embeddedDescriptions) -> {
                return annotationPageSearchService.searchAnnotationPageByTarget(fields, value, strict, xywh, t, page, embeddedDescriptions);
            }, iris, descriptions);
        }
    }

    private ResponseEntity<?> processCollectionSearchRequest(AnnotationCollectionSearch<C> searchCollection, HttpServletRequest request) {

        ClientPreference clientPref = determineClientPreference(request);
        if (clientPref == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        ServiceResponse<C> serviceResponse = searchCollection.searchAnnotationCollection(clientPref);
        Status status = serviceResponse.getStatus();

        if (status.equals(Status.NOT_FOUND)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(serviceResponse.getObj());
    }

    private ResponseEntity<?> processPageSearchRequest(AnnotationPageSearch<P> searchPage, boolean iris, boolean descs) {

        if (iris && descs) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        ServiceResponse<P> serviceResponse;
        if (!iris) {
            serviceResponse = searchPage.searchAnnotationPage(true);
        } else {
            serviceResponse = searchPage.searchAnnotationPage(false);
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
