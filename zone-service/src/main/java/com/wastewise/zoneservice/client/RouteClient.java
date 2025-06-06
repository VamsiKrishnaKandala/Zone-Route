package com.wastewise.zoneservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Feign client to communicate with Route Service for fetching routes assigned to a zone.
 */
@FeignClient(name = "ROUTE-SERVICE", fallback = RouteClientFallback.class)
public interface RouteClient {

    /**
     * Fetch all route IDs assigned to a given zone.
     *
     * @param zoneId the zone ID
     * @return list of route IDs assigned to the zone
     */
    @GetMapping("/wastewise/admin/routes/zone/{zoneId}")
    List<String> getRoutesByZoneId(@PathVariable("zoneId") String zoneId);
}
