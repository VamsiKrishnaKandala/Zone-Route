package com.wastewise.routeservice.exception.custom;

/**
 * Exception thrown when a duplicate route name is found within a zone.
 */
public class DuplicateRouteNameException extends RuntimeException {
    public DuplicateRouteNameException(String routeName, String zoneId) {
        super("Route with name '" + routeName + "' already exists in zone " + zoneId + ".");
    }
}
