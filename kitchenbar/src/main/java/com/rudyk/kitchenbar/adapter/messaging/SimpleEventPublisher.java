package com.rudyk.kitchenbar.adapter.messaging;



import com.rudyk.kitchenbar.domain.port.out.EventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component // <-- makes it a Spring bean
public class SimpleEventPublisher implements EventPublisher {
    private static final Logger log = LoggerFactory.getLogger(SimpleEventPublisher.class);

    @Override
    public void publish(Object event) {

        log.info("Publishing event: {}", event);
    }
}