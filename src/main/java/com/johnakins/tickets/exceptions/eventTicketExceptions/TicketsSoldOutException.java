package com.johnakins.tickets.exceptions.eventTicketExceptions;

import com.johnakins.tickets.exceptions.EventTicketException;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class TicketsSoldOutException extends EventTicketException {

    public TicketsSoldOutException() {
        super("Ticket has been sold out",
                HttpStatus.NOT_FOUND);
    }
}
