package com.wastewise.routeservice.service.impl;

import com.wastewise.routeservice.dto.RouteCreationRequest;
import com.wastewise.routeservice.dto.RouteResponse;
import com.wastewise.routeservice.dto.RouteUpdateRequest;
import com.wastewise.routeservice.entity.Route;
import com.wastewise.routeservice.exception.custom.*;
import com.wastewise.routeservice.feign.ZoneClient;
import com.wastewise.routeservice.repository.RouteRepository;
import com.wastewise.routeservice.service.RouteService;
import com.wastewise.routeservice.util.RouteIdGenerator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation class for RouteService interface.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;
    private final RouteIdGenerator routeIdGenerator;
    private final ZoneClient zoneClient;

    /**
     * {@inheritDoc}
     */
    @Override
    public RouteResponse createRoute(RouteCreationRequest requestDto) {
        log.info("Creating route for zone: {}", requestDto.getZoneId());

        if (!zoneClient.existsByZoneId(requestDto.getZoneId())) {
            throw new ZoneNotFoundException(requestDto.getZoneId());
        }

        if (routeRepository.findByRouteNameAndZoneId(requestDto.getRouteName(), requestDto.getZoneId()).isPresent()) {
            throw new DuplicateRouteNameException(requestDto.getRouteName(), requestDto.getZoneId());
        }

        String routeId = routeIdGenerator.generateRouteId(requestDto.getZoneId());

        Route route = Route.builder()
                .routeId(routeId)
                .routeName(requestDto.getRouteName())
                .pickupPoints(requestDto.getPickupPoints())
                .zoneId(requestDto.getZoneId())
                .estimatedTime(requestDto.getEstimatedTime())
                .build();

        routeRepository.save(route);
        log.info("Route created with ID: {}", routeId);

        return RouteResponse.builder()
                .routeId(route.getRouteId())
                .routeName(route.getRouteName())
                .pickupPoints(route.getPickupPoints())
                .zoneId(route.getZoneId())
                .estimatedTime(route.getEstimatedTime())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RouteResponse updateRoute(String routeId, RouteUpdateRequest requestDto) {
        log.info("Updating route with ID: {}", routeId);

        Route existingRoute = routeRepository.findById(routeId)
                .orElseThrow(() -> new RouteNotFoundException(routeId));

        boolean isNameChanged = !existingRoute.getRouteName().equalsIgnoreCase(requestDto.getRouteName());
        boolean isPointsChanged = !existingRoute.getPickupPoints().equals(requestDto.getPickupPoints());
        boolean isTimeChanged = existingRoute.getEstimatedTime() != requestDto.getEstimatedTime();

        if (!isNameChanged && !isPointsChanged && !isTimeChanged) {
            throw new NoRouteChangesDetectedException(routeId);
        }

        if (isNameChanged &&
            routeRepository.findByRouteNameAndZoneId(requestDto.getRouteName(), existingRoute.getZoneId()).isPresent()) {
            throw new DuplicateRouteNameException(requestDto.getRouteName(), existingRoute.getZoneId());
        }

        existingRoute.setRouteName(requestDto.getRouteName());
        existingRoute.setPickupPoints(requestDto.getPickupPoints());
        existingRoute.setEstimatedTime(requestDto.getEstimatedTime());

        routeRepository.save(existingRoute);
        log.info("Route updated successfully: {}", routeId);

        return RouteResponse.builder()
                .routeId(existingRoute.getRouteId())
                .routeName(existingRoute.getRouteName())
                .pickupPoints(existingRoute.getPickupPoints())
                .zoneId(existingRoute.getZoneId())
                .estimatedTime(existingRoute.getEstimatedTime())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteRoute(String routeId) {
        log.info("Deleting route with ID: {}", routeId);

        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new RouteNotFoundException(routeId));

        routeRepository.delete(route);
        log.info("Route deleted successfully: {}", routeId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RouteResponse> getAllRoutes() {
        log.info("Fetching all routes");

        return routeRepository.findAll()
                .stream()
                .map(route -> RouteResponse.builder()
                        .routeId(route.getRouteId())
                        .routeName(route.getRouteName())
                        .pickupPoints(route.getPickupPoints())
                        .zoneId(route.getZoneId())
                        .estimatedTime(route.getEstimatedTime())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RouteResponse getRouteById(String routeId) {
        log.info("Fetching route with ID: {}", routeId);

        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new RouteNotFoundException(routeId));

        return RouteResponse.builder()
                .routeId(route.getRouteId())
                .routeName(route.getRouteName())
                .pickupPoints(route.getPickupPoints())
                .zoneId(route.getZoneId())
                .estimatedTime(route.getEstimatedTime())
                .build();
    }
    @Override
    public List<String> getRouteIdsByZoneId(String zoneId) {
        log.info("Fetching route IDs for zone ID: {}", zoneId);
        return routeRepository.findByZoneId(zoneId)
                .stream()
                .map(Route::getRouteId)
                .collect(Collectors.toList());
    }

}
