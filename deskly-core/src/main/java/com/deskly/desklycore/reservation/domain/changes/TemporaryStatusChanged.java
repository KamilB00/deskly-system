package com.deskly.desklycore.reservation.domain.changes;

import com.deskly.desklycore.reservation.domain.ReservationProperty;
import com.deskly.desklycore.reservation.domain.ReservationVersion;
import jakarta.annotation.Nullable;

import java.time.LocalDateTime;

import static com.deskly.desklycore.reservation.domain.ReservationProperty.TEMPORARY_STATUS;

public final class TemporaryStatusChanged extends ReservationChange<Boolean> {
    public TemporaryStatusChanged(@Nullable Boolean oldValue, Boolean newValue, ReservationVersion version) {
        super(oldValue, newValue, TEMPORARY_STATUS, version);
    }

    public TemporaryStatusChanged(Boolean newValue, ReservationProperty property, LocalDateTime occurredAt, ReservationVersion version) {
        super(null, newValue, property, occurredAt, version);
    }
}
