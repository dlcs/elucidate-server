package com.digirati.elucidate.infrastructure.aspect;

import com.digirati.elucidate.common.infrastructure.listener.RegisteredListener;
import com.digirati.elucidate.common.model.annotation.w3c.W3CAnnotation;
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

import java.util.ArrayList;
import java.util.List;

@Aspect
@Order(Ordered.LOWEST_PRECEDENCE - 1)
@Component(AnnotationListenerAspect.COMPONENT_NAME)
public class AnnotationListenerAspect {

    private static final Logger LOGGER = Logger.getLogger(AnnotationListenerAspect.class);

    public static final String COMPONENT_NAME = "annotationListenerAspect";

    private ApplicationContext applicationContext;
    private TaskExecutor taskExecutor;
    private List<RegisteredListener> synchronousRegisteredListeners;
    private List<RegisteredListener> asynchronousRegisteredListeners;

    @Autowired
    public AnnotationListenerAspect(ApplicationContext applicationContext, @Qualifier("listenerTaskExecutor") TaskExecutor taskExecutor, @Value("#{'${listeners.registered}'.split(',')}") List<String> configuredListeners) throws ClassNotFoundException {
        this.applicationContext = applicationContext;
        this.taskExecutor = taskExecutor;
        this.synchronousRegisteredListeners = new ArrayList<RegisteredListener>();
        this.asynchronousRegisteredListeners = new ArrayList<RegisteredListener>();
        initialiseConfiguredListeners(configuredListeners);
    }

    @SuppressWarnings("unchecked")
    private void initialiseConfiguredListeners(List<String> configuredListeners) throws ClassNotFoundException {

        if (configuredListeners != null && !configuredListeners.isEmpty()) {
            for (String configuredListener : configuredListeners) {
                if (StringUtils.isNotBlank(configuredListener)) {

                    Class<? extends RegisteredListener> clazz = (Class<? extends RegisteredListener>) Class.forName(configuredListener);
                    RegisteredListener registeredListener = applicationContext.getAutowireCapableBeanFactory().createBean(clazz);

                    if (registeredListener.executeInParallel()) {
                        registerAsynchronousListener(registeredListener);
                    } else {
                        registerSynchronousListener(registeredListener);
                    }
                }
            }
        }

    }

    public void registerSynchronousListener(RegisteredListener registeredListener) {
        if (!synchronousRegisteredListeners.contains(registeredListener)) {
            LOGGER.info(String.format("Registering synchronous listener [%s]", registeredListener));
            synchronousRegisteredListeners.add(registeredListener);
        }
    }

    public void registerAsynchronousListener(RegisteredListener registeredListener) {
        if (!asynchronousRegisteredListeners.contains(registeredListener)) {
            LOGGER.info(String.format("Registering asynchronous listener [%s]", registeredListener));
            asynchronousRegisteredListeners.add(registeredListener);
        }
    }

    public void deregisterListener(RegisteredListener registeredListener) {
        if (synchronousRegisteredListeners.contains(registeredListener)) {
            LOGGER.info(String.format("Deregistering synchronous listener [%s]", registeredListener));
            synchronousRegisteredListeners.remove(registeredListener);
        } else if (asynchronousRegisteredListeners.contains(registeredListener)) {
            LOGGER.info(String.format("Deregistering asynchronous listener [%s]", registeredListener));
            asynchronousRegisteredListeners.remove(registeredListener);
        }
    }

    @AfterReturning(pointcut = "execution(* com.digirati.elucidate.repository.AnnotationStoreRepository.createAnnotation(..))", returning = "w3cAnnotation")
    public void afterCreate(W3CAnnotation w3cAnnotation) {

        for (RegisteredListener synchronousRegisteredListener : synchronousRegisteredListeners) {
            LOGGER.info(String.format("Notifying synchronous listener [%s] of CREATE on W3CAnnotation [%s]", synchronousRegisteredListener, w3cAnnotation));
            synchronousRegisteredListener.notifyCreate(w3cAnnotation);
            LOGGER.info(String.format("Synchronous listener [%s] has finished processing CREATE on W3CAnnotation [%s]", synchronousRegisteredListener, w3cAnnotation));
        }

        for (RegisteredListener asynchronousRegisteredListener : asynchronousRegisteredListeners) {
            taskExecutor.execute(() -> {
                LOGGER.info(String.format("Notifying asynchronous listener [%s] of CREATE on W3CAnnotation [%s]", asynchronousRegisteredListener, w3cAnnotation));
                asynchronousRegisteredListener.notifyCreate(w3cAnnotation);
                LOGGER.info(String.format("Asynchronous listener [%s] has finished processing CREATE on W3CAnnotation [%s]", asynchronousRegisteredListener, w3cAnnotation));
            });
        }

    }

    @AfterReturning(pointcut = "execution(* com.digirati.elucidate.repository.AnnotationStoreRepository.updateAnnotation(..))", returning = "w3cAnnotation")
    public void afterUpdate(W3CAnnotation w3cAnnotation) {

        for (RegisteredListener synchronousRegisteredListener : synchronousRegisteredListeners) {
            LOGGER.info(String.format("Notifying synchronous listener [%s] of UPDATE on W3CAnnotation [%s]", synchronousRegisteredListener, w3cAnnotation));
            synchronousRegisteredListener.notifyUpdate(w3cAnnotation);
            LOGGER.info(String.format("Synchronous listener [%s] has finished processing UPDATE on W3CAnnotation [%s]", synchronousRegisteredListener, w3cAnnotation));
        }

        for (RegisteredListener asynchronousRegisteredListener : asynchronousRegisteredListeners) {
            taskExecutor.execute(() -> {
                LOGGER.info(String.format("Notifying asynchronous listener [%s] of UPDATE on W3CAnnotation [%s]", asynchronousRegisteredListener, w3cAnnotation));
                asynchronousRegisteredListener.notifyUpdate(w3cAnnotation);
                LOGGER.info(String.format("Asynchronous listener [%s] has finished processing UPDATE on W3CAnnotation [%s]", asynchronousRegisteredListener, w3cAnnotation));
            });
        }
    }

    @AfterReturning(pointcut = "execution(* com.digirati.elucidate.repository.AnnotationStoreRepository.deleteAnnotation(..))", returning = "w3cAnnotation")
    public void afterDelete(W3CAnnotation w3cAnnotation) {

        for (RegisteredListener synchronousRegisteredListener : synchronousRegisteredListeners) {
            LOGGER.info(String.format("Notifying synchronous listener [%s] of DELETE on W3CAnnotation [%s]", synchronousRegisteredListener, w3cAnnotation));
            synchronousRegisteredListener.notifyDelete(w3cAnnotation);
            LOGGER.info(String.format("Synchronous listener [%s] has finished processing DELETE on W3CAnnotation [%s]", synchronousRegisteredListener, w3cAnnotation));
        }

        for (RegisteredListener asynchronousRegisteredListener : asynchronousRegisteredListeners) {
            taskExecutor.execute(() -> {
                LOGGER.info(String.format("Notifying asynchronous listener [%s] of DELETE on W3CAnnotation [%s]", asynchronousRegisteredListener, w3cAnnotation));
                asynchronousRegisteredListener.notifyDelete(w3cAnnotation);
                LOGGER.info(String.format("Asynchronous listener [%s] has finished processing DELETE on W3CAnnotation [%s]", asynchronousRegisteredListener, w3cAnnotation));
            });
        }
    }
}
