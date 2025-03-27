package com.deskly.desklycore.shared.language;

import java.util.UUID;

public final class PartyId {

    private final UUID partyId;

    public PartyId(UUID partyId) {
        this.partyId = partyId;
    }

    public static PartyId none() {
        return new PartyId(null);
    }

    public UUID id() {
        return partyId;
    }
}
