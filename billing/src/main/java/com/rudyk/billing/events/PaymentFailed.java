package com.rudyk.billing.events;



import com.rudyk.billing.domain.vo.PaymentId;
import com.rudyk.billing.domain.vo.InvoiceId;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Domain event indicating that a payment attempt has failed.
 */
public class PaymentFailed implements Serializable {

    private final PaymentId paymentId;
    private final InvoiceId invoiceId;
    private final String reason;
    private final LocalDateTime occurredAt;

    public PaymentFailed(PaymentId paymentId,
                         InvoiceId invoiceId,
                         String reason,
                         LocalDateTime occurredAt) {
        this.paymentId = Objects.requireNonNull(paymentId, "paymentId must not be null");
        this.invoiceId = Objects.requireNonNull(invoiceId, "invoiceId must not be null");
        this.reason = Objects.requireNonNull(reason, "reason must not be null");
        this.occurredAt = Objects.requireNonNull(occurredAt, "occurredAt must not be null");
    }

    public PaymentId getPaymentId() {
        return paymentId;
    }

    public InvoiceId getInvoiceId() {
        return invoiceId;
    }

    public String getReason() {
        return reason;
    }

    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }

    @Override
    public String toString() {
        return "PaymentFailed{" +
                "paymentId=" + paymentId +
                ", invoiceId=" + invoiceId +
                ", reason='" + reason + '\'' +
                ", occurredAt=" + occurredAt +
                '}';
    }
}