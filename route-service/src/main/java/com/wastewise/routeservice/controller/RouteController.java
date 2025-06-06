package com.wastewise.routeservice.controller;

import com.wastewise.routeservice.dto.RouteCreationRequest;
import com.wastewise.routeservice.dto.RouteUpdateRequest;
import com.wastewise.routeservice.dto.RouteResponse;
import com.wastewise.routeservice.service.RouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST controller for managing routes.
 */
@RestController
@RequestMapping("/wastewise/admin/routes")
@RequiredArgsConstructor
@Slf4j
public class RouteController {

    private final RouteService routeService;

    /**
     * Creates a new route.
     *
     * @param request the route creation request
     * @return the created route response
     */
    @PostMapping("/create")
    public ResponseEntity<RouteResponse> createRoute(@RequestBody RouteCreationRequest request) {
        log.info("Received request to create route: {}", request);
        RouteResponse response = routeService.createRoute(request);
        return ResponseEntity.status(201).body(response);
    }

    /**
     * Updates an existing route.
     *
     * @param routeId the ID of the route to update
     * @param request the route update request
     * @return the updated route response
     */
    @PutMapping("/update/{routeId}")
    public ResponseEntity<RouteResponse> updateRoute(@PathVariable String routeId, @RequestBody RouteUpdateRequest request) {
        log.info("Received request to update route with ID: {}", routeId);
        RouteResponse response = routeService.updateRoute(routeId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Deletes a route.
     *
     * @param routeId the ID of the route to delete
     * @return no content
     */
    @DeleteMapping("/delete/{routeId}")
    public ResponseEntity<Void> deleteRoute(@PathVariable String routeId) {
        log.info("Received request to delete route with ID: {}", routeId);
        routeService.deleteRoute(routeId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves all routes.
     *
     * @return list of route responses
     */
    @GetMapping("/list")
    public ResponseEntity<List<RouteResponse>> getAllRoutes() {
        log.info("Received request to fetch all routes");
        List<RouteResponse> responses = routeService.getAllRoutes();
        return ResponseEntity.ok(responses);
    }

    /**
     * Retrieves a route by ID.
     *
     * @param routeId the ID of the route to retrieve
     * @return the route response
     */
    @GetMapping("/{routeId}")
    public ResponseEntity<RouteResponse> getRouteById(@PathVariable String routeId) {
        log.info("Received request to fetch route with ID: {}", routeId);
        RouteResponse response = routeService.getRouteById(routeId);
        return ResponseEntity.ok(response);
    }
    /**
     * Retrieves all route IDs for a given zone.
     *
     * @param zoneId the zone ID
     * @return list of route IDs
     */
    @GetMapping("/zone/{zoneId}")
    public ResponseEntity<List<String>> getRoutesByZoneId(@PathVariable String zoneId) {
        log.info("Received request to fetch route IDs for zone: {}", zoneId);
        List<String> routeIds = routeService.getRouteIdsByZoneId(zoneId);
        return ResponseEntity.ok(routeIds);
    }

}
