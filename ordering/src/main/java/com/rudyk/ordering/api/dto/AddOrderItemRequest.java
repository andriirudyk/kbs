package com.rudyk.ordering.api.dto;


import java.math.BigDecimal;

public class AddOrderItemRequest {
    public String itemCode;
    public String displayName;
    public BigDecimal unitPrice;
    public int quantity;
}
