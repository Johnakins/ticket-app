package com.johnakins.tickets.controllers;


import com.johnakins.tickets.domain.dtos.event.GetPublishedEventResponseDto;
import com.johnakins.tickets.domain.dtos.event.ListPublishedEventResponseDto;
import com.johnakins.tickets.domain.entity.Event;
import com.johnakins.tickets.mappers.EventMapper;
import com.johnakins.tickets.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/published-events")
public class PublishedEventController {
    private final EventMapper eventMapper;
    private final EventService eventService;

    @GetMapping
    public ResponseEntity<Page<ListPublishedEventResponseDto>> listPublishedEvents(
            Pageable pageable,
            @RequestParam(required = false) String q){

        Page<Event> events;
        if (null != q && !q.trim().isEmpty()){
            events = eventService.searchPublishedEvents(q, pageable);
        } else {
            events = eventService.listPublishedEvents(pageable);
        }

        return ResponseEntity.ok(events.map(eventMapper::toPublishedDto));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<GetPublishedEventResponseDto> getPublishedEvent(
            @PathVariable UUID eventId
    ){
        return eventService.getPublishedEvent(eventId)
                .map(eventMapper::toGetPublishedDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
