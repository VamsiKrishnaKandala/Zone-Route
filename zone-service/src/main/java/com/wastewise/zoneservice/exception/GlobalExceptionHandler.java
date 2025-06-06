package com.wastewise.zoneservice.exception;

import com.wastewise.zoneservice.exception.custom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * Global exception handler for Zone Service REST APIs.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ZoneNotFoundException.class)
    public ResponseEntity<?> handleNotFound(ZoneNotFoundException ex) {
        logger.error("Zone not found error: {}", ex.getMessage());
        return ResponseEntity.status(404).body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(DuplicateZoneNameException.class)
    public ResponseEntity<?> handleConflict(DuplicateZoneNameException ex) {
        logger.error("Duplicate zone name error: {}", ex.getMessage());
        return ResponseEntity.status(409).body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(NoZoneChangesDetectedException.class)
    public ResponseEntity<?> handleNoChanges(NoZoneChangesDetectedException ex) {
        logger.warn("No changes detected: {}", ex.getMessage());
        return ResponseEntity.status(409).body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(ZoneDeletionException.class)
    public ResponseEntity<?> handleZoneDeletionException(ZoneDeletionException ex) {
        logger.error("Zone deletion error: {}", ex.getMessage());
        return ResponseEntity.status(400).body(Map.of(
                "message", ex.getMessage(),
                "assignedRoutes", ex.getAssignedRoutes()
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleOther(Exception ex) {
        logger.error("Internal server error: {}", ex.getMessage());
        return ResponseEntity.status(500).body(Map.of("message", "Internal error: " + ex.getMessage()));
    }
}
