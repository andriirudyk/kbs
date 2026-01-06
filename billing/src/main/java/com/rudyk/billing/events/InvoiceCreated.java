package com.rudyk.billing.events;



import com.rudyk.billing.domain.vo.InvoiceId;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Domain event indicating a new invoice has been created.
 */
public class InvoiceCreated implements Serializable {

    private final InvoiceId invoiceId;
    private final String tabId;
    private final int totalCents;
    private final LocalDateTime occurredAt;

    public InvoiceCreated(InvoiceId invoiceId, String tabId, int totalCents, LocalDateTime occurredAt) {
        this.invoiceId = Objects.requireNonNull(invoiceId, "invoiceId must not be null");
        this.tabId = Objects.requireNonNull(tabId, "tabId must not be null");
        this.totalCents = totalCents;
        this.occurredAt = Objects.requireNonNull(occurredAt, "occurredAt must not be null");
    }

    public InvoiceId getInvoiceId() {
        return invoiceId;
    }

    public String getTabId() {
        return tabId;
    }

    public int getTotalCents() {
        return totalCents;
    }

    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }

    @Override
    public String toString() {
        return "InvoiceCreated{" +
                "invoiceId=" + invoiceId +
                ", tabId='" + tabId + '\'' +
                ", totalCents=" + totalCents +
                ", occurredAt=" + occurredAt +
                '}';
    }
}