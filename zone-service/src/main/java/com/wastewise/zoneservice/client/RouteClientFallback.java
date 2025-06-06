package com.wastewise.zoneservice.client;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Fallback implementation for RouteClient Feign interface in case route service is down.
 */
@Component
public class RouteClientFallback implements RouteClient {
    @Override
    public List<String> getRoutesByZoneId(String zoneId) {
        // Returning empty list so deletion can proceed if routes cannot be verified
        return Collections.emptyList();
    }
}
