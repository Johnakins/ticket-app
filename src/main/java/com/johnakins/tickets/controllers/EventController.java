package com.johnakins.tickets.controllers;

import com.johnakins.tickets.domain.dtos.event.CreateEventRequest;
import com.johnakins.tickets.domain.dtos.event.CreateEventRequestDto;
import com.johnakins.tickets.domain.dtos.event.CreateEventResponseDto;
import com.johnakins.tickets.domain.dtos.updateEvent.UpdateEventRequest;
import com.johnakins.tickets.domain.dtos.updateEvent.UpdateEventRequestDto;
import com.johnakins.tickets.domain.dtos.updateEvent.UpdateEventResponseDto;
import com.johnakins.tickets.domain.entity.Event;
import com.johnakins.tickets.mappers.EventMapper;
import com.johnakins.tickets.services.EventService;
import com.johnakins.tickets.utils.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @PutMapping("/{eventId}")
    public ResponseEntity<UpdateEventResponseDto> updateEvent(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable UUID eventId,
            @Valid @RequestBody UpdateEventRequestDto updateEventRequestDto){

        UpdateEventRequest updateEventRequest = eventMapper.fromDto(updateEventRequestDto);

        String token = authHeader.substring(7);
        UUID organizerId = jwtUtil.getUserIdFromToken(token);
        Event updatedEvent = eventService.updateEventForOrganizer(organizerId, eventId, updateEventRequest);
        //Response object dto
        UpdateEventResponseDto updateEventResponseDto = eventMapper.toUpdateDto(updatedEvent);
        return ResponseEntity.status(HttpStatus.CREATED).body(updateEventResponseDto);
    }

    @GetMapping
    public ResponseEntity<Page<CreateEventResponseDto>> listEvents(
            @RequestHeader("Authorization") String authHeader, Pageable pageable){
        String token = authHeader.substring(7);
        UUID organizerId = jwtUtil.getUserIdFromToken(token);

        Page<CreateEventResponseDto> listEvents = eventService.listEventsForOrganizer(organizerId, pageable)
                .map(eventMapper::toDto);
        //Page<CreateEventResponseDto> listEvents = events.map(event -> eventMapper.toDto(event));

        return ResponseEntity.status(HttpStatus.CREATED).body(listEvents);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<CreateEventResponseDto> getEvent(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable UUID eventId,
            Pageable pageable){
        String token = authHeader.substring(7);
        UUID organizerId = jwtUtil.getUserIdFromToken(token);

//        Event event = eventService.getEventForOrganizer(organizerId, eventId)
//                .orElseThrow(() -> new EventNotFoundException(eventId));
//        CreateEventResponseDto getEvent = eventMapper.toDto(event);
//        return ResponseEntity.status(HttpStatus.CREATED).body(getEvent);

        return eventService.getEventForOrganizer(organizerId, eventId)
                .map(eventMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable UUID eventId
    ) {
        String token = authHeader.substring(7);
        UUID organizerId = jwtUtil.getUserIdFromToken(token);

        eventService.deleteEventForOrganizer(organizerId, eventId);
        return ResponseEntity.noContent().build();
    }

}