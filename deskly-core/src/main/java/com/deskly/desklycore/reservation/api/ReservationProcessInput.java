package com.deskly.desklycore.reservation.api;

import com.deskly.desklycore.shared.language.Party;
import com.deskly.desklycore.shared.language.PartyId;

public record ReservationProcessInput(Party party, ResourceReservationInput reservation) {

    public PartyId getPartyId() {
        return party.id();
    }
}
