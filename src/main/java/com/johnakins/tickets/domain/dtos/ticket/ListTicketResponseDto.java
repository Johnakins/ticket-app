package com.johnakins.tickets.domain.dtos.ticket;

import com.johnakins.tickets.domain.dtos.updateTicketType.ListTicketTicketTypeResponseDto;
import com.johnakins.tickets.domain.enums.TicketStatusEnum;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class ListTicketResponseDto {
    private UUID id;
    private TicketStatusEnum status;
    private ListTicketTicketTypeResponseDto ticketType;
}
