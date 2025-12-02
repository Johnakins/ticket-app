package com.johnakins.tickets.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private int status;
}
