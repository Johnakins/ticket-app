package com.johnakins.tickets.domain.dtos.updateTicketType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ListTicketTicketTypeResponseDto {

    private UUID id;
    private String name;
    private Double price;
}
