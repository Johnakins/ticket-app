package com.johnakins.tickets.exceptions.eventTicketExceptions;

import com.johnakins.tickets.exceptions.EventTicketException;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class QrCodeGenerationException extends EventTicketException {

    public QrCodeGenerationException() {
        super("Ticket with ID can not be found",
                HttpStatus.NOT_FOUND);
    }
    public QrCodeGenerationException(Exception ex) {
        super("Failed to generate QR code," + ex ,
                HttpStatus.NOT_FOUND);
    }
}
