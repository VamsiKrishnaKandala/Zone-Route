package com.wastewise.zoneservice.exception.custom;

/**
 * Exception thrown when a zone name already exists.
 */
public class DuplicateZoneNameException extends RuntimeException {

    public DuplicateZoneNameException(String zoneName) {
        super("Zone with name '" + zoneName + "' already exists.");
    }
}
