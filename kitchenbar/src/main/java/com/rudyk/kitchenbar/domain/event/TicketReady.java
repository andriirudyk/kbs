package com.rudyk.kitchenbar.domain.event;



import com.rudyk.kitchenbar.domain.vo.TicketId;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Domain event published when a ticket is marked READY.
 */
public final class TicketReady {

    private final Long ticketId;
    private final LocalDateTime readyAt;

    public TicketReady(Long ticketId, LocalDateTime readyAt) {
        this.ticketId = Objects.requireNonNull(ticketId, "ticketId must not be null");
        this.readyAt = Objects.requireNonNull(readyAt, "readyAt must not be null");
    }

    public Long getTicketId() {
        return ticketId;
    }

    public LocalDateTime getReadyAt() {
        return readyAt;
    }
}