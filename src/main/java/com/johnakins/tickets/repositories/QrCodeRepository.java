package com.johnakins.tickets.repositories;

import com.johnakins.tickets.domain.entity.QrCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface QrCodeRepository extends JpaRepository<QrCode, UUID> {

    @Query("""
    SELECT q FROM QrCode q
    WHERE q.ticket.id = :ticketId
      AND q.ticket.purchaser.id = :purchaserId
    """)
    Optional<QrCode> findByTicketIdAndTicketPurchaseId(UUID ticketId, UUID purchaserId);
}
