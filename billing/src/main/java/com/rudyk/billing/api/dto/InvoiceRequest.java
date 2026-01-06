package com.rudyk.billing.api.dto;



import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

public class InvoiceRequest {

    @NotBlank
    public String tabId;

    @Min(1)
    public int totalCents;

    public List<InvoiceLineItem> items;

    public static class InvoiceLineItem {
        @NotBlank public String description;
        @Min(1) public int amountCents;
        public int quantity;
    }
}