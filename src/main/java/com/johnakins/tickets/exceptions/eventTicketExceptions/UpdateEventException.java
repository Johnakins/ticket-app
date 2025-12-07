package com.johnakins.tickets.exceptions.eventTicketExceptions;

import com.johnakins.tickets.exceptions.EventTicketException;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class UpdateEventException extends EventTicketException {

    public UpdateEventException(UUID id) {
        super("Event with ID" + id + "can not null",
                HttpStatus.NOT_FOUND);
    }
}
