package com.johnakins.tickets.services.impl;

import com.johnakins.tickets.domain.entity.Ticket;
import com.johnakins.tickets.repositories.TicketRepository;
import com.johnakins.tickets.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;

    @Override
    public Page<Ticket> listTicketForUser(UUID userId, Pageable pageable) {
        return ticketRepository.findByPurchaserId(userId, pageable);
    }

    @Override
    public Optional<Ticket> getTicketForUser(UUID userId, UUID purchaserId) {
        return ticketRepository.findByIdAndPurchaserId(userId, purchaserId);
    }
}
