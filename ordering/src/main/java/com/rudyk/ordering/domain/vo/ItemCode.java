package com.rudyk.ordering.domain.vo;


import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Pattern;

public final class ItemCode implements Serializable {
    private static final Pattern VALID = Pattern.compile("[A-Z]{2,5}-\\d{2}");
    private final String value;

    public ItemCode(String value) {
        if (value == null || !VALID.matcher(value).matches()) {
            throw new IllegalArgumentException(
                    "ItemCode must match pattern [A-Z]{2,5}-\\d{2}, got: " + value);
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        return this == o ||
                (o instanceof ItemCode && value.equals(((ItemCode) o).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}