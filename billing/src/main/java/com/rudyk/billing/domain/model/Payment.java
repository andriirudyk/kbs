package com.rudyk.billing.domain.model;


import com.rudyk.billing.domain.vo.PaymentId;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Aggregate root representing a payment attempt for a tab/order.
 */
@Entity
@Table(name = "payments")
public class Payment implements Serializable {

    @EmbeddedId
    private PaymentId id;

    @Column(name = "tab_id", nullable = false)
    private String tabId;

    @Column(name = "amount", nullable = false)   // <-- map to existing column name
    private int amountCents;

    @Column(name = "method", nullable = false, length = 20)
    private String method; // e.g., CARD, CASH

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private PaymentStatus status;

    @Column(name = "occurred_at", nullable = false)
    private LocalDateTime occurredAt;

    @Column(name = "psp_reference")
    private String pspReference;

    @Column(name = "idempotency_key", unique = true)
    private String idempotencyKey;

    @Column(name = "failed_reason")
    private String failedReason;

    @Column(name = "refunded_at")
    private LocalDateTime refundedAt;

    @Column(name = "refund_reason")
    private String refundReason;

    protected Payment() {
        // JPA
    }

    private Payment(PaymentId id,
                    String tabId,
                    int amountCents,
                    String method,
                    String idempotencyKey,
                    LocalDateTime occurredAt) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.tabId = Objects.requireNonNull(tabId, "tabId must not be null");
        if (amountCents <= 0) throw new IllegalArgumentException("amountCents must be > 0");
        this.amountCents = amountCents;
        this.method = Objects.requireNonNull(method, "method must not be null");
        this.idempotencyKey = idempotencyKey;
        this.occurredAt = Objects.requireNonNull(occurredAt, "occurredAt must not be null");
        this.status = PaymentStatus.PENDING;
    }

    public static Payment create(PaymentId id,
                                 String tabId,
                                 int amountCents,
                                 String method,
                                 String idempotencyKey,
                                 LocalDateTime occurredAt) {
        return new Payment(id, tabId, amountCents, method, idempotencyKey, occurredAt);
    }

    // ----- state transitions -----

    public void markSucceeded(LocalDateTime at, String pspReference) {
        if (this.status == PaymentStatus.SUCCEEDED) return; // idempotent
        if (this.status == PaymentStatus.REFUNDED) throw new IllegalStateException("Already refunded");
        this.status = PaymentStatus.SUCCEEDED;
        this.pspReference = pspReference;
        this.occurredAt = at != null ? at : LocalDateTime.now();
        this.failedReason = null;
    }

    public void markFailed(LocalDateTime at, String reason) {
        if (this.status == PaymentStatus.SUCCEEDED) throw new IllegalStateException("Cannot fail a succeeded payment");
        this.status = PaymentStatus.FAILED;
        this.occurredAt = at != null ? at : LocalDateTime.now();
        this.failedReason = reason;
    }

    public void markRefunded(LocalDateTime at, String reason) {
        if (this.status != PaymentStatus.SUCCEEDED) {
            throw new IllegalStateException("Only succeeded payments can be refunded");
        }
        this.status = PaymentStatus.REFUNDED;
        this.refundedAt = at != null ? at : LocalDateTime.now();
        this.refundReason = reason;
    }

    // ----- getters -----

    public PaymentId getId() { return id; }
    public String getTabId() { return tabId; }
    public int getAmountCents() { return amountCents; }
    public String getMethod() { return method; }
    public PaymentStatus getStatus() { return status; }
    public LocalDateTime getOccurredAt() { return occurredAt; }
    public String getPspReference() { return pspReference; }
    public String getIdempotencyKey() { return idempotencyKey; }
    public String getFailedReason() { return failedReason; }
    public LocalDateTime getRefundedAt() { return refundedAt; }
    public String getRefundReason() { return refundReason; }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", tabId='" + tabId + '\'' +
                ", status=" + status +
                ", refundedAt=" + refundedAt +
                ", refundReason='" + refundReason + '\'' +
                ", pspReference='" + pspReference + '\'' +
                ", occurredAt=" + occurredAt +
                ", idempotencyKey='" + idempotencyKey + '\'' +
                ", method='" + method + '\'' +
                ", failedReason='" + failedReason + '\'' +
                ", amountCents=" + amountCents +
                '}';
    }
}