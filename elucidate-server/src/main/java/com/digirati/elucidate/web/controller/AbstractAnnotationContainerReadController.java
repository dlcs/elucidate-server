package com.digirati.elucidate.web.controller;

import javax.servlet.http.HttpServletRequest;

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
import com.digirati.elucidate.service.AbstractAnnotationCollectionService;
import com.digirati.elucidate.service.AbstractAnnotationPageService;

public abstract class AbstractAnnotationContainerReadController<A extends AbstractAnnotation, P extends AbstractAnnotationPage, C extends AbstractAnnotationCollection> {

    private static final String VARIABLE_COLLECTION_ID = "collectionId";
    private static final String REQUEST_PATH = "/{" + VARIABLE_COLLECTION_ID + "}/";

    private AbstractAnnotationCollectionService<A, C> annotationCollectionService;
    private AbstractAnnotationPageService<A, P, C> annotationPageService;

    @Autowired
    public AbstractAnnotationContainerReadController(AbstractAnnotationCollectionService<A, C> annotationCollectionService, AbstractAnnotationPageService<A, P, C> annotationPageService) {
        this.annotationCollectionService = annotationCollectionService;
        this.annotationPageService = annotationPageService;
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

        ServiceResponse<C> serviceResponse = annotationCollectionService.getAnnotationCollection(collectionId, clientPref);
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

        ServiceResponse<P> serviceResponse;
        if (!iris) {
            serviceResponse = annotationPageService.getAnnotationPage(collectionId, true, page);
        } else {
            serviceResponse = annotationPageService.getAnnotationPage(collectionId, false, page);
        }
        Status status = serviceResponse.getStatus();

        if (status.equals(Status.NOT_FOUND)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(serviceResponse.getObj());
    }

    private ClientPreference determineClientPreference(HttpServletRequest request) {
        String preferHeader = request.getHeader("Prefer");
        if (StringUtils.isBlank(preferHeader) || StringUtils.equalsIgnoreCase(preferHeader, "return=representation;include=\"http://www.w3.org/ns/oa#PreferContainedDescriptions\"")) {
            return ClientPreference.CONTAINED_DESCRIPTIONS;
        } else if (StringUtils.equalsIgnoreCase(preferHeader, "return=representation;include=\"http://www.w3.org/ns/ldp#PreferMinimalContainer\"")) {
            return ClientPreference.MINIMAL_CONTAINER;
        } else if (StringUtils.equalsIgnoreCase(preferHeader, "return=representation;include=\"http://www.w3.org/ns/oa#PreferContainedIRIs\"")) {
            return ClientPreference.CONTAINED_IRIS;
        }
        return null;
    }
}
