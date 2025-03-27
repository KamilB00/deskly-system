package com.deskly.desklycore.reservation.domain;

import com.deskly.desklycore.availability.domain.ResourceAvailabilityId;
import com.deskly.desklycore.shared.language.*;
import lombok.Getter;

import java.time.Instant;

import static java.util.Objects.requireNonNull;

@Getter
public class Reservation {

    private final ReservationId reservationId;

    private final ResourceId resourceId;

    private final ResourceAvailabilityId resourceAvailabilityId;

    private final Party party;

    private Status status = Status.PENDING;

    private final Instant startTime;

    private final Instant endTime;

    private ReservationVersion version;

    public Reservation(ReservationId reservationId,
                       ResourceId resourceId,
                       ResourceAvailabilityId resourceAvailabilityId,
                       Party party,
                       Instant startTime,
                       Instant endTime) {
        this.reservationId = requireNonNull(reservationId);
        this.resourceId = requireNonNull(resourceId);
        this.resourceAvailabilityId = requireNonNull(resourceAvailabilityId);
        this.party = requireNonNull(party);
        this.startTime = requireNonNull(startTime);
        this.endTime = requireNonNull(endTime);
        this.version = new ReservationVersion();
    }

    public void changeStatus(Status status) {
        this.status = status;
        this.version = version.getNext();
    }

    public TimeSlot getTimeSlot() {
        return new TimeSlot(startTime, endTime);
    }

    public PartyId getPartyId() {
        return party.id();
    }
}
