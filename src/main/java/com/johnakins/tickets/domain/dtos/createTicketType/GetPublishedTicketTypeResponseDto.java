package com.johnakins.tickets.domain.dtos.createTicketType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class GetPublishedTicketTypeResponseDto {

    private UUID id;
    private String name;
    private Double price;
    private String description;
}
