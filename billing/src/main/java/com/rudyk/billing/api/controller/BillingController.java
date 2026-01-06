package com.rudyk.billing.api.controller;


import com.rudyk.billing.services.BillingService;
import com.rudyk.billing.services.PaymentProcessingService;
import com.rudyk.billing.domain.model.Payment;
import com.rudyk.billing.domain.vo.PaymentId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/billing/payments")
public class BillingController {

    private final BillingService billingService;
    private final PaymentProcessingService processingService;

    public BillingController(BillingService billingService,
                             PaymentProcessingService processingService) {
        this.billingService = billingService;
        this.processingService = processingService;
    }

    // ========= Commands =========

    /**
     * Charge a payment for a tab.
     * Body example:
     * {
     *   "tabId": "TAB-123",
     *   "amountCents": 2599,
     *   "method": "CARD",
     *   "idempotencyKey": "client-uuid-123",   // optional
     *   "tipCents": 0                           // optional
     * }
     */
    @PostMapping
    public PaymentResponse charge(@RequestBody PaymentRequest req) {
        int amount = req.amountCents;
        if (req.tipCents != null && req.tipCents > 0) {
            amount = processingService.computeTotalWithTip(req.amountCents, req.tipCents);
        }

        Payment p = billingService.charge(req.tabId, amount, req.method, req.idempotencyKey);
        return PaymentResponse.from(p);
    }

    /**
     * Refund a succeeded payment.
     * Body example:
     * { "reason": "Customer request" }
     */
    @PostMapping("/{paymentId}/refund")
    public PaymentResponse refund(@PathVariable String paymentId,
                                  @RequestBody RefundRequest req) {
        Payment p = billingService.refund(PaymentId.of(paymentId), req.reason);
        return PaymentResponse.from(p);
    }

    // ========= Queries =========

    @GetMapping("/{paymentId}")
    public PaymentResponse getById(@PathVariable Integer paymentId) {
        Payment p = billingService.getById(PaymentId.of(paymentId.toString()));
        return PaymentResponse.from(p);
    }

    @GetMapping("/by-tab/{tabId}")
    public java.util.List<PaymentResponse> listByTab(@PathVariable String tabId) {
        return billingService.listByTab(tabId).stream()
                .map(PaymentResponse::from)
                .collect(java.util.stream.Collectors.toList());
    }

    // ========= Error mapping =========

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> badRequest(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError("BAD_REQUEST", ex.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiError> conflict(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiError("CONFLICT", ex.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiError> notFound(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError("NOT_FOUND", ex.getMessage(), LocalDateTime.now()));
    }

    // ========= DTOs (inline for brevity; move to interfaces.dto if you prefer) =========

    public static class PaymentRequest {
        @NotBlank public String tabId;
        @Min(1)  public int amountCents;
        @NotBlank public String method;          // "CARD" | "CASH"
        public String idempotencyKey;            // optional
        public Integer tipCents;                 // optional
    }

    public static class RefundRequest {
        @NotBlank public String reason;
    }

    public static class PaymentResponse {
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

    public static class ApiError {
        public String code;
        public String message;
        public LocalDateTime timestamp;

        public ApiError(String code, String message, LocalDateTime timestamp) {
            this.code = code;
            this.message = message;
            this.timestamp = timestamp;
        }
    }
}