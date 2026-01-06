package com.rudyk.inventoryservice.domain.event;



import com.rudyk.inventoryservice.domain.vo.ItemCode;
import com.rudyk.inventoryservice.domain.vo.Quantity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Domain event: Кількість залишку критично низька
 */
public final class StockLow implements Serializable {
    private final ItemCode itemCode;
    private final Quantity quantity;
    private final LocalDateTime occurredAt;

    public StockLow(ItemCode itemCode, Quantity quantity) {
        this.itemCode = Objects.requireNonNull(itemCode);
        this.quantity = Objects.requireNonNull(quantity);
        this.occurredAt = LocalDateTime.now();
    }

    public ItemCode getItemCode() { return itemCode; }
    public Quantity getQuantity() { return quantity; }
    public LocalDateTime getOccurredAt() { return occurredAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StockLow)) return false;
        StockLow that = (StockLow) o;
        return itemCode.equals(that.itemCode)
                && quantity.equals(that.quantity)
                && occurredAt.equals(that.occurredAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemCode, quantity, occurredAt);
    }

    @Override
    public String toString() {
        return "StockLow{" +
                "itemCode=" + itemCode +
                ", quantity=" + quantity +
                ", occurredAt=" + occurredAt +
                '}';
    }
}