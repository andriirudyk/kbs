package com.rudyk.inventoryservice.domain.port.out;



public interface EventPublisher {
    void publish(Object event);
}