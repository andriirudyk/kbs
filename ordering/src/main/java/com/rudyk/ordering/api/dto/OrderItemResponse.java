package com.rudyk.ordering.api.dto;

import java.math.BigDecimal;

public class OrderItemResponse {
    public String itemCode;
    public String displayName;
    public BigDecimal unitPrice;
    public int quantity;
    public BigDecimal price;
}