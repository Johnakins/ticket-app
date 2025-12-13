package com.johnakins.tickets.controllers;

import com.johnakins.tickets.domain.dtos.ticket.GetTicketResponseDto;
import com.johnakins.tickets.domain.dtos.ticket.ListTicketResponseDto;
import com.johnakins.tickets.mappers.TicketMapper;
import com.johnakins.tickets.services.QrCodeService;
import com.johnakins.tickets.services.TicketService;
import com.johnakins.tickets.services.TicketTypeService;
import com.johnakins.tickets.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tickets")
public class TicketController {
    private final TicketService ticketService;
    private final TicketMapper ticketMapper;
    private final QrCodeService qrCodeService;
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

    @GetMapping("/{ticketId}")
    public ResponseEntity<GetTicketResponseDto> getTicket(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable UUID ticketId
    ){
        String token = authHeader.substring(7);
        UUID userId = jwtUtil.getUserIdFromToken(token);

       return ticketService.getTicketForUser(userId, ticketId)
                .map(ticketMapper::toGetTicketResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{ticketId}/qr-codes")
    public ResponseEntity<byte[]> getTicketQrCode(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable UUID ticketId
    ) {
        String token = authHeader.substring(7);
        UUID userId = jwtUtil.getUserIdFromToken(token);

        byte[] qrCodeImage = qrCodeService.getQrCodeImageForUserAndTicket(userId, ticketId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(qrCodeImage.length);

        return ResponseEntity.ok()
                .headers(headers)
                .body(qrCodeImage);
    }
}
