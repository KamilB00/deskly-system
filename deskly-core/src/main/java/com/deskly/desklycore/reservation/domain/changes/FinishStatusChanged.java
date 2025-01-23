package com.deskly.desklycore.reservation.domain.changes;

import com.deskly.desklycore.reservation.domain.ReservationProperty;
import com.deskly.desklycore.reservation.domain.ReservationVersion;
import jakarta.annotation.Nullable;

import java.time.LocalDateTime;

import static com.deskly.desklycore.reservation.domain.ReservationProperty.FINISH_STATUS;

public final class FinishStatusChanged extends ReservationChange<Boolean> {

    public FinishStatusChanged(@Nullable Boolean oldValue, Boolean newValue, ReservationVersion version) {
        super(oldValue, newValue, FINISH_STATUS, version);
    }

    public FinishStatusChanged(Boolean newValue, ReservationProperty property, LocalDateTime occurredAt, ReservationVersion version) {
        super(null, newValue, property, occurredAt, version);
    }
}
