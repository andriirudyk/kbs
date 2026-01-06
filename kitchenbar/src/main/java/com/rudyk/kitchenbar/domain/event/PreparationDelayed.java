package com.rudyk.kitchenbar.domain.event;



import com.rudyk.kitchenbar.domain.vo.TicketId;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Domain event published when preparation of a ticket is delayed.
 */
public final class PreparationDelayed {

    private final Long ticketId;
    private final int extraMinutes;
    private final String reason;
    private final LocalDateTime delayedAt;

    public PreparationDelayed(Long ticketId, int extraMinutes, String reason, LocalDateTime delayedAt) {
        if (extraMinutes <= 0) {
            throw new IllegalArgumentException("extraMinutes must be > 0");
        }
        this.ticketId = Objects.requireNonNull(ticketId, "ticketId must not be null");
        this.extraMinutes = extraMinutes;
        this.reason = Objects.requireNonNull(reason, "reason must not be null");
        this.delayedAt = Objects.requireNonNull(delayedAt, "delayedAt must not be null");
    }

    public Long getTicketId() {
        return ticketId;
    }

    public int getExtraMinutes() {
        return extraMinutes;
    }

    public String getReason() {
        return reason;
    }

    public LocalDateTime getDelayedAt() {
        return delayedAt;
    }
}