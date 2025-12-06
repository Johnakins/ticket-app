package com.johnakins.tickets.domain.dtos.createTicketType;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
public class CreateTicketTypeResponseDto {

    private UUID id;
    private String name;
    private Double price;
    private String description;
    private Integer totalAvailable;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
