package com.rudyk.billing.domain.model;



import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Aggregate root representing a generated invoice for a tab/order.
 */
@Entity
@Table(name = "invoices")
public class Invoice implements Serializable {

    @Id
    @Column(name = "invoice_id", nullable = false, updatable = false)
    private String id;

    @Column(name = "tab_id", nullable = false)
    private String tabId; // Business reference to the originating tab/order

    @Column(name = "amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency; // ISO 4217 code, e.g., "USD"

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private InvoiceStatus status;

    @Column(name = "issued_at", nullable = false)
    private LocalDateTime issuedAt;

    @Column(name = "due_at")
    private LocalDateTime dueAt;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    @Column(name = "payment_reference")
    private String paymentReference; // e.g., PSP transaction id

    protected Invoice() {
        // JPA
    }

    private Invoice(String tabId, BigDecimal amount, String currency, LocalDateTime dueAt) {
        this.id = UUID.randomUUID().toString();
        this.tabId = Objects.requireNonNull(tabId, "tabId must not be null");
        this.amount = Objects.requireNonNull(amount, "amount must not be null");
        this.currency = Objects.requireNonNull(currency, "currency must not be null");
        this.status = InvoiceStatus.PENDING;
        this.issuedAt = LocalDateTime.now();
        this.dueAt = dueAt;
    }

    public static Invoice create(String tabId, BigDecimal amount, String currency, LocalDateTime dueAt) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be > 0");
        }
        return new Invoice(tabId, amount, currency, dueAt);
    }

    public void markPaid(String paymentReference, LocalDateTime paidAt) {
        if (this.status == InvoiceStatus.PAID) {
            return; // idempotent
        }
        this.status = InvoiceStatus.PAID;
        this.paymentReference = paymentReference;
        this.paidAt = paidAt;
    }

    public void markCancelled() {
        if (this.status == InvoiceStatus.PAID) {
            throw new IllegalStateException("Cannot cancel a paid invoice");
        }
        this.status = InvoiceStatus.CANCELLED;
    }

    // --- Getters ---
    public String getId() {
        return id;
    }

    public String getTabId() {
        return tabId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public LocalDateTime getDueAt() {
        return dueAt;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    // equals/hashCode based on id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Invoice)) return false;
        Invoice invoice = (Invoice) o;
        return Objects.equals(id, invoice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}