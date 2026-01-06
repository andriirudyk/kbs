package com.rudyk.billing.api.dto;



import com.rudyk.billing.domain.model.Payment;

public class PaymentResponse {

    public String id;
    public String tabId;
    public int amountCents;
    public String method;
    public String status;
    public String occurredAt;
    public String pspReference;
    public String idempotencyKey;
    public String failedReason;
    public String refundedAt;
    public String refundReason;

    public static PaymentResponse from(Payment p) {
        PaymentResponse r = new PaymentResponse();
        r.id = p.getId() != null ? p.getId().getValue() : null;
        r.tabId = p.getTabId();
        r.amountCents = p.getAmountCents();
        r.method = p.getMethod();
        r.status = p.getStatus() != null ? p.getStatus().name() : null;
        r.occurredAt = p.getOccurredAt() != null ? p.getOccurredAt().toString() : null;
        r.pspReference = p.getPspReference();
        r.idempotencyKey = p.getIdempotencyKey();
        r.failedReason = p.getFailedReason();
        r.refundedAt = p.getRefundedAt() != null ? p.getRefundedAt().toString() : null;
        r.refundReason = p.getRefundReason();
        return r;
    }
}