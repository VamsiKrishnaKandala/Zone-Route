package com.wastewise.routeservice.service;

import com.wastewise.routeservice.dto.RouteCreationRequest;
import com.wastewise.routeservice.dto.RouteUpdateRequest;
import com.wastewise.routeservice.dto.RouteResponse;
import java.util.List;

/**
 * Service interface for route operations.
 */
public interface RouteService {
    RouteResponse createRoute(RouteCreationRequest request);
    RouteResponse updateRoute(String routeId, RouteUpdateRequest request);
    void deleteRoute(String routeId);
    List<RouteResponse> getAllRoutes();
    RouteResponse getRouteById(String routeId);
    List<String> getRouteIdsByZoneId(String zoneId);

}