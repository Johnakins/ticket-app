package com.johnakins.tickets.controllers;

import com.johnakins.tickets.exceptions.ErrorResponse;
import com.johnakins.tickets.exceptions.EventTicketException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {
    @ExceptionHandler(EventTicketException.class)
    public ResponseEntity<ErrorResponse> handleEventTicketException(EventTicketException ex){
        ErrorResponse response = new ErrorResponse(ex.getMessage(), ex.getStatus().value());

        return ResponseEntity.status(ex.getStatus()).body(response);
    }
}
