package com.rudyk.billing.services;

import com.rudyk.billing.domain.model.Payment;
import com.rudyk.billing.domain.model.PaymentStatus;
import com.rudyk.billing.domain.vo.PaymentId;
import com.rudyk.billing.events.PaymentCompleted;
import com.rudyk.billing.domain.port.out.EventPublisher;
import com.rudyk.billing.repository.PaymentRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Application service orchestrating payment processing.
 */
@Service
public class BillingService {

    private final PaymentRepository paymentRepository;
    private final EventPublisher eventPublisher;

    public BillingService(PaymentRepository paymentRepository,
                          EventPublisher eventPublisher) {
        this.paymentRepository = paymentRepository;
        this.eventPublisher = eventPublisher;
    }

    /**
     * Charge a tab synchronously.
     */
    @Transactional
    public Payment charge(String tabId, int amountCents, String method, String idempotencyKey) {
        validate(tabId, amountCents, method);

        // Idempotency: return existing SUCCEEDED payment with the same key
        if (idempotencyKey != null && !idempotencyKey.isEmpty()) {
            Optional<Payment> existing = paymentRepository.findByIdempotencyKey(idempotencyKey);
            if (existing.isPresent() && existing.get().getStatus() == PaymentStatus.SUCCEEDED) {
                return existing.get();
            }
        }

        // Simulate PSP success
        Payment payment = Payment.create(
                PaymentId.newId(),
                tabId,
                amountCents,
                method,
                idempotencyKey,
                LocalDateTime.now()
        );
        System.out.println(payment.toString());
        payment.markSucceeded(LocalDateTime.now(), "SIMULATED_PSP_REF");

        paymentRepository.save(payment);
        System.out.println("PAYMENT_SUCCEEDED");
        // Publish integration event (version with tabId + method)
        eventPublisher.publish(new PaymentCompleted(
                payment.getId(),
                payment.getTabId(),
                payment.getAmountCents(),
                payment.getMethod(),
                payment.getOccurredAt()
        ));

        return payment;
    }

    /**
     * Refund flow (simple).
     */
    @Transactional
    public Payment refund(PaymentId paymentId, String reason) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found: " + paymentId));

        if (payment.getStatus() != PaymentStatus.SUCCEEDED) {
            throw new IllegalStateException("Only succeeded payments can be refunded");
        }

        payment.markRefunded(LocalDateTime.now(), reason);
        return paymentRepository.save(payment);
    }



    // ------- Queries for your GET endpoints -------

    @Transactional(readOnly = true)
    public Payment getById(PaymentId id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new java.util.NoSuchElementException("Payment not found: " + id.getValue()));
    }

    @Transactional(readOnly = true)
    public List<Payment> listByTab(String tabId) {
        return paymentRepository.findByTabIdOrderByOccurredAtDesc(tabId);
    }

    // ---------- helpers ----------

    private static void validate(String tabId, int amountCents, String method) {
        Objects.requireNonNull(tabId, "tabId must not be null");
        Objects.requireNonNull(method, "method must not be null");
        if (tabId.isEmpty()) throw new IllegalArgumentException("tabId must not be empty");
        if (amountCents <= 0) throw new IllegalArgumentException("amountCents must be > 0");
        if (method.isEmpty()) throw new IllegalArgumentException("method must not be empty");
    }
}