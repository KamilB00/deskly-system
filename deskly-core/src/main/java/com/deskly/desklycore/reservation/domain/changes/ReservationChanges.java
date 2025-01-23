package com.deskly.desklycore.reservation.domain.changes;

import com.deskly.desklycore.shared.ReservationId;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Getter
public class ReservationChanges {

    private final List<ReservationChange<?>> changes = new ArrayList<>();

    private final ReservationId reservationId;

    private ReservationChanges(ReservationId reservationId) {
        this.reservationId = requireNonNull(reservationId);
    }

    public static ReservationChanges empty(ReservationId reservationId) {
        return new ReservationChanges(reservationId);
    }

    public boolean appendIfChanged(ReservationChange<?> change) {
        if (change.isChanged()) {
            changes.add(change);
            return true;
        }
        return false;
    }

}
