package com.rudyk.inventoryservice.domain.vo;


import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Значимий код інвентарної позиції (напр. "IPA-01", "BURGER-03").
 */
@Embeddable
public final class ItemCode implements Serializable {
    private static final Pattern VALID = Pattern.compile("[A-Z]{2,8}-\\d{2}");

    private String value;

    protected ItemCode() {} // For JPA

    public ItemCode(String value) {
        if (value == null || !VALID.matcher(value).matches()) {
            throw new IllegalArgumentException(
                    "ItemCode must match pattern [A-Z]{2,8}-\\d{2}, got: " + value);
        }
        this.value = value;
    }

    public String getValue() { return value; }

    @Override
    public boolean equals(Object o) {
        return this == o ||
                (o instanceof ItemCode && value.equals(((ItemCode) o).value));
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}