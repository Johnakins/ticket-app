package com.johnakins.tickets.domain.dtos.createEvent;

import com.johnakins.tickets.domain.dtos.createTicketType.GetPublishedTicketTypeResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class GetPublishedEventResponseDto {

    private UUID id;
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;
    private String venue;
    private List<GetPublishedTicketTypeResponseDto> ticketTypes = new ArrayList<>();
}