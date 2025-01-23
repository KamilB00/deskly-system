package com.deskly.desklycore.reservation.domain.changes;

import com.deskly.desklycore.reservation.domain.Owner;
import com.deskly.desklycore.reservation.domain.ReservationProperty;
import com.deskly.desklycore.reservation.domain.ReservationVersion;
import jakarta.annotation.Nullable;

import java.time.LocalDateTime;

import static com.deskly.desklycore.reservation.domain.ReservationProperty.OWNERSHIP;

public class OwnershipChanged extends ReservationChange<Owner> {

    public OwnershipChanged(@Nullable Owner oldValue, Owner newValue, ReservationVersion version) {
        super(oldValue, newValue, OWNERSHIP, version);
    }

    public OwnershipChanged(Owner newValue, ReservationProperty property, LocalDateTime occurredAt, ReservationVersion version) {
        super(null, newValue, property, occurredAt, version);
    }
}
