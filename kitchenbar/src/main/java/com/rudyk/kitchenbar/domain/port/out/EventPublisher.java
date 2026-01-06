package com.rudyk.kitchenbar.domain.port.out;



/**
 * Outgoing domain port for publishing domain events (e.g., to Kafka, RabbitMQ).
 * Provide an implementation in adapter/messaging.
 */
public interface EventPublisher {
    void publish(Object event);
}