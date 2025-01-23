package com.deskly.desklycore.reservation.domain.commands;

import static com.deskly.desklycore.reservation.domain.ReservationProperty.FINISH_STATUS;

public final class ChangeFinishStatusCommand extends Command<Boolean> {

    public ChangeFinishStatusCommand(Boolean isFinished) {
        super(isFinished, FINISH_STATUS);
    }
}
