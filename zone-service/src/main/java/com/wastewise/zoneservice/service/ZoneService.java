package com.wastewise.zoneservice.service;

import com.wastewise.zoneservice.dto.ZoneCreationRequest;
import com.wastewise.zoneservice.dto.ZoneUpdateRequest;
import com.wastewise.zoneservice.entity.Zone;

import java.util.List;

/**
 * Service interface for Zone management.
 */
public interface ZoneService {

    Zone createZone(ZoneCreationRequest request);

    Zone updateZone(String zoneId, ZoneUpdateRequest request);

    void deleteZone(String zoneId);

    List<Zone> getAllZones();

    Zone getZoneById(String zoneId);

    boolean existsByZoneId(String zoneId);
}
