package com.johnakins.tickets.exceptions.eventTicketExceptions;

import com.johnakins.tickets.exceptions.EventTicketException;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class UserNotFoundException extends EventTicketException {

    public UserNotFoundException(UUID id) {
        super("User with ID" + id + "was not found",
                HttpStatus.NOT_FOUND);
    }
}
