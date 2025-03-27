package com.deskly.desklycore.reservation.infrastructure;

import com.deskly.desklycore.availability.domain.ResourceAvailabilityId;
import com.deskly.desklycore.reservation.domain.Reservation;
import com.deskly.desklycore.reservation.domain.Status;
import com.deskly.desklycore.shared.language.*;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Entity
public class ReservationHibernate {

    @EmbeddedId
    private UUID reservationId;

    @Column(nullable = false)
    private UUID resourceId;

    @Column(nullable = false, unique = true)
    private UUID resourceAvailabilityId;

    @Column(nullable = false)
    private UUID partyId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PartyType partyType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    private Instant startTime;

    @Column(nullable = false)
    private Instant endTime;

    @Column(nullable = false, unique = true)
    private int version;

    public Reservation toDomain() {
        return new Reservation(
                ReservationId.of(reservationId),
                ResourceId.of(resourceId),
                new ResourceAvailabilityId(resourceAvailabilityId),
                new Party(new PartyId(partyId), partyType),
                startTime,
                endTime);
    }

    public ReservationHibernate(Reservation reservation) {
        this.reservationId = reservation.getReservationId().id();
        this.resourceId = reservation.getResourceId().getId();
        this.resourceAvailabilityId = reservation.getResourceAvailabilityId().id();
        this.partyId = reservation.getParty().id().id();
        this.partyType = reservation.getParty().type();
        this.status = reservation.getStatus();
        this.startTime = reservation.getStartTime();
        this.endTime = reservation.getEndTime();
        this.version = reservation.getVersion().getInternal();
    }

    public ReservationHibernate() {
    }
}
