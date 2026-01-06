package com.rudyk.kitchenbar.domain.model;

import com.rudyk.kitchenbar.domain.vo.MenuItem;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Aggregate root representing a kitchen/bar ticket for preparing ordered items.
 */
@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // MySQL auto-increment
    @Column(name = "id", nullable = false)
    private Long id;


    @ElementCollection
    @CollectionTable(
            name = "ticket_items",
            joinColumns = @JoinColumn(name = "ticket_id") // FK to tickets.id
    )
    private List<MenuItem> items = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TicketStatus status = TicketStatus.NEW;

    @Column(name = "station_or_assignee")
    private String stationOrAssignee;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "last_delay_minutes")
    private Integer lastDelayMinutes;

    @Column(name = "last_delay_reason")
    private String lastDelayReason;

    protected Ticket() {}

    private Ticket(List<MenuItem> items) {
        this.items = new ArrayList<>(Objects.requireNonNull(items, "items cannot be null"));
        this.status = TicketStatus.NEW;
        this.createdAt = LocalDateTime.now();
    }

    public static Ticket create(List<MenuItem> items) {
        return new Ticket(items);
    }

    /** Start the ticket and optionally assign a station/person. */
    public void start(LocalDateTime now, String stationOrAssignee) {
        if (this.status == TicketStatus.READY) {
            throw new IllegalStateException("Ticket already completed");
        }
        if (this.status == TicketStatus.IN_PROGRESS) {
            return; // idempotent
        }
        this.status = TicketStatus.IN_PROGRESS;
        this.startedAt = now;
        this.stationOrAssignee = stationOrAssignee;
    }

    /** Mark the ticket as READY (completed). */
    public void markReady(LocalDateTime now) {
        if (this.status == TicketStatus.READY) {
            return; // idempotent
        }
        this.status = TicketStatus.READY;
        this.completedAt = now;
    }

    /** Cancel the ticket before it's ready. */
    public void cancel(LocalDateTime now) {
        if (this.status == TicketStatus.READY) {
            throw new IllegalStateException("Cannot cancel a completed ticket");
        }
        this.status = TicketStatus.CANCELLED;
        this.completedAt = now;
    }

    /** Add a preparation delay with a reason. */
    public void addDelay(int minutes, String reason, LocalDateTime now) {
        if (minutes <= 0) {
            throw new IllegalArgumentException("Delay minutes must be > 0");
        }
        if (this.status == TicketStatus.READY || this.status == TicketStatus.CANCELLED) {
            throw new IllegalStateException("Cannot delay a completed/cancelled ticket");
        }
        this.lastDelayMinutes = minutes;
        this.lastDelayReason = reason;
    }

    /** Reassign the ticket to another station/person. */
    public void reassign(String newStationOrAssignee, LocalDateTime now) {
        if (this.status == TicketStatus.READY) {
            throw new IllegalStateException("Cannot reassign a completed ticket");
        }
        this.stationOrAssignee = newStationOrAssignee;
    }

    // --- Getters ---
    public Long getId() {
        return id;
    }

    public List<MenuItem> getItems() {
        return new ArrayList<>(items);
    }

    public TicketStatus getStatus() {
        return status;
    }

    public String getStationOrAssignee() {
        return stationOrAssignee;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public Integer getLastDelayMinutes() {
        return lastDelayMinutes;
    }

    public String getLastDelayReason() {
        return lastDelayReason;
    }
}