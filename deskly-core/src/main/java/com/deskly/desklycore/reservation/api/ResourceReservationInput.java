package com.deskly.desklycore.reservation.api;

import com.deskly.desklycore.availability.domain.ResourceAvailabilityId;
import com.deskly.desklycore.shared.language.ResourceId;
import com.deskly.desklycore.shared.language.TimeSlot;

import java.time.Instant;

public record ResourceReservationInput(Instant from, Instant to, ResourceAvailabilityId resourceAvailabilityId,
                                       ResourceId resourceId) {

    public TimeSlot getTimeSlot(){
        return new TimeSlot(Instant.from(from), Instant.from(to));
    }
}
