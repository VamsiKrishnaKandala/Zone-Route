package com.wastewise.zoneservice.service.impl;

import com.wastewise.zoneservice.client.RouteClient;
import com.wastewise.zoneservice.dto.ZoneCreationRequest;
import com.wastewise.zoneservice.dto.ZoneUpdateRequest;
import com.wastewise.zoneservice.entity.Zone;
import com.wastewise.zoneservice.exception.custom.*;
import com.wastewise.zoneservice.repository.ZoneRepository;
import com.wastewise.zoneservice.service.ZoneService;
import com.wastewise.zoneservice.util.ZoneIdGenerator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service implementation for Zone management.
 */
@Service
@RequiredArgsConstructor
public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepository zoneRepository;
    private final ZoneIdGenerator idGenerator;
    private final RouteClient routeClient;
    private static final Logger logger = LoggerFactory.getLogger(ZoneServiceImpl.class);

    @Override
    @Transactional
    public Zone createZone(ZoneCreationRequest request) {
        logger.info("Creating zone with name: {}", request.getZoneName());
        zoneRepository.findByZoneName(request.getZoneName())
                .ifPresent(z -> {
                    logger.error("Duplicate zone name: {}", request.getZoneName());
                    throw new DuplicateZoneNameException(request.getZoneName());
                });

        String zoneId = idGenerator.generateZoneId();

        Zone zone = Zone.builder()
                .zoneId(zoneId)
                .zoneName(request.getZoneName())
                .areaCoverage(request.getAreaCoverage())
                .build();
        Zone createdZone = zoneRepository.save(zone);
        logger.info("Zone created successfully with ID: {}", createdZone.getZoneId());
        return createdZone;
    }

    @Override
    @Transactional
    public Zone updateZone(String zoneId, ZoneUpdateRequest request) {
        logger.info("Updating zone with ID: {}", zoneId);
        Zone existing = zoneRepository.findById(zoneId)
                .orElseThrow(() -> {
                    logger.error("Zone not found with ID: {}", zoneId);
                    return new ZoneNotFoundException(zoneId);
                });

        boolean changed = false;

        if (!existing.getZoneName().equals(request.getZoneName())) {
            zoneRepository.findByZoneName(request.getZoneName()).ifPresent(z -> {
                logger.error("Duplicate zone name: {}", request.getZoneName());
                throw new DuplicateZoneNameException(request.getZoneName());
            });
            existing.setZoneName(request.getZoneName());
            changed = true;
        }

        if (!existing.getAreaCoverage().equals(request.getAreaCoverage())) {
            existing.setAreaCoverage(request.getAreaCoverage());
            changed = true;
        }

        if (!changed) {
            logger.warn("No changes detected during update for zone ID: {}", zoneId);
            throw new NoZoneChangesDetectedException(zoneId);
        }

        Zone updatedZone = zoneRepository.save(existing);
        logger.info("Zone updated successfully with ID: {}", updatedZone.getZoneId());
        return updatedZone;
    }

    @Override
    @Transactional
    public void deleteZone(String zoneId) {
        logger.info("Deleting zone with ID: {}", zoneId);
        Zone zone = zoneRepository.findById(zoneId)
                .orElseThrow(() -> {
                    logger.error("Zone not found with ID: {}", zoneId);
                    return new ZoneNotFoundException(zoneId);
                });

        List<String> assignedRoutes = routeClient.getRoutesByZoneId(zoneId);
        if (assignedRoutes != null && !assignedRoutes.isEmpty()) {
            logger.error("Zone deletion failed, assigned routes found: {}", assignedRoutes);
            throw new ZoneDeletionException(zoneId, assignedRoutes);
        }

        zoneRepository.delete(zone);
        logger.info("Zone deleted successfully with ID: {}", zoneId);
    }

    @Override
    public List<Zone> getAllZones() {
        logger.info("Fetching all zones");
        return zoneRepository.findAll();
    }

    @Override
    public Zone getZoneById(String zoneId) {
        logger.info("Fetching zone with ID: {}", zoneId);
        return zoneRepository.findById(zoneId)
                .orElseThrow(() -> new ZoneNotFoundException(zoneId));
    }

    @Override
    public boolean existsByZoneId(String zoneId) {
        logger.info("Checking existence of zone with ID: {}", zoneId);
        return zoneRepository.existsByZoneId(zoneId);
    }
}
