package com.deskly.desklycore.availability.domain;


import com.deskly.desklycore.shared.PublishedEvent;
import com.deskly.desklycore.shared.ResourceId;
import com.deskly.desklycore.shared.TimeSlot;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record ResourceTakenOver(UUID eventId,
                                ResourceId resourceId,
                                Set<Owner> previousOwners,
                                TimeSlot slot,
                                Instant occurredAt) implements PublishedEvent {

    public ResourceTakenOver(ResourceId resourceId, Set<Owner> previousOwners, TimeSlot slot, Instant occuredAt) {
        this(UUID.randomUUID(), resourceId, previousOwners, slot, occuredAt);
    }
}
