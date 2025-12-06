package com.johnakins.tickets.domain.dtos.createEvent;

import com.johnakins.tickets.domain.dtos.createTicketType.CreateTicketTypeResponseDto;
import com.johnakins.tickets.domain.enums.EventStatusEnum;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class CreateEventResponseDto {

    private UUID id;
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;
    private String venue;
    private LocalDateTime salesStart;
    private LocalDateTime salesEnd;
    private EventStatusEnum status;
    private List<CreateTicketTypeResponseDto> ticketTypes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}