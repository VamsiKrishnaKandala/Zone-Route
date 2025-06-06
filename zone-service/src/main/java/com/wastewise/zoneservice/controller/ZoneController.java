package com.wastewise.zoneservice.controller;

import com.wastewise.zoneservice.dto.ZoneCreationRequest;
import com.wastewise.zoneservice.dto.ZoneUpdateRequest;
import com.wastewise.zoneservice.entity.Zone;
import com.wastewise.zoneservice.service.ZoneService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST controller for Zone management APIs.
 */
@RestController
@RequestMapping("/wastewise/admin/zones")
@RequiredArgsConstructor
public class ZoneController {

    private final ZoneService zoneService;
    private static final Logger logger = LoggerFactory.getLogger(ZoneController.class);

    /**
     * Create a new zone.
     *
     * @param request zone creation data
     * @return Response with created zone ID
     */
    @PostMapping("/create")
    public ResponseEntity<?> createZone(@RequestBody ZoneCreationRequest request) {
        logger.info("Received request to create zone with name: {}", request.getZoneName());
        Zone created = zoneService.createZone(request);
        logger.info("Zone created successfully with ID: {}", created.getZoneId());
        return ResponseEntity.status(201).body(
                Map.of("message", "New zone created with ID", "zoneId", created.getZoneId())
        );
    }

    /**
     * Update an existing zone.
     *
     * @param zoneId  ID of zone to update
     * @param request update data
     * @return Response with updated zone details
     */
    @PutMapping("/update/{zoneId}")
    public ResponseEntity<?> updateZone(@PathVariable String zoneId,
                                        @RequestBody ZoneUpdateRequest request) {
        logger.info("Received request to update zone with ID: {}", zoneId);
        Zone updated = zoneService.updateZone(zoneId, request);
        logger.info("Zone updated successfully with ID: {}", updated.getZoneId());
        return ResponseEntity.ok(Map.of(
                "zoneId", updated.getZoneId(),
                "zoneName", updated.getZoneName(),
                "areaCoverage", updated.getAreaCoverage()
        ));
    }

    /**
     * Delete a zone by ID.
     *
     * @param zoneId ID of zone to delete
     * @return Response message
     */
    @DeleteMapping("/delete/{zoneId}")
    public ResponseEntity<?> deleteZone(@PathVariable String zoneId) {
        logger.info("Received request to delete zone with ID: {}", zoneId);
        zoneService.deleteZone(zoneId);
        logger.info("Zone deleted successfully with ID: {}", zoneId);
        return ResponseEntity.ok(Map.of("message", "Zone deleted successfully"));
    }

    /**
     * Get list of all zones.
     *
     * @return List of zones
     */
    @GetMapping("/list")
    public ResponseEntity<List<Zone>> getAllZones() {
        logger.info("Received request to fetch all zones");
        List<Zone> zones = zoneService.getAllZones();
        logger.info("Returning {} zones", zones.size());
        return ResponseEntity.ok(zones);
    }

    /**
     * Get a zone by ID.
     *
     * @param zoneId zone ID
     * @return Zone entity
     */
    @GetMapping("/{zoneId}")
    public ResponseEntity<Zone> getZoneById(@PathVariable String zoneId) {
        logger.info("Received request to fetch zone with ID: {}", zoneId);
        Zone zone = zoneService.getZoneById(zoneId);
        logger.info("Returning zone with ID: {}", zoneId);
        return ResponseEntity.ok(zone);
    }

    /**
     * Check if zone exists by ID.
     *
     * @param zoneId zone ID
     * @return true if exists, false otherwise
     */
    @GetMapping("/{zoneId}/exists")
    public boolean zoneExists(@PathVariable String zoneId) {
        logger.info("Checking existence of zone with ID: {}", zoneId);
        return zoneService.existsByZoneId(zoneId);
    }
}
