package com.johnakins.tickets.services;

import com.johnakins.tickets.domain.entity.QrCode;
import com.johnakins.tickets.domain.entity.Ticket;

import java.util.UUID;

public interface QrCodeService {

    QrCode generateQrCode(Ticket ticket);

    byte[] getQrCodeImageForUserAndTicket(UUID userId, UUID ticketId);
}
