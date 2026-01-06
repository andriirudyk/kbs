package com.rudyk.ordering.domain.vo;



import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public final class TabId implements Serializable {
    private final UUID value;

    public TabId(UUID value) {
        this.value = Objects.requireNonNull(value);
    }

    public static TabId random() {
        return new TabId(UUID.randomUUID());
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        return this == o ||
                (o instanceof TabId && value.equals(((TabId) o).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}