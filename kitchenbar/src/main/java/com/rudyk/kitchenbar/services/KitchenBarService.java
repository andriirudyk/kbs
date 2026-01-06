package com.rudyk.kitchenbar.services;

import com.rudyk.kitchenbar.domain.event.TicketReady;
import com.rudyk.kitchenbar.domain.model.Ticket;
import com.rudyk.kitchenbar.domain.model.TicketStatus;
import com.rudyk.kitchenbar.repositories.TicketRepository;
import com.rudyk.kitchenbar.domain.vo.MenuItem;
import com.rudyk.kitchenbar.domain.port.out.EventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class KitchenBarService {

    private final TicketRepository ticketRepository;
    private final EventPublisher eventPublisher;

    public KitchenBarService(TicketRepository ticketRepository,
                             EventPublisher eventPublisher) {
        this.ticketRepository = ticketRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public Ticket createTicket(List<MenuItem> items) {
        Ticket ticket = Ticket.create(items);
        return ticketRepository.save(ticket);
    }


    @Transactional
    public Ticket markTicketReady(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NoSuchElementException("Ticket not found: " + ticketId));

        if (ticket.getStatus() == TicketStatus.READY) {
            return ticket; // idempotent
        }

        ticket.markReady(LocalDateTime.now());
        ticketRepository.save(ticket);

        eventPublisher.publish(new TicketReady(ticket.getId(), LocalDateTime.now()));
        return ticket;
    }

    @Transactional(readOnly = true)
    public Optional<Ticket> getTicket(Long ticketId) {
        return ticketRepository.findById(ticketId);
    }

    @Transactional
    public Ticket cancelTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NoSuchElementException("Ticket not found: " + ticketId));
        if (ticket.getStatus() == TicketStatus.READY) {
            throw new IllegalStateException("Cannot cancel a READY ticket");
        }
        ticket.cancel(LocalDateTime.now());
        return ticketRepository.save(ticket);
    }
}