package com.johnakins.tickets.repositories;

import com.johnakins.tickets.domain.entity.QrCode;
import com.johnakins.tickets.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QrCodeRepository extends JpaRepository<QrCode, UUID> {
}
