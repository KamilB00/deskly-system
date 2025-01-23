package com.deskly.desklycore.availability.infrastructure;

import com.deskly.desklycore.shared.LocationId;
import com.deskly.desklycore.shared.ResourceId;
import com.deskly.desklycore.shared.ReceivedEvent;

import java.time.Instant;
import java.util.UUID;


public record ResourceAssignedToLocation(UUID eventId, ResourceId resourceId, LocationId locationId,
                                         Instant occurredAt) implements ReceivedEvent {

}
