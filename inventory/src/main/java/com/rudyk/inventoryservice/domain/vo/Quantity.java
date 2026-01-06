package com.rudyk.inventoryservice.domain.vo;


import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Значимий об'єкт-обгортка для кількості інвентарної позиції.
 */
@Embeddable
public final class Quantity implements Serializable {

    private int value;

    protected Quantity() {} // For JPA

    public Quantity(int value) {
        if (value < 0) throw new IllegalArgumentException("Quantity must be >= 0");
        this.value = value;
    }

    public int getValue() { return value; }

    public Quantity plus(Quantity other) {
        return new Quantity(this.value + other.value);
    }

    public Quantity minus(Quantity other) {
        int result = this.value - other.value;
        if (result < 0) throw new IllegalArgumentException("Quantity cannot be negative");
        return new Quantity(result);
    }

    public boolean isLessThan(Quantity other) {
        return this.value < other.value;
    }

    public boolean isZero() {
        return value == 0;
    }

    @Override
    public boolean equals(Object o) {
        return this == o ||
                (o instanceof Quantity && value == ((Quantity) o).value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    public void setValue(int value) {
        this.value = value;
    }
}