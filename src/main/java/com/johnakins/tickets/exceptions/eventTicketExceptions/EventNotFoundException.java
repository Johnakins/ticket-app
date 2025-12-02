package com.johnakins.tickets.exceptions.eventTicketExceptions;

import com.johnakins.tickets.exceptions.EventTicketException;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class EventNotFoundException extends EventTicketException {

    public EventNotFoundException(UUID id) {
        super("Event with ID" + id + "was not found",
                HttpStatus.NOT_FOUND);
    }
}
