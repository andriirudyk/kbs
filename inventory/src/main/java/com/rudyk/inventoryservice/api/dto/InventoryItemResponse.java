package com.rudyk.inventoryservice.api.dto;


import com.rudyk.inventoryservice.domain.model.InventoryItem;

/**
 * DTO для повернення стану інвентарної позиції у відповіді REST.
 */
public class InventoryItemResponse {
    public String itemCode;
    public int quantity;
    public String status;

    public static InventoryItemResponse fromDomain(InventoryItem item) {
        InventoryItemResponse dto = new InventoryItemResponse();
        dto.itemCode = item.getItemCode().getValue();
        dto.quantity = item.getQuantity().getValue();
        dto.status = item.getStatus().name();
        return dto;
    }
}