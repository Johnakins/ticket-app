package com.johnakins.tickets.exceptions.eventTicketExceptions;

import com.johnakins.tickets.exceptions.EventTicketException;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class TicketTypeNotFoundException extends EventTicketException {

    public TicketTypeNotFoundException(UUID id) {
        super("Ticket Type with ID" + id + "was not found",
                HttpStatus.NOT_FOUND);
    }
}
