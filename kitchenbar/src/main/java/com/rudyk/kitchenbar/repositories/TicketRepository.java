package com.rudyk.kitchenbar.repositories;


import com.rudyk.kitchenbar.domain.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Outgoing domain port for persisting and querying Tickets.
 * Implement in adapter/persistence (e.g., JPA).
 */
public interface TicketRepository extends JpaRepository<Ticket,Long> {



}