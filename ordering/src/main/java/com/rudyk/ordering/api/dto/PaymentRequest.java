package com.rudyk.ordering.api.dto;



import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class PaymentRequest {

    @NotBlank
    public String tabId;

    @Min(1)
    public int amountCents;

    @NotBlank
    public String method; // e.g., CARD, CASH

    public String idempotencyKey; // optional

    public Integer tipCents; // optional
}