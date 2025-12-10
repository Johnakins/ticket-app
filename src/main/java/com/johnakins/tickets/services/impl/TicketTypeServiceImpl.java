package com.johnakins.tickets.services.impl;

import com.johnakins.tickets.domain.entity.Ticket;
import com.johnakins.tickets.domain.entity.TicketType;
import com.johnakins.tickets.domain.entity.User;
import com.johnakins.tickets.domain.enums.TicketStatusEnum;
import com.johnakins.tickets.exceptions.eventTicketExceptions.TicketTypeNotFoundException;
import com.johnakins.tickets.exceptions.eventTicketExceptions.TicketsSoldOutException;
import com.johnakins.tickets.exceptions.eventTicketExceptions.UserNotFoundException;
import com.johnakins.tickets.repositories.TicketRepository;
import com.johnakins.tickets.repositories.TicketTypeRepository;
import com.johnakins.tickets.repositories.UserRepository;
import com.johnakins.tickets.services.QrCodeService;
import com.johnakins.tickets.services.TicketTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {
    private final UserRepository userRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final TicketRepository ticketRepository;
    private final QrCodeService qrCodeService;

    @Override
    @Transactional
    public Ticket purchaseTicket(UUID userId, UUID ticketTypeId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        TicketType ticketType = ticketTypeRepository.findByIdWithLock(ticketTypeId).orElseThrow(() -> new TicketTypeNotFoundException(ticketTypeId));

        int purchasedTickets = ticketRepository.countByTicketTypeId(ticketType.getId());
        Integer totalAvailable = ticketType.getTotalAvailable();

        if(purchasedTickets + 1 > totalAvailable) {
            throw new TicketsSoldOutException();
        }

        Ticket ticket = new Ticket();
        ticket.setStatus(TicketStatusEnum.PURCHASED);
        ticket.setTicketType(ticketType);
        ticket.setPurchaser(user);

        Ticket savedTicket = ticketRepository.save(ticket);
        qrCodeService.generateQrCode(savedTicket);

        return ticketRepository.save(savedTicket);
    }
}
