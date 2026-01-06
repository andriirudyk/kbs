package com.rudyk.kitchenbar.domain.event;



import com.rudyk.kitchenbar.domain.vo.MenuItem;
import com.rudyk.kitchenbar.domain.vo.MenuItem;
import com.rudyk.kitchenbar.domain.vo.TicketId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Domain event published when a new ticket is created.
 */
public final class TicketCreated {

    private final TicketId ticketId;
    private final List<MenuItem> items;
    private final LocalDateTime createdAt;

    public TicketCreated(TicketId ticketId, List<MenuItem> items, LocalDateTime createdAt) {
        this.ticketId = Objects.requireNonNull(ticketId, "ticketId must not be null");
        this.items = List.copyOf(Objects.requireNonNull(items, "items must not be null"));
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
    }

    public TicketId getTicketId() {
        return ticketId;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}