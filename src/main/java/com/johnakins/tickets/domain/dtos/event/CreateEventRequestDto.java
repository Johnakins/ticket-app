package com.johnakins.tickets.domain.dtos.event;

import com.johnakins.tickets.domain.dtos.createTicketType.CreateTicketTypeRequestDto;
import com.johnakins.tickets.domain.enums.EventStatusEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateEventRequestDto {

    @NotBlank(message = "Event name is required")
    private String name;

    private LocalDateTime start;

    private LocalDateTime end;

    @NotBlank(message = "Venue information is required")
    private String venue;

    private LocalDateTime salesStart;

    private LocalDateTime salesEnd;

    @NotNull(message = "Event status must be provided")
    private EventStatusEnum status;

    @NotEmpty(message = "At least one ticket type is required")
    @Valid
    private List<CreateTicketTypeRequestDto> ticketTypes;
}
