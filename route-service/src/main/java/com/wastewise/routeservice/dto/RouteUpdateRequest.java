package com.wastewise.routeservice.dto;

import lombok.*;

/**
 * DTO for route update requests.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RouteUpdateRequest {
    private String routeName;
    private String pickupPoints;
    private Integer estimatedTime;
}
