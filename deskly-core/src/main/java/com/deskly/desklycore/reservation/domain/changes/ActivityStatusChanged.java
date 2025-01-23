package com.deskly.desklycore.reservation.domain.changes;

import com.deskly.desklycore.reservation.domain.ReservationProperty;
import com.deskly.desklycore.reservation.domain.ReservationVersion;
import jakarta.annotation.Nullable;

import java.time.LocalDateTime;

import static com.deskly.desklycore.reservation.domain.ReservationProperty.ACTIVITY_STATUS;

public final class ActivityStatusChanged extends ReservationChange<Boolean> {
    public ActivityStatusChanged(@Nullable Boolean oldValue, Boolean newValue, ReservationVersion version) {
        super(oldValue, newValue, ACTIVITY_STATUS, version);
    }

    public ActivityStatusChanged(Boolean newValue, ReservationProperty property, LocalDateTime occurredAt, ReservationVersion version) {
        super(null, newValue, property, occurredAt, version);
    }
}
