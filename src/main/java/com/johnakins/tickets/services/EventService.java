package com.johnakins.tickets.services;

import com.johnakins.tickets.domain.dtos.CreateEvent.CreateEventRequest;
import com.johnakins.tickets.domain.entity.Event;

import java.util.UUID;

public interface EventService {
    Event createEvent(UUID organizerId, CreateEventRequest event);
}
