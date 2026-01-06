package com.rudyk.inventoryservice.domain.model;


import com.rudyk.inventoryservice.domain.vo.ItemCode;

import java.time.LocalDateTime;

public abstract class InventoryEvent {
    private final ItemCode itemCode;
    private final LocalDateTime occurredAt;

    protected InventoryEvent(ItemCode itemCode) {
        this.itemCode = itemCode;
        this.occurredAt = LocalDateTime.now();
    }

    public ItemCode getItemCode() { return itemCode; }
    public LocalDateTime getOccurredAt() { return occurredAt; }
}