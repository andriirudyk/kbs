package com.rudyk.ordering.api.dto;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Request payload for creating a new kitchen/bar ticket.
 */
public class CreateTicketRequest {

    /**
     * External order reference (from ordering service).
     */
    private String orderId;   // optional (not used for now)
    @NotEmpty
    private List<ItemRequest> items;

    // --- getters/setters ---

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }



    public List<ItemRequest> getItems() {
        return items;
    }

    public void setItems(List<ItemRequest> items) {
        this.items = items;
    }
}