package com.deskly.desklycore.reservation.domain.commands;

import com.deskly.desklycore.shared.TimeSlot;

import static com.deskly.desklycore.reservation.domain.ReservationProperty.TIME_SLOT;

public final class ChangeTimeSlotCommand extends Command<TimeSlot> {

    public ChangeTimeSlotCommand(TimeSlot timeSlot) {
        super(timeSlot, TIME_SLOT);
    }
}
