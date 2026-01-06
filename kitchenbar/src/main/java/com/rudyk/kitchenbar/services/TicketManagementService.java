package com.rudyk.kitchenbar.services;

import com.rudyk.kitchenbar.domain.event.PreparationDelayed;
import com.rudyk.kitchenbar.domain.event.TicketReady;
import com.rudyk.kitchenbar.domain.model.Ticket;
import com.rudyk.kitchenbar.domain.model.TicketStatus;
import com.rudyk.kitchenbar.repositories.TicketRepository;
import com.rudyk.kitchenbar.domain.port.out.EventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TicketManagementService {

    private final TicketRepository ticketRepository;
    private final EventPublisher eventPublisher;

    public TicketManagementService(TicketRepository ticketRepository,
                                   EventPublisher eventPublisher) {
        this.ticketRepository = ticketRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public Ticket startTicket(Long ticketId, String stationOrAssignee) {
        Objects.requireNonNull(ticketId, "ticketId must not be null");

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NoSuchElementException("Ticket not found: " + ticketId));

        if (ticket.getStatus() == TicketStatus.READY) {
            throw new IllegalStateException("Ticket already READY");
        }
        if (ticket.getStatus() == TicketStatus.IN_PROGRESS) {
            return ticket; // idempotent
        }

        ticket.start(LocalDateTime.now(), stationOrAssignee);
        return ticketRepository.save(ticket);
    }

    @Transactional
    public Ticket addPreparationDelay(Long ticketId, int extraMinutes, String reason) {
        if (extraMinutes <= 0) {
            throw new IllegalArgumentException("extraMinutes must be > 0");
        }
        Objects.requireNonNull(ticketId, "ticketId must not be null");

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NoSuchElementException("Ticket not found: " + ticketId));

        ticket.addDelay(extraMinutes, reason, LocalDateTime.now());
        ticketRepository.save(ticket);

        eventPublisher.publish(new PreparationDelayed(ticket.getId(), extraMinutes, reason, LocalDateTime.now()));
        return ticket;
    }

    @Transactional
    public Ticket completeTicket(Long ticketId) {
        Objects.requireNonNull(ticketId, "ticketId must not be null");

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NoSuchElementException("Ticket not found: " + ticketId));

        if (ticket.getStatus() == TicketStatus.READY) {
            return ticket;
        }

        ticket.markReady(LocalDateTime.now());
        ticketRepository.save(ticket);

        eventPublisher.publish(new TicketReady(ticket.getId(), LocalDateTime.now()));
        return ticket;
    }

    @Transactional
    public Ticket reassign(Long ticketId, String newStationOrAssignee) {
        Objects.requireNonNull(ticketId, "ticketId must not be null");
        Objects.requireNonNull(newStationOrAssignee, "assignee must not be null");

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NoSuchElementException("Ticket not found: " + ticketId));

        if (ticket.getStatus() == TicketStatus.READY) {
            throw new IllegalStateException("Cannot reassign READY ticket");
        }

        ticket.reassign(newStationOrAssignee, LocalDateTime.now());
        return ticketRepository.save(ticket);
    }

    @Transactional(readOnly = true)
    public List<Ticket> getActiveTickets() {
        return ticketRepository.findAll().stream()
                .filter(t -> t.getStatus() != TicketStatus.READY)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Ticket> getRecentlyCompleted(LocalDateTime since) {
        return ticketRepository.findAll().stream()
                .filter(t -> t.getStatus() == TicketStatus.READY
                        && t.getCompletedAt() != null
                        && !t.getCompletedAt().isBefore(since))
                .collect(Collectors.toList());
    }
}