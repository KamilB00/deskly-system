package com.deskly.desklycore.shared;

import java.util.UUID;

public class ReservationId {

    private UUID reservationId;

    public ReservationId(UUID reservationId) {
        this.reservationId = reservationId;
    }

    public ReservationId() {
    }

    public static ReservationId none() {
        return new ReservationId(null);
    }

    public static ReservationId of(String id) {
        if (id == null) {
            return none();
        }
        return new ReservationId(UUID.fromString(id));
    }

    public UUID id() {
        return reservationId;
    }
}
