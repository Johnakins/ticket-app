package com.johnakins.tickets.controllers;

import com.johnakins.tickets.domain.dtos.createEvent.CreateEventRequest;
import com.johnakins.tickets.domain.dtos.createEvent.CreateEventRequestDto;
import com.johnakins.tickets.domain.dtos.createEvent.CreateEventResponseDto;
import com.johnakins.tickets.domain.entity.Event;
import com.johnakins.tickets.mappers.EventMapper;
import com.johnakins.tickets.services.EventService;
import com.johnakins.tickets.utils.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final EventMapper eventMapper;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<CreateEventResponseDto> createEvent(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody CreateEventRequestDto createEventRequestDto){

        CreateEventRequest createEventRequest = eventMapper.fromDto(createEventRequestDto);

        String token = authHeader.substring(7);
        UUID organizerId = jwtUtil.getUserIdFromToken(token);
        Event createdEvent = eventService.createEvent(organizerId , createEventRequest);
        //Response object dto
        CreateEventResponseDto createEventResponseDto = eventMapper.toDto(createdEvent);
        return ResponseEntity.status(HttpStatus.CREATED).body(createEventResponseDto);
    }
}