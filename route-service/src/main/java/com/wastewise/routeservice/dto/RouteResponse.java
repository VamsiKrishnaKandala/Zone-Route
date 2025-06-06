package com.wastewise.routeservice.dto;

import lombok.*;

/**
 * DTO for route responses.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteResponse {
    private String routeId; // Add this field
    private String zoneId;
    private String routeName;
    private int estimatedTime;
    private String pickupPoints;
}
