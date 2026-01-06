package com.rudyk.kitchenbar.domain.vo;



import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Value Object for preparation time in minutes.
 */
@Embeddable
public class PreparationTime implements Serializable {

    private int minutes;

    protected PreparationTime() {
        // JPA
    }

    public PreparationTime(int minutes) {
        if (minutes < 0) {
            throw new IllegalArgumentException("Preparation time cannot be negative");
        }
        this.minutes = minutes;
    }

    public int getMinutes() {
        return minutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PreparationTime)) return false;
        PreparationTime that = (PreparationTime) o;
        return minutes == that.minutes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(minutes);
    }

    @Override
    public String toString() {
        return minutes + " min";
    }
}