package com.digirati.elucidate.infrastructure.aspect;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.digirati.elucidate.common.infrastructure.listener.RegisteredListener;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;

@Aspect
@Order(Ordered.LOWEST_PRECEDENCE - 1)
@Component(AnnotationListenerAspect.COMPONENT_NAME)
public class AnnotationListenerAspect {

    private static final Logger LOGGER = Logger.getLogger(AnnotationListenerAspect.class);

    public static final String COMPONENT_NAME = "annotationListenerAspect";

    private ApplicationContext applicationContext;
    private TaskExecutor taskExecutor;
    private List<RegisteredListener> registeredListeners;

    @Autowired
    public AnnotationListenerAspect(ApplicationContext applicationContext, @Qualifier("listenerTaskExecutor") TaskExecutor taskExecutor, @Value("#{'${listeners.registered}'.split(',')}") List<String> configuredListeners) throws ClassNotFoundException {
        this.applicationContext = applicationContext;
        this.taskExecutor = taskExecutor;
        this.registeredListeners = new ArrayList<RegisteredListener>();
        initialiseConfiguredListeners(configuredListeners);
    }

    @SuppressWarnings("unchecked")
    private void initialiseConfiguredListeners(List<String> configuredListeners) throws ClassNotFoundException {

        if (configuredListeners != null && !configuredListeners.isEmpty()) {
            for (String configuredListener : configuredListeners) {
                if (StringUtils.isNotBlank(configuredListener)) {

                    Class<? extends RegisteredListener> clazz = (Class<? extends RegisteredListener>) Class.forName(configuredListener);
                    RegisteredListener registeredListener = applicationContext.getAutowireCapableBeanFactory().createBean(clazz);
                    registerListener(registeredListener);
                }
            }
        }

    }

    public void registerListener(RegisteredListener registeredListener) {
        if (!registeredListeners.contains(registeredListener)) {
            LOGGER.info(String.format("Registering listener [%s]", registeredListener));
            registeredListeners.add(registeredListener);
        }
    }

    public void deregisterListener(RegisteredListener registeredListener) {
        if (registeredListeners.contains(registeredListener)) {
            LOGGER.info(String.format("Unregistering listener [%s]", registeredListener));
            registeredListeners.remove(registeredListener);
        }
    }

    @AfterReturning(pointcut = "execution(* com.digirati.elucidate.repository.AnnotationStoreRepository.createAnnotation(..))", returning = "w3cAnnotation")
    public void afterCreate(W3CAnnotation w3cAnnotation) {
        for (RegisteredListener registeredListener : registeredListeners) {
            taskExecutor.execute(() -> {
                LOGGER.info(String.format("Notifying listener [%s] of CREATE on W3CAnnotation [%s]", registeredListener, w3cAnnotation));
                registeredListener.notifyCreate(w3cAnnotation);
            });
        }
    }

    @AfterReturning(pointcut = "execution(* com.digirati.elucidate.repository.AnnotationStoreRepository.updateAnnotation(..))", returning = "w3cAnnotation")
    public void afterUpdate(W3CAnnotation w3cAnnotation) {
        for (RegisteredListener registeredListener : registeredListeners) {
            taskExecutor.execute(() -> {
                LOGGER.info(String.format("Notifying listener [%s] of UPDATE on W3CAnnotation [%s]", registeredListener, w3cAnnotation));
                registeredListener.notifyUpdate(w3cAnnotation);
            });
        }
    }

    @AfterReturning(pointcut = "execution(* com.digirati.elucidate.repository.AnnotationStoreRepository.deleteAnnotation(..))", returning = "w3cAnnotation")
    public void afterDelete(W3CAnnotation w3cAnnotation) {
        for (RegisteredListener registeredListener : registeredListeners) {
            taskExecutor.execute(() -> {
                LOGGER.info(String.format("Notifying listener [%s] of DELETE on W3CAnnotation [%s]", registeredListener, w3cAnnotation));
                registeredListener.notifyDelete(w3cAnnotation);
            });
        }
    }
}
