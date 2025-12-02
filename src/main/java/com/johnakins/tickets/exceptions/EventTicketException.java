package com.johnakins.tickets.exceptions;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

public class EventTicketException extends RuntimeException{

    private  final HttpStatus status;

    public EventTicketException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus(){
        return status;
    }

}
