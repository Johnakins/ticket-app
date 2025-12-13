package com.johnakins.tickets.services.impl;

import com.johnakins.tickets.domain.dtos.event.CreateEventRequest;
import com.johnakins.tickets.domain.dtos.updateEvent.UpdateEventRequest;
import com.johnakins.tickets.domain.dtos.updateTicketType.UpdateTicketTypeRequest;
import com.johnakins.tickets.domain.entity.Event;
import com.johnakins.tickets.domain.entity.TicketType;
import com.johnakins.tickets.domain.entity.User;
import com.johnakins.tickets.domain.enums.EventStatusEnum;
import com.johnakins.tickets.exceptions.eventTicketExceptions.EventNotFoundException;
import com.johnakins.tickets.exceptions.eventTicketExceptions.TicketTypeNotFoundException;
import com.johnakins.tickets.exceptions.eventTicketExceptions.UpdateEventException;
import com.johnakins.tickets.exceptions.eventTicketExceptions.UserNotFoundException;
import com.johnakins.tickets.repositories.EventRepository;
import com.johnakins.tickets.repositories.UserRepository;
import com.johnakins.tickets.services.EventService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public Event createEvent(UUID organizerId, CreateEventRequest event) {
        User organizer = userRepository.findById(organizerId)
                .orElseThrow(() -> new UserNotFoundException(organizerId));

        Event eventToCreate = new Event();

        List<TicketType> ticketTypesToCreate = event.getTicketTypes().stream().map(
                ticketType -> {
                    TicketType ticketTypeToCreate = new TicketType();
                    ticketTypeToCreate.setName(ticketType.getName());
                    ticketTypeToCreate.setPrice(ticketType.getPrice());
                    ticketTypeToCreate.setDescription(ticketType.getDescription());
                    ticketTypeToCreate.setTotalAvailable(ticketType.getTotalAvailable());
                    return ticketTypeToCreate;
                }).toList();

        eventToCreate.setName(event.getName());
        eventToCreate.setStart(event.getStart());
        eventToCreate.setEnd(event.getEnd());
        eventToCreate.setVenue(event.getVenue());
        eventToCreate.setSalesStart(event.getSalesStart());
        eventToCreate.setSalesEnd(event.getSalesEnd());
        eventToCreate.setStatus(event.getStatus());
        eventToCreate.setOrganizer(organizer);
        eventToCreate.setTicketTypes(ticketTypesToCreate);

        return eventRepository.save(eventToCreate);
    }

    @Override
    public Page<Event> listEventsForOrganizer(UUID organizerId, Pageable pageable) {
        return eventRepository.findByOrganizerId(organizerId, pageable);
    }

    @Override
    public Optional<Event> getEventForOrganizer(UUID organizerId, UUID id) {
        return eventRepository.findByIdAndOrganizerId(id, organizerId);
    }

    @Override
    @Transactional
    public Event updateEventForOrganizer(
            UUID organizerId,
            UUID id,
            UpdateEventRequest event
    ) {
        if (event.getId() == null || !event.getId().equals(id)) {
            throw new UpdateEventException(id);
        }

        Event existingEvent = eventRepository
                .findByIdAndOrganizerId(id, organizerId)
                .orElseThrow(() -> new EventNotFoundException(id));

        existingEvent.setName(event.getName());
        existingEvent.setStart(event.getStart());
        existingEvent.setEnd(event.getEnd());
        existingEvent.setVenue(event.getVenue());
        existingEvent.setSalesStart(event.getSalesStart());
        existingEvent.setSalesEnd(event.getSalesEnd());
        existingEvent.setStatus(event.getStatus());

        // IDs sent in request
        Set<UUID> requestTicketTypeIds = event.getTicketTypes().stream()
                .map(UpdateTicketTypeRequest::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // Index existing ticket types
        Map<UUID, TicketType> existingTicketTypesIndex =
                existingEvent.getTicketTypes().stream()
                        .collect(Collectors.toMap(TicketType::getId, Function.identity()));

        // Remove deleted ticket types
        existingEvent.getTicketTypes().removeIf(
                ticketType -> ticketType.getId() != null
                        && !requestTicketTypeIds.contains(ticketType.getId())
        );

        for (UpdateTicketTypeRequest ticketType : event.getTicketTypes()) {
            if (ticketType.getId() == null) {
                // Create
                TicketType newTicketType = new TicketType();
                newTicketType.setName(ticketType.getName());
                newTicketType.setPrice(ticketType.getPrice());
                newTicketType.setDescription(ticketType.getDescription());
                newTicketType.setTotalAvailable(ticketType.getTotalAvailable());
                newTicketType.setEvent(existingEvent);

                existingEvent.getTicketTypes().add(newTicketType);
            } else {
                // Update
                TicketType existingTicketType =
                        existingTicketTypesIndex.get(ticketType.getId());

                if (existingTicketType == null) {
                    throw new TicketTypeNotFoundException(ticketType.getId());
                }

                existingTicketType.setName(ticketType.getName());
                existingTicketType.setPrice(ticketType.getPrice());
                existingTicketType.setDescription(ticketType.getDescription());
                existingTicketType.setTotalAvailable(ticketType.getTotalAvailable());
            }
        }

        return existingEvent; // save not required if JPA dirty checking
    }


    @Override
    public void deleteEventForOrganizer(UUID organizerId, UUID id) {
        getEventForOrganizer(organizerId,id).ifPresent(eventRepository::delete);
    }

    @Override
    public Page<Event> listPublishedEvents(Pageable pageable) {
        return eventRepository.findByStatus(EventStatusEnum.PUBLISHED, pageable);
    }

    @Override
    public Page<Event> searchPublishedEvents(String query, Pageable pageable) {
        return eventRepository.searchEvents(query, pageable);
    }

    @Override
    public Optional<Event> getPublishedEvent(UUID id) {
        return eventRepository.findByIdAndStatus(id, EventStatusEnum.PUBLISHED);
    }
}
