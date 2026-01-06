package com.rudyk.ordering.api.dto;


import java.math.BigDecimal;
import java.util.List;

public class TabResponse {
    public Long id;
    public int tableNumber;
    public String status;
    public List<OrderItemResponse> items;
    public BigDecimal total;

    public List<OrderItemResponse> getItems() {
        return items;
    }

    public void setItems(List<OrderItemResponse> items) {
        this.items = items;
    }
}