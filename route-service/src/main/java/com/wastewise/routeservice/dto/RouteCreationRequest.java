package com.wastewise.routeservice.dto;

import lombok.*;

/**
 * DTO for route creation requests.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteCreationRequest {
    private String zoneId;
    private String routeName;
    private String pickupPoints;
    private int estimatedTime;
}
