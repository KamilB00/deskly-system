package com.deskly.desklycore.reservation.api;

import com.deskly.desklycore.shared.language.RequestId;
import com.deskly.desklycore.shared.language.ReservationId;


public record ReservationRequestResult(RequestId requestId, ReservationId reservationId, String errorMessage) {

    public static ReservationRequestResult success(RequestId requestId, ReservationId reservationId) {
        return new ReservationRequestResult(requestId, reservationId, null);
    }

    public static ReservationRequestResult failed(String errorMessage) {
        return new ReservationRequestResult(null, null, errorMessage);
    }

    boolean isSuccessful() {
        return errorMessage == null;
    }

}
