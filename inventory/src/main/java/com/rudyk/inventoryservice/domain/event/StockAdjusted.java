package com.rudyk.inventoryservice.domain.event;



import com.rudyk.inventoryservice.domain.vo.ItemCode;
import com.rudyk.inventoryservice.domain.vo.Quantity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Domain event: Залишок товару був змінений (наприклад, під час приходу/списання/корекції)
 */
public final class StockAdjusted implements Serializable {
    private final ItemCode itemCode;
    private final Quantity previousQuantity;
    private final Quantity newQuantity;
    private final LocalDateTime occurredAt;

    public StockAdjusted(ItemCode itemCode, Quantity previousQuantity, Quantity newQuantity) {
        this.itemCode = Objects.requireNonNull(itemCode);
        this.previousQuantity = Objects.requireNonNull(previousQuantity);
        this.newQuantity = Objects.requireNonNull(newQuantity);
        this.occurredAt = LocalDateTime.now();
    }

    public ItemCode getItemCode() { return itemCode; }
    public Quantity getPreviousQuantity() { return previousQuantity; }
    public Quantity getNewQuantity() { return newQuantity; }
    public LocalDateTime getOccurredAt() { return occurredAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StockAdjusted)) return false;
        StockAdjusted that = (StockAdjusted) o;
        return itemCode.equals(that.itemCode)
                && previousQuantity.equals(that.previousQuantity)
                && newQuantity.equals(that.newQuantity)
                && occurredAt.equals(that.occurredAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemCode, previousQuantity, newQuantity, occurredAt);
    }

    @Override
    public String toString() {
        return "StockAdjusted{" +
                "itemCode=" + itemCode +
                ", previousQuantity=" + previousQuantity +
                ", newQuantity=" + newQuantity +
                ", occurredAt=" + occurredAt +
                '}';
    }
}
