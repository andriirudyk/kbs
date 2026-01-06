package com.rudyk.kitchenbar.domain.model;



/**
 * Lifecycle states of a kitchen/bar ticket.
 */
public enum TicketStatus {
    NEW,          // Created but not yet started
    IN_PROGRESS,  // Being prepared
    READY,        // Finished and ready to serve
    CANCELLED     // Cancelled before completion
}