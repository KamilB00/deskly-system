package com.deskly.desklycore.reservation.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ReservationVersion {

    private final int internal;

    public ReservationVersion() {
        this.internal = 0;
    }

    public ReservationVersion(int internal) {
        this.internal = internal;
    }

    public static ReservationVersion valueOf(int version) {
        return new ReservationVersion(version);
    }

    public ReservationVersion getNext() {
        return new ReservationVersion(internal + 1);
    }

}
