package com.wastewise.routeservice.exception;

/**
 * Global exception handler for the Route Service.
 */
import com.wastewise.routeservice.exception.custom.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RouteNotFoundException.class)
    public ResponseEntity<?> handleNotFound(RouteNotFoundException ex) {
        return ResponseEntity.status(404).body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(DuplicateRouteNameException.class)
    public ResponseEntity<?> handleConflict(DuplicateRouteNameException ex) {
        return ResponseEntity.status(409).body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(NoRouteChangesDetectedException.class)
    public ResponseEntity<Map<String, String>> handleNoChanges(NoRouteChangesDetectedException ex) {
        return ResponseEntity.status(409).body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(ZoneNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleZoneNotFound(ZoneNotFoundException ex) {
        return ResponseEntity.status(404).body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(InvalidRouteDetailsException.class)
    public ResponseEntity<?> handleInvalidDetails(InvalidRouteDetailsException ex) {
        return ResponseEntity.status(400).body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleOther(Exception ex) {
        return ResponseEntity.status(500).body(Map.of("message", "Internal error: " + ex.getMessage()));
    }
}
