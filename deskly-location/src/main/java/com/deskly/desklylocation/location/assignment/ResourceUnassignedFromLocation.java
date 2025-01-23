package com.deskly.desklylocation.location.assignment;

import com.deskly.desklylocation.location.LocationId;
import com.deskly.desklylocation.location.resource.ResourceId;
import com.deskly.desklylocation.shared.publisher.PublishedEvent;

import java.time.Instant;
import java.util.UUID;

public record ResourceUnassignedFromLocation(UUID eventId,
                                             ResourceId resourceId,
                                             LocationId locationId,
                                             Instant occurredAt) implements PublishedEvent {

    public ResourceUnassignedFromLocation(ResourceId resourceId, LocationId locationId) {
        this(UUID.randomUUID(), resourceId, locationId, Instant.now());
    }

}
