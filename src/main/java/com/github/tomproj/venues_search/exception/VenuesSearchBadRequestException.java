package com.github.tomproj.venues_search.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class VenuesSearchBadRequestException extends VenuesSearchException{

    private static final long serialVersionUID = 9136574031370635473L;

    public VenuesSearchBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public VenuesSearchBadRequestException(String message) {
        super(message);
    }

    
    
}
