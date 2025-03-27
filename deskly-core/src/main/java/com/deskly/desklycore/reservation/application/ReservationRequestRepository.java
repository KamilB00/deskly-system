package com.deskly.desklycore.reservation.application;

import com.deskly.desklycore.reservation.infrastructure.ReservationRequestHibernate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ReservationRequestRepository extends JpaRepository<ReservationRequestHibernate, UUID> {

    @Query("SELECT r FROM ReservationRequestHibernate r WHERE r.expirationTime < :now")
    List<ReservationRequestHibernate> getAllExpiredRequests(@Param("now") LocalDateTime now);

    @Query("SELECT r FROM ReservationRequestHibernate r WHERE r.reservationId = :reservationId")
    ReservationRequestHibernate findByReservationId(@Param("reservationId") UUID reservationId);

    @Query("SELECT r FROM ReservationRequestHibernate r WHERE r.requestId = :requestId")
    ReservationRequestHibernate findByRequestId(@Param("requestId") UUID requestId);

    @Modifying
    @Query("DELETE FROM ReservationRequestHibernate r WHERE r.reservationId = :reservationId")
    void deleteByReservationId(@Param("reservationId") UUID reservationId);

    @Modifying
    @Query("DELETE FROM ReservationRequestHibernate r WHERE r.reservationId IN :reservationIds")
    void deleteAllByReservationId(@Param("reservationIds") Set<UUID> reservationIds);
}
