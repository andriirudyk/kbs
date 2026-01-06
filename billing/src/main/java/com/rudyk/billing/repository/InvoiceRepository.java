package com.rudyk.billing.repository;


import com.rudyk.billing.domain.model.Invoice;
import com.rudyk.billing.domain.model.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String> {

    // Find most recent invoice for a tab
    Optional<Invoice> findTopByTabIdOrderByIssuedAtDesc(String tabId);

    // Find all invoices for a tab
    List<Invoice> findByTabId(String tabId);

    // Find invoices by status
    List<Invoice> findByStatus(InvoiceStatus status);

    // Find invoices for a tab by status
    List<Invoice> findByTabIdAndStatus(String tabId, InvoiceStatus status);

    // Find invoices issued within a time window
    List<Invoice> findByIssuedAtBetween(LocalDateTime from, LocalDateTime to);

    // Optional: unpaid/pending before a due time (for dunning or cleanup)
    List<Invoice> findByStatusAndDueAtBefore(InvoiceStatus status, LocalDateTime dueBefore);
}