package com.deskly.desklycore.reservation.application;

import com.deskly.desklycore.availability.domain.AvailabilityFacade;
import com.deskly.desklycore.availability.domain.Owner;
import com.deskly.desklycore.reservation.api.ReservationFacade;
import com.deskly.desklycore.reservation.api.ReservationProcessInput;
import com.deskly.desklycore.reservation.api.ReservationRequestResult;
import com.deskly.desklycore.reservation.domain.Reservation;
import com.deskly.desklycore.reservation.domain.ReservationException;
import com.deskly.desklycore.reservation.domain.ReservationRequest;
import com.deskly.desklycore.reservation.domain.Status;
import com.deskly.desklycore.reservation.infrastructure.ReservationHibernate;
import com.deskly.desklycore.reservation.infrastructure.ReservationRequestHibernate;
import com.deskly.desklycore.shared.language.ReservationId;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService implements ReservationFacade {

    private final ReservationRequestRepository reservationRequestRepository;

    private final ReservationRepository reservationRepository;

    private final AvailabilityFacade availabilityFacade;

    @Override
    public ReservationRequestResult requestAReservation(ReservationProcessInput input) {
        boolean isSuccessful = availabilityFacade.block(input.reservation().resourceId(), input.reservation().getTimeSlot(), Owner.of(input.getPartyId().id()));
        if (isSuccessful) {
            val reservation = new Reservation(
                    ReservationId.newOne(),
                    input.reservation().resourceId(),
                    input.reservation().resourceAvailabilityId(),
                    input.party(),
                    input.reservation().from(),
                    input.reservation().from());
            val request = ReservationRequest.fifteenMinutesRequest(reservation.getReservationId());
            val savedReservation = reservationRepository.save(new ReservationHibernate(reservation)).toDomain();
            val savedRequest = reservationRequestRepository.save(new ReservationRequestHibernate(request)).toDomain();
            return ReservationRequestResult.success(savedRequest.id(), savedReservation.getReservationId());
        }
        return ReservationRequestResult.failed("Request failed on resource block");
    }

    @Override
    public void cancel(ReservationId id) {
        val reservation = reservationRepository.findById(id.id())
                .orElseThrow(() -> new RuntimeException("Reservation not found")).toDomain();
        boolean isSuccessful = availabilityFacade.release(reservation.getResourceId(), reservation.getTimeSlot(), Owner.of(reservation.getPartyId().id()));
        if (isSuccessful) {
            reservationRequestRepository.deleteByReservationId(id.id());
            reservation.changeStatus(Status.CANCELLED);
            reservationRepository.save(new ReservationHibernate(reservation));
        } else {
            throw new ReservationException("Reservation with id: " + reservation.getReservationId() + " failed on resource block");
        }
    }

    @Override
    public void confirm(ReservationId id) {
        reservationRequestRepository.deleteByReservationId(id.id());
        val reservation = reservationRepository.findById(id.id())
                .orElseThrow(() -> new RuntimeException("Reservation not found")).toDomain();
        reservation.changeStatus(Status.CONFIRMED);
        reservationRepository.save(new ReservationHibernate(reservation));
    }

    @Override
    public void releaseExpiredReservations() {
        val expiredReservations = reservationRequestRepository.getAllExpiredRequests(LocalDateTime.now()).stream()
                .map(ReservationRequestHibernate::getRequestId)
                .collect(Collectors.toSet());

        val reservations = reservationRepository.findAllById(expiredReservations).stream()
                .map(ReservationHibernate::toDomain)
                .collect(Collectors.toSet());

        reservations.forEach(reservation -> reservation.changeStatus(Status.EXPIRED));
        val collectionToSave = reservations.stream()
                .map(ReservationHibernate::new)
                .collect(Collectors.toSet());
        reservationRepository.saveAll(collectionToSave);
        reservationRequestRepository.deleteAllByReservationId(expiredReservations);
    }

    @Override
    public void startReservations() {
        val reservationsThatShouldBeStarted = reservationRepository.findReservationsThatShouldBeStarted(Instant.now()).stream()
                .map(ReservationHibernate::toDomain)
                .collect(Collectors.toSet());

        if (!reservationsThatShouldBeStarted.isEmpty()) {
            reservationsThatShouldBeStarted.forEach(reservation -> reservation.changeStatus(Status.ACTIVE));
            val collectionToSave = reservationsThatShouldBeStarted.stream()
                    .map(ReservationHibernate::new)
                    .collect(Collectors.toSet());
            reservationRepository.saveAll(collectionToSave);
        }
    }

    @Override
    public void finishReservations() {
        val reservationsThatShouldBeFinished = reservationRepository.findReservationsThatShouldBeFinished(Instant.now()).stream()
                .map(ReservationHibernate::toDomain)
                .collect(Collectors.toSet());

        if (!reservationsThatShouldBeFinished.isEmpty()) {
            for (Reservation reservation : reservationsThatShouldBeFinished) {
                boolean isSuccessful = availabilityFacade.release(reservation.getResourceId(), reservation.getTimeSlot(), Owner.of(reservation.getPartyId().id()));
                if (isSuccessful) {
                    reservation.changeStatus(Status.FINISHED);
                } else {
                    throw new ReservationException("Reservation with id: " + reservation.getReservationId() + " failed on resource release");
                }
            }
            val collectionToSave = reservationsThatShouldBeFinished.stream()
                    .map(ReservationHibernate::new)
                    .collect(Collectors.toSet());
            reservationRepository.saveAll(collectionToSave);
        }
    }


}
