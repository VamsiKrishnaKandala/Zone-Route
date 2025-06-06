package com.wastewise.routeservice.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Entity representing a route.
 */
@Entity
@Table(name= "route")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Route {

	@Id
	@Column(name = "route_id", nullable = false, unique = true)
	private String routeId;
	
	@Column(name = "route_name", nullable = false)
	private String routeName;
	
	@Column(name = "zone_id", nullable = false)
	private String zoneId;
	
	@Column(name = "estimated_time", nullable = false)
	private int estimatedTime;
	
	@Column(name = "path_details", nullable = false)
	private String pickupPoints;
	
	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;
	
	@LastModifiedDate
	private LocalDateTime updatedAt;
}
