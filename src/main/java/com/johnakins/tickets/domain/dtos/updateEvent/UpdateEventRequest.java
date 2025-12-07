package com.johnakins.tickets.domain.dtos.updateEvent;

import com.johnakins.tickets.domain.dtos.createTicketType.CreateTicketTypeRequest;
import com.johnakins.tickets.domain.dtos.updateTicketType.UpdateTicketTypeRequest;
import com.johnakins.tickets.domain.enums.EventStatusEnum;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventRequest {
    private UUID id;
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;
    private String venue;
    private LocalDateTime salesStart;
    private LocalDateTime salesEnd;
    private EventStatusEnum status;
    private List<UpdateTicketTypeRequest> ticketTypes = new ArrayList<>();
}