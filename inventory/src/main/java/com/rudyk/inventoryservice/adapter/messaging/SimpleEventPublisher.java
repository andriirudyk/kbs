package com.rudyk.inventoryservice.adapter.messaging;



import com.rudyk.inventoryservice.domain.port.out.EventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SimpleEventPublisher implements EventPublisher {
    @Override
    public void publish(Object event) {
        // Для демонстрації: просто логувати або System.out.println
        System.out.println("EVENT PUBLISHED: " + event);
    }
}