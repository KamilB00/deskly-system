package com.deskly.desklycore.reservation.domain.changes;

import com.deskly.desklycore.reservation.domain.ReservationProperty;
import com.deskly.desklycore.reservation.domain.ReservationVersion;
import jakarta.annotation.Nullable;

import java.time.LocalDateTime;

import static com.deskly.desklycore.reservation.domain.ReservationProperty.CANCELLATION_STATUS;

public final class CancellationStatusChanged extends ReservationChange<Boolean> {
    public CancellationStatusChanged(@Nullable Boolean oldValue, Boolean newValue, ReservationVersion version) {
        super(oldValue, newValue, CANCELLATION_STATUS, version);
    }

    public CancellationStatusChanged(Boolean newValue, ReservationProperty property, LocalDateTime occurredAt, ReservationVersion version) {
        super(null, newValue, property, occurredAt, version);
    }
}
