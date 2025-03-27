package com.deskly.desklycore.availability.infrastructure;


import com.deskly.desklycore.shared.language.LocationId;
import com.deskly.desklycore.shared.messaging.ReceivedEvent;
import com.deskly.desklycore.shared.language.ResourceId;

import java.time.Instant;
import java.util.UUID;

public record ResourceUnassignedFromLocation(UUID eventId, ResourceId resourceId, LocationId locationId,
                                             Instant occurredAt) implements ReceivedEvent {

}
