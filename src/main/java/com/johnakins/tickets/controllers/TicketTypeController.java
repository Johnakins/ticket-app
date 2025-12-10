package com.johnakins.tickets.controllers;

import com.johnakins.tickets.services.TicketTypeService;
import com.johnakins.tickets.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events/{eventId}/ticket-types")
public class TicketTypeController {
    private final TicketTypeService ticketTypeService;
    private final JwtUtil jwtUtil;

    @PostMapping("/{ticketTypeId}/tickets")
    public ResponseEntity<Void> purchaseTicket(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable UUID ticketTypeId
    ){
        String token = authHeader.substring(7);
        UUID userId = jwtUtil.getUserIdFromToken(token);

        ticketTypeService.purchaseTicket(userId, ticketTypeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
