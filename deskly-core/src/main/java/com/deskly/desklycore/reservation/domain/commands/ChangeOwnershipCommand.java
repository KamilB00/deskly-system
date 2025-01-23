package com.deskly.desklycore.reservation.domain.commands;

import com.deskly.desklycore.reservation.domain.Owner;

import static com.deskly.desklycore.reservation.domain.ReservationProperty.OWNERSHIP;

public class ChangeOwnershipCommand extends Command<Owner> {

    public ChangeOwnershipCommand(Owner owner) {
        super(owner, OWNERSHIP);
    }
}
