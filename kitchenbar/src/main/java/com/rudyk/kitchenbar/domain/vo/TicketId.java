package com.rudyk.kitchenbar.domain.vo;



import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Value Object for Ticket identifier.
 */
@Embeddable
public class TicketId implements Serializable {

    private String value;


    protected TicketId() {
        // JPA
    }

    private TicketId(String value) {
        this.value = value;
    }

    public static TicketId newId() {
        return new TicketId(UUID.randomUUID().toString());
    }

    public static TicketId of(String value) {
        return new TicketId(value);
    }

    public String getValue() {
        return value;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TicketId)) return false;
        TicketId ticketId = (TicketId) o;
        return Objects.equals(value, ticketId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }

}