package com.wastewise.routeservice.exception.custom;

/**
 * Exception thrown when no changes are detected during a route update.
 */
public class NoRouteChangesDetectedException extends RuntimeException {
    public NoRouteChangesDetectedException(String routeId) {
        super("No changes in the route details detected for route ID " + routeId);
    }
}
