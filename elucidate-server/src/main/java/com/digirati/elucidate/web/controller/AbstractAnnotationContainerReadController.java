package com.digirati.elucidate.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.digirati.elucidate.service.query.AbstractAnnotationCollectionService;
import com.digirati.elucidate.service.query.AbstractAnnotationPageService;
import com.digirati.elucidate.service.query.AbstractAnnotationService;

public abstract class AbstractAnnotationContainerReadController<A extends AbstractAnnotation, P extends AbstractAnnotationPage, C extends AbstractAnnotationCollection> {

    private static final String VARIABLE_COLLECTION_ID = "collectionId";
    private static final String REQUEST_PATH = "/{" + VARIABLE_COLLECTION_ID + "}/";
    private static final String PREFER_MINIMAL_CONTAINER = "http://www.w3.org/ns/ldp#preferminimalcontainer";
    private static final String PREFER_CONTAINED_IRIS = "http://www.w3.org/ns/oa#prefercontainediris";
    private static final String PREFER_CONTAINED_DESCRIPTIONS = "http://www.w3.org/ns/oa#prefercontaineddescriptions";

    private AbstractAnnotationService<A> annotationService;
    private AbstractAnnotationPageService<A, P> annotationPageService;
    private AbstractAnnotationCollectionService<A, C> annotationCollectionService;

    @Autowired
    public AbstractAnnotationContainerReadController(AbstractAnnotationService<A> annotationService, AbstractAnnotationPageService<A, P> annotationPageService, AbstractAnnotationCollectionService<A, C> annotationCollectionService) {
        this.annotationService = annotationService;
        this.annotationPageService = annotationPageService;
        this.annotationCollectionService = annotationCollectionService;
    }

    @RequestMapping(value = REQUEST_PATH, method = {RequestMethod.GET, RequestMethod.HEAD})
    public ResponseEntity<?> get(@PathVariable(VARIABLE_COLLECTION_ID) String collectionId, @RequestParam(value = URLConstants.PARAM_PAGE, required = false) Integer page, @RequestParam(value = URLConstants.PARAM_IRIS, required = false, defaultValue = "false") boolean iris, @RequestParam(value = URLConstants.PARAM_DESC, required = false, defaultValue = "false") boolean descriptions, HttpServletRequest request) {
        if (page == null) {
            return processCollectionRequest(collectionId, request);
        } else {
            return processPageRequest(collectionId, page, iris, descriptions);
        }
    }

    private ResponseEntity<?> processCollectionRequest(String collectionId, HttpServletRequest request) {

        ClientPreference clientPref = determineClientPreference(request);
        if (clientPref == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        ServiceResponse<List<A>> annotationsServiceResponse = annotationService.getAnnotations(collectionId);

        List<A> annotations = annotationsServiceResponse.getObj();

        ServiceResponse<C> serviceResponse = annotationCollectionService.getAnnotationCollection(collectionId, annotations, clientPref);
        Status status = serviceResponse.getStatus();

        if (status.equals(Status.NOT_FOUND)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(serviceResponse.getObj());
    }

    private ResponseEntity<?> processPageRequest(String collectionId, int page, boolean iris, boolean descs) {

        if (iris && descs) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        ServiceResponse<List<A>> annotationsServiceResponse = annotationService.getAnnotations(collectionId);
        Status status = annotationsServiceResponse.getStatus();

        if (!status.equals(Status.OK)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        List<A> annotations = annotationsServiceResponse.getObj();

        ServiceResponse<P> annotationPageServiceResponse;
        if (!iris) {
            annotationPageServiceResponse = annotationPageService.buildAnnotationPage(annotations, collectionId, page, true);
        } else {
            annotationPageServiceResponse = annotationPageService.buildAnnotationPage(annotations, collectionId, page, false);
        }
        status = annotationPageServiceResponse.getStatus();

        if (status.equals(Status.NOT_FOUND)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(annotationPageServiceResponse.getObj());
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
