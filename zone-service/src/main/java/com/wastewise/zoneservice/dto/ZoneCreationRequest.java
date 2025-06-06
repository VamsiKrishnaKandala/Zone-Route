package com.wastewise.zoneservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for zone creation request.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ZoneCreationRequest {

    private String zoneName;
    private Long areaCoverage;
}
