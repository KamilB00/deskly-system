package com.deskly.desklycore.reservation.application;

import com.deskly.desklycore.reservation.infrastructure.ReservationHibernate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<ReservationHibernate, UUID> {

    @Query("SELECT r FROM ReservationHibernate r WHERE r.startTime <= :now AND r.endTime > :now AND r.status = 'CONFIRMED'")
    Set<ReservationHibernate> findReservationsThatShouldBeStarted(@Param("now") Instant now);

    @Query("SELECT r FROM ReservationHibernate r WHERE r.endTime <= :now AND r.status = 'ACTIVE'")
    Set<ReservationHibernate> findReservationsThatShouldBeFinished(@Param("now") Instant now);
}
