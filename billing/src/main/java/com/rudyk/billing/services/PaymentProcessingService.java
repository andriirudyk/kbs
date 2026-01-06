package com.rudyk.billing.services;


import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;


@Service
public class PaymentProcessingService {


    public ProcessingOutcome process(String tabId, int amountCents, String method, String idempotencyKey) {
        validate(tabId, amountCents, method);

        // --- Simulated PSP logic ---
        // You can plug real gateway logic here (e.g., Feign client).
        String pspReference = "PSP-" + UUID.randomUUID();
        LocalDateTime occurredAt = LocalDateTime.now();

        // Example rule: decline zero/negative or absurdly large amounts (guardrails)
        if (amountCents <= 0 || amountCents > 5_000_000) {
            return ProcessingOutcome.failed("AMOUNT_NOT_ALLOWED", "Invalid amount", occurredAt);
        }

        // Example rule: accept only known methods
        if (!"CARD".equalsIgnoreCase(method) && !"CASH".equalsIgnoreCase(method)) {
            return ProcessingOutcome.failed("UNSUPPORTED_METHOD", "Unsupported payment method: " + method, occurredAt);
        }

        // If all OK, mark as succeeded
        return ProcessingOutcome.succeeded(pspReference, occurredAt, idempotencyKey);
    }

    /**
     * Optional helper for adding tips before charging.
     *
     * @param baseAmountCents base amount (required)
     * @param tipCents        nullable tip in cents
     * @return total amount in cents
     */
    public int computeTotalWithTip(int baseAmountCents, Integer tipCents) {
        if (baseAmountCents < 0) {
            throw new IllegalArgumentException("baseAmountCents must be >= 0");
        }
        int tip = tipCents == null ? 0 : tipCents;
        if (tip < 0) {
            throw new IllegalArgumentException("tipCents must be >= 0");
        }
        long total = (long) baseAmountCents + tip; // prevent overflow
        if (total > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("total exceeds allowed range");
        }
        return (int) total;
    }

    // ----- helpers -----

    private static void validate(String tabId, int amountCents, String method) {
        Objects.requireNonNull(tabId, "tabId must not be null");
        Objects.requireNonNull(method, "method must not be null");
        if (tabId.isEmpty()) throw new IllegalArgumentException("tabId must not be empty");
        if (amountCents <= 0) throw new IllegalArgumentException("amountCents must be > 0");
        if (method.isEmpty()) throw new IllegalArgumentException("method must not be empty");
    }

    // ----- DTO for processing outcome -----

    public static final class ProcessingOutcome {
        public enum Status { SUCCEEDED, FAILED }

        private final Status status;
        private final String pspReference;   // present on success
        private final String errorCode;      // present on failure
        private final String errorMessage;   // present on failure
        private final LocalDateTime occurredAt;
        private final String idempotencyKey;

        private ProcessingOutcome(Status status,
                                  String pspReference,
                                  String errorCode,
                                  String errorMessage,
                                  LocalDateTime occurredAt,
                                  String idempotencyKey) {
            this.status = status;
            this.pspReference = pspReference;
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
            this.occurredAt = occurredAt;
            this.idempotencyKey = idempotencyKey;
        }

        public static ProcessingOutcome succeeded(String pspReference, LocalDateTime at, String idemKey) {
            return new ProcessingOutcome(Status.SUCCEEDED, pspReference, null, null, at, idemKey);
        }

        public static ProcessingOutcome failed(String errorCode, String message, LocalDateTime at) {
            return new ProcessingOutcome(Status.FAILED, null, errorCode, message, at, null);
        }

        public Status getStatus() { return status; }
        public String getPspReference() { return pspReference; }
        public String getErrorCode() { return errorCode; }
        public String getErrorMessage() { return errorMessage; }
        public LocalDateTime getOccurredAt() { return occurredAt; }
        public String getIdempotencyKey() { return idempotencyKey; }

        public boolean isSucceeded() { return status == Status.SUCCEEDED; }
        public boolean isFailed() { return status == Status.FAILED; }
    }
}