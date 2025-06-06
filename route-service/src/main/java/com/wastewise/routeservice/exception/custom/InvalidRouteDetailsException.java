package com.wastewise.routeservice.exception.custom;


/**
 * Exception thrown when route details are invalid.
 */
public class InvalidRouteDetailsException extends RuntimeException {
    public InvalidRouteDetailsException(String message) {
        super("Invalid route details: " + message);
    }
}
