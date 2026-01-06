package com.rudyk.billing.api.dto;

import com.rudyk.billing.domain.model.Invoice;

public class InvoiceResponse {

    public String id;
    public String tabId;
    public String amount;             // formatted BigDecimal as string
    public String currency;           // e.g., "PLN"
    public String status;             // PENDING | PAID | CANCELLED
    public String issuedAt;
    public String dueAt;
    public String paidAt;
    public String paymentReference;

    public static InvoiceResponse from(Invoice invoice) {
        InvoiceResponse r = new InvoiceResponse();
        r.id = invoice.getId(); // id is String in your current model
        r.tabId = invoice.getTabId();
        r.amount = invoice.getAmount() != null ? invoice.getAmount().toPlainString() : null;
        r.currency = invoice.getCurrency();
        r.status = invoice.getStatus() != null ? invoice.getStatus().name() : null;
        r.issuedAt = invoice.getIssuedAt() != null ? invoice.getIssuedAt().toString() : null;
        r.dueAt = invoice.getDueAt() != null ? invoice.getDueAt().toString() : null;
        r.paidAt = invoice.getPaidAt() != null ? invoice.getPaidAt().toString() : null;
        r.paymentReference = invoice.getPaymentReference();
        return r;
    }
}