package com.rudyk.billing.domain.vo;



import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Value Object representing the unique identifier for an Invoice.
 */
@Embeddable
public class InvoiceId implements Serializable {

    private String value;

    protected InvoiceId() {
        // JPA requires default constructor
    }

    private InvoiceId(String value) {
        this.value = Objects.requireNonNull(value, "value must not be null");
    }

    /**
     * Generate a new unique InvoiceId.
     */
    public static InvoiceId newId() {
        return new InvoiceId(UUID.randomUUID().toString());
    }

    /**
     * Create an InvoiceId from an existing value.
     */
    public static InvoiceId of(String value) {
        return new InvoiceId(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InvoiceId)) return false;
        InvoiceId that = (InvoiceId) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}