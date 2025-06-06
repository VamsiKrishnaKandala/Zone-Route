package com.wastewise.zoneservice.exception.custom;

import java.util.List;

/**
 * Exception thrown when zone deletion is forbidden due to assigned routes.
 */
public class ZoneDeletionException extends RuntimeException {

    private final List<String> assignedRoutes;

    public ZoneDeletionException(String zoneId, List<String> assignedRoutes) {
        super("Cannot delete zone " + zoneId + " because it has assigned routes: " + assignedRoutes);
        this.assignedRoutes = assignedRoutes;
    }

    public List<String> getAssignedRoutes() {
        return assignedRoutes;
    }
}
