package com.deskly.desklycore.shared.language;

import java.util.UUID;

public class ReservationId {

    private final UUID reservationId;

    private ReservationId(UUID reservationId) {
        this.reservationId = reservationId;
    }

    public static ReservationId newOne(){
        return new ReservationId(UUID.randomUUID());
    }

    public static ReservationId of(UUID id) {
        return new ReservationId(id);
    }

    public UUID id() {
        return reservationId;
    }
}
