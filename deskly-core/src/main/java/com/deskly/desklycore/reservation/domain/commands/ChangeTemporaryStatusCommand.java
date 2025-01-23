package com.deskly.desklycore.reservation.domain.commands;

import static com.deskly.desklycore.reservation.domain.ReservationProperty.TEMPORARY_STATUS;

public final class ChangeTemporaryStatusCommand extends Command<Boolean> {

    public ChangeTemporaryStatusCommand(Boolean isTemporary) {
        super(isTemporary, TEMPORARY_STATUS);
    }
}
