package com.deskly.desklycore.reservation.domain.commands;

import static com.deskly.desklycore.reservation.domain.ReservationProperty.CANCELLATION_STATUS;

public final class ChangeCancellationStatusCommand extends Command<Boolean> {

    public ChangeCancellationStatusCommand(Boolean isCancelled) {
        super(isCancelled, CANCELLATION_STATUS);
    }
}
