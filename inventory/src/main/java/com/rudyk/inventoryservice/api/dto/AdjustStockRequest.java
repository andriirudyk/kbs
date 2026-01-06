package com.rudyk.inventoryservice.api.dto;



/**
 * DTO для запиту на зміну залишку (наприклад, надходження чи ручна корекція).
 */
public class AdjustStockRequest {
    public String itemCode;
    public int newQuantity;
}