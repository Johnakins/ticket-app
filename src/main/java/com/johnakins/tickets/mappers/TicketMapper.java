package com.johnakins.tickets.mappers;

import com.johnakins.tickets.domain.dtos.ticket.ListTicketResponseDto;
import com.johnakins.tickets.domain.dtos.updateTicketType.ListTicketTicketTypeResponseDto;
import com.johnakins.tickets.domain.entity.Ticket;
import com.johnakins.tickets.domain.entity.TicketType;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {
    public ListTicketTicketTypeResponseDto tolistTicketTicketTypeResponseDto(TicketType ticketType){
        return new ListTicketTicketTypeResponseDto(
                ticketType.getId(),
                ticketType.getName(),
                ticketType.getPrice()
        );
    }

    public ListTicketResponseDto toListTicketResponseDto(Ticket ticket){
        return new ListTicketResponseDto(
                ticket.getId(),
                ticket.getStatus(),
                tolistTicketTicketTypeResponseDto(ticket.getTicketType())
        );
    }
}
