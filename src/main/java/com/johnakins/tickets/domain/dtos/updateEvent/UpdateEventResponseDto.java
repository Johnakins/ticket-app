package com.johnakins.tickets.domain.dtos.updateEvent;

import com.johnakins.tickets.domain.dtos.createTicketType.CreateTicketTypeResponseDto;
import com.johnakins.tickets.domain.dtos.updateTicketType.UpdateTicketTypeResponseDto;
import com.johnakins.tickets.domain.enums.EventStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class UpdateEventResponseDto {

    private UUID id;
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;
    private String venue;
    private LocalDateTime salesStart;
    private LocalDateTime salesEnd;
    private EventStatusEnum status;
    private List<UpdateTicketTypeResponseDto> ticketTypes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}