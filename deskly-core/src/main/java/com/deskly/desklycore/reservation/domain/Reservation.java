package com.deskly.desklycore.reservation.domain;

import com.deskly.desklycore.reservation.domain.changes.*;
import com.deskly.desklycore.reservation.domain.commands.*;
import com.deskly.desklycore.shared.ReservationId;
import com.deskly.desklycore.shared.ResourceId;
import com.deskly.desklycore.shared.TimeSlot;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.val;


@Getter
@EqualsAndHashCode
public final class Reservation {

    private final ReservationId reservationId;

    private final ReservationChanges reservationChanges;

    private final ResourceId resourceId;

    private Owner owner = Owner.none();

    private TimeSlot timeSlot = TimeSlot.empty();

    private boolean isActive = false;

    private boolean isTemporary = true;

    private boolean isCancelled = false;

    private boolean isFinished = false;

    private ReservationVersion version;

    public Reservation(ReservationId reservationId,
                       ResourceId resourceId,
                       TimeSlot timeSlot,
                       boolean isActive,
                       boolean isTemporary,
                       boolean isCancelled,
                       boolean isFinished,
                       ReservationVersion version) {
        this.reservationId = reservationId;
        this.reservationChanges = ReservationChanges.empty(reservationId);
        this.resourceId = resourceId;
        this.timeSlot = timeSlot;
        this.isActive = isActive;
        this.isTemporary = isTemporary;
        this.isCancelled = isCancelled;
        this.isFinished = isFinished;
        this.version = version;
    }

    public void change(ChangeActivityStatusCommand command) {
        val changed = reservationChanges.appendIfChanged(new ActivityStatusChanged(this.isActive, command.getValue(), version.getNext()));
        if (changed) {
            this.isActive = command.getValue();
            this.version = version.getNext();
        }
    }

    public void change(ChangeCancellationStatusCommand command) {
        val changed = reservationChanges.appendIfChanged(new CancellationStatusChanged(this.isCancelled, command.getValue(), version.getNext()));
        if (changed) {
            this.isCancelled = command.getValue();
            this.version = version.getNext();
        }
    }

    public void change(ChangeFinishStatusCommand command) {
        val changed = reservationChanges.appendIfChanged(new FinishStatusChanged(this.isFinished, command.getValue(), version.getNext()));
        if (changed) {
            this.isFinished = command.getValue();
            this.version = version.getNext();
        }
    }

    public void change(ChangeTemporaryStatusCommand command) {
        val changed = reservationChanges.appendIfChanged(new TemporaryStatusChanged(this.isTemporary, command.getValue(), version.getNext()));
        if (changed) {
            this.isTemporary = command.getValue();
            this.version = version.getNext();
        }
    }

    public void change(ChangeTimeSlotCommand command) {
        val changed = reservationChanges.appendIfChanged(new TimeSlotChanged(this.timeSlot, command.getValue(), version.getNext()));
        if (changed) {
            this.timeSlot = command.getValue();
            this.version = version.getNext();
        }
    }

    public void change(ChangeOwnershipCommand command) {
        val changed = reservationChanges.appendIfChanged(new OwnershipChanged(this.owner, command.getValue(), version.getNext()));
        if (changed) {
            this.owner = command.getValue();
            this.version = version.getNext();
        }
    }


}
