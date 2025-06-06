package com.wastewise.zoneservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for zone update request.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ZoneUpdateRequest {

    private String zoneName;
    private Long areaCoverage;
}
