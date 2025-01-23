package com.deskly.desklylocation.location.assignment;


import com.deskly.desklylocation.location.LocationId;
import com.deskly.desklylocation.location.resource.ResourceId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

interface AssignmentRepository extends JpaRepository<Assignment, LocationResource> {

    Optional<Assignment> findByLocationResource(LocationResource locationResource);

    default boolean isResourceAssigned(ResourceId resourceId, LocationId locationId) {
        return findByLocationResource(new LocationResource(locationId, resourceId)).isPresent();
    }

    default Set<ResourceId> findByLocationId(LocationId locationId) {
        return findAll().stream()
                .filter(assignment -> assignment.locationId().equals(locationId))
                .map(Assignment::resourceId)
                .collect(Collectors.toSet());
    }

}
