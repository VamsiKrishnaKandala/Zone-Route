package com.wastewise.zoneservice.exception.custom;

/**
 * Exception thrown when no changes are detected during zone update.
 */
public class NoZoneChangesDetectedException extends RuntimeException {

    public NoZoneChangesDetectedException(String zoneId) {
        super("No changes in the zone details detected for zone ID " + zoneId);
    }
}
