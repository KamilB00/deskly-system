package com.deskly.desklycore.reservation.domain.changes;

import com.deskly.desklycore.reservation.domain.ReservationProperty;
import com.deskly.desklycore.reservation.domain.ReservationVersion;
import com.deskly.desklycore.shared.TimeSlot;
import jakarta.annotation.Nullable;

import java.time.LocalDateTime;

import static com.deskly.desklycore.reservation.domain.ReservationProperty.TIME_SLOT;

public final class TimeSlotChanged extends ReservationChange<TimeSlot> {
    public TimeSlotChanged(@Nullable TimeSlot oldValue, TimeSlot newValue, ReservationVersion version) {
        super(oldValue, newValue, TIME_SLOT, version);
    }

    public TimeSlotChanged(TimeSlot newValue, ReservationProperty property, LocalDateTime occurredAt, ReservationVersion version) {
        super(null, newValue, property, occurredAt, version);
    }
}
