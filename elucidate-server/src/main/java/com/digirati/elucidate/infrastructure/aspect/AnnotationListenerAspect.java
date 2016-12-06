package com.digirati.elucidate.infrastructure.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
import com.digirati.elucidate.common.service.impl.AnnotationPublisherServiceImpl;

@Aspect
@Component(AnnotationListenerAspect.COMPONENT_NAME)
public class AnnotationListenerAspect {

    private static final Logger LOGGER = Logger.getLogger(AnnotationListenerAspect.class);

    public static final String COMPONENT_NAME = "annotationListenerAspect";

    private AnnotationPublisherServiceImpl annotationPublisher;
    private TaskExecutor taskExecutor;

    @Autowired
    public AnnotationListenerAspect(AnnotationPublisherServiceImpl annotationPublisher, @Qualifier("listenerTaskExecutor") TaskExecutor taskExecutor) {
        this.annotationPublisher = annotationPublisher;
        this.taskExecutor = taskExecutor;
    }

    @AfterReturning(pointcut = "execution(* com.digirati.elucidate.repository.AnnotationStoreRepository.createAnnotation(..))", returning = "w3cAnnotation")
    public void afterCreate(W3CAnnotation w3cAnnotation) {
        taskExecutor.execute(() -> {
            LOGGER.info(String.format("Notifying publisher of CREATE on W3CAnnotation [%s]", w3cAnnotation));
            annotationPublisher.create(w3cAnnotation);
        });
    }

    @AfterReturning(pointcut = "execution(* com.digirati.elucidate.repository.AnnotationStoreRepository.updateAnnotation(..))", returning = "w3cAnnotation")
    public void afterUpdate(W3CAnnotation w3cAnnotation) {
        taskExecutor.execute(() -> {
            LOGGER.info(String.format("Notifying publisher of UPDATE on W3CAnnotation [%s]", w3cAnnotation));
            annotationPublisher.update(w3cAnnotation);
        });
    }

    @AfterReturning(pointcut = "execution(* com.digirati.elucidate.repository.AnnotationStoreRepository.deleteAnnotation(..))", returning = "w3cAnnotation")
    public void afterDelete(W3CAnnotation w3cAnnotation) {
        taskExecutor.execute(() -> {
            LOGGER.info(String.format("Notifying publisher of DELETE on W3CAnnotation [%s]", w3cAnnotation));
            annotationPublisher.delete(w3cAnnotation);
        });
    }
}
