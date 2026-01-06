package com.rudyk.billing.events; // <- keep this package consistent with your project

import com.rudyk.billing.domain.vo.PaymentId;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Integration event: payment succeeded for a tab.
 */
public class PaymentCompleted implements Serializable {

    private final PaymentId paymentId;
    private final String tabId;
    private final int amountCents;
    private final String method;           // e.g. CARD, CASH
    private final LocalDateTime occurredAt;

    public PaymentCompleted(PaymentId paymentId,
                            String tabId,
                            int amountCents,
                            String method,
                            LocalDateTime occurredAt) {
        this.paymentId = Objects.requireNonNull(paymentId, "paymentId must not be null");
        this.tabId = Objects.requireNonNull(tabId, "tabId must not be null");
        this.amountCents = amountCents;
        this.method = Objects.requireNonNull(method, "method must not be null");
        this.occurredAt = Objects.requireNonNull(occurredAt, "occurredAt must not be null");
    }

    public PaymentId getPaymentId() { return paymentId; }
    public String getTabId() { return tabId; }
    public int getAmountCents() { return amountCents; }
    public String getMethod() { return method; }
    public LocalDateTime getOccurredAt() { return occurredAt; }

    @Override
    public String toString() {
        return "PaymentCompleted{" +
                "paymentId=" + paymentId +
                ", tabId='" + tabId + '\'' +
                ", amountCents=" + amountCents +
                ", method='" + method + '\'' +
                ", occurredAt=" + occurredAt +
                '}';
    }
}