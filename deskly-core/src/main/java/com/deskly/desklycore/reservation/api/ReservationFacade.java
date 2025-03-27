package com.deskly.desklycore.reservation.api;

import com.deskly.desklycore.shared.language.ReservationId;

public interface ReservationFacade {

    ReservationRequestResult requestAReservation(ReservationProcessInput input);

    void cancel(ReservationId id);

    void confirm(ReservationId id);

    void releaseExpiredReservations();

    void startReservations();

    void finishReservations();

}
