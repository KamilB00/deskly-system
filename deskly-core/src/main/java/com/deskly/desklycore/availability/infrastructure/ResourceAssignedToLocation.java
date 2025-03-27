package com.deskly.desklycore.availability.infrastructure;

import com.deskly.desklycore.shared.language.LocationId;
import com.deskly.desklycore.shared.language.ResourceId;
import com.deskly.desklycore.shared.messaging.ReceivedEvent;

import java.time.Instant;
import java.util.UUID;


public record ResourceAssignedToLocation(UUID eventId, ResourceId resourceId, LocationId locationId,
                                         Instant occurredAt) implements ReceivedEvent {

}
