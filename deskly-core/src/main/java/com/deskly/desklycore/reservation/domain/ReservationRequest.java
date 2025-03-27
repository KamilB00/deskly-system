package com.deskly.desklycore.reservation.domain;

import com.deskly.desklycore.shared.language.RequestId;
import com.deskly.desklycore.shared.language.ReservationId;

import java.time.LocalDateTime;

public record ReservationRequest(RequestId id, ReservationId reservationId, LocalDateTime expirationTime) {

    public static ReservationRequest fifteenMinutesRequest(ReservationId reservationId) {
        return new ReservationRequest(RequestId.newOne(), reservationId, LocalDateTime.now().plusMinutes(15));
    }

}
