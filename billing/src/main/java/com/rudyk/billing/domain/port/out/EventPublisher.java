package com.rudyk.billing.domain.port.out;


import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Simple event publisher that uses Spring's ApplicationEventPublisher
 * to propagate domain events synchronously inside the same JVM.
 */
@Component
public class EventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public EventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * Publishes a domain event to the Spring application context.
     * @param event the event to publish
     */
    public void publish(Object event) {
        applicationEventPublisher.publishEvent(event);
    }
}