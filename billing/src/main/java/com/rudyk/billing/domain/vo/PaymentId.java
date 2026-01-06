package com.rudyk.billing.domain.vo;



import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Value Object representing the unique identifier for a Payment.
 */
@Embeddable
public class PaymentId implements Serializable {

    private String value;

    protected PaymentId() {
        // Required by JPA
    }

    private PaymentId(String value) {
        this.value = Objects.requireNonNull(value, "value must not be null");
    }

    /**
     * Creates a new PaymentId with a random UUID value.
     */
    public static PaymentId newId() {
        return new PaymentId(UUID.randomUUID().toString());
    }

    /**
     * Creates a PaymentId from an existing value.
     */
    public static PaymentId of(String value) {
        return new PaymentId(value);
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
        if (!(o instanceof PaymentId)) return false;
        PaymentId that = (PaymentId) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}