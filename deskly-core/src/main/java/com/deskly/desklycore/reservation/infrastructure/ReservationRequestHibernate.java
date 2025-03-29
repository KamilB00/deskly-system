package com.deskly.desklycore.reservation.infrastructure;

import com.deskly.desklycore.reservation.domain.ReservationRequest;
import com.deskly.desklycore.shared.language.RequestId;
import com.deskly.desklycore.shared.language.ReservationId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
public class ReservationRequestHibernate {

    @Id
    private UUID requestId;

    @Column(nullable = false, unique = true)
    private UUID reservationId;

    @Column(nullable = false)
    private LocalDateTime expirationTime;

    public ReservationRequestHibernate(ReservationRequest request) {
        this.requestId = request.id().id();
        this.reservationId = request.reservationId().id();
        this.expirationTime = request.expirationTime();
    }

    public ReservationRequestHibernate() {
    }

    public ReservationRequest toDomain() {
        return new ReservationRequest(RequestId.of(requestId), ReservationId.of(reservationId), expirationTime);
    }
}
