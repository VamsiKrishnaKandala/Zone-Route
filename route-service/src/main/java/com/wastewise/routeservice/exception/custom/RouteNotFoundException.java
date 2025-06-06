package com.wastewise.routeservice.exception.custom;

/**
 * Exception thrown when a route is not found.
 */
public class RouteNotFoundException extends RuntimeException{
	
	public RouteNotFoundException(String routeId) {
		super("Route with ID " + routeId + " not found.");
	}
}

