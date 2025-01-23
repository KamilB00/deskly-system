package com.deskly.desklycore.reservation.domain.commands;

import static com.deskly.desklycore.reservation.domain.ReservationProperty.ACTIVITY_STATUS;

public final class ChangeActivityStatusCommand extends Command<Boolean> {

    public ChangeActivityStatusCommand(Boolean isActive) {
        super(isActive, ACTIVITY_STATUS);
    }
}
