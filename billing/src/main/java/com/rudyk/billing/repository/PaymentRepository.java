package com.rudyk.billing.repository;


import com.rudyk.billing.domain.model.Payment;
import com.rudyk.billing.domain.model.PaymentStatus;
import com.rudyk.billing.domain.vo.PaymentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, PaymentId> {

    // Idempotency support
    Optional<Payment> findByIdempotencyKey(String idempotencyKey);
    boolean existsByIdempotencyKeyAndStatus(String idempotencyKey, PaymentStatus status);

    // Lookups by business reference
    List<Payment> findByTabIdOrderByOccurredAtDesc(String tabId);
    Optional<Payment> findTopByTabIdAndStatusOrderByOccurredAtDesc(String tabId, PaymentStatus status);

    // Status-based queries
    List<Payment> findByStatus(PaymentStatus status);
}