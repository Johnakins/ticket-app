package com.johnakins.tickets.controllers;

import com.johnakins.tickets.domain.dtos.ticket.ListTicketResponseDto;
import com.johnakins.tickets.mappers.TicketMapper;
import com.johnakins.tickets.services.TicketService;
import com.johnakins.tickets.services.TicketTypeService;
import com.johnakins.tickets.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tickets")
public class TicketController {
    private final TicketService ticketService;
    private final TicketMapper ticketMapper;
    private final JwtUtil jwtUtil;

    @GetMapping
    public Page<ListTicketResponseDto> listTickets(
            @RequestHeader("Authorization") String authHeader,
            Pageable pageable
    ){
        String token = authHeader.substring(7);
        UUID userId = jwtUtil.getUserIdFromToken(token);

        return ticketService.listTicketForUser(userId, pageable)
                .map(ticketMapper::toListTicketResponseDto);
    }
}
