package com.johnakins.tickets.services;

import com.johnakins.tickets.domain.entity.Ticket;

import java.util.UUID;

public interface TicketTypeService {
    Ticket purchaseTicket(UUID userId, UUID ticketTypeId);
}
