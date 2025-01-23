package com.deskly.desklycore.reservation.application;

public interface ReservationService {

    ReservationProcessResult reserve(ReservationProcessInput input);
}
