package com.deskly.desklycore.reservation.domain;

import java.util.UUID;

public class OwnerId {

    private UUID ownerId;

    public OwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public OwnerId() {
    }

    public static OwnerId none() {
        return new OwnerId(null);
    }

    public static OwnerId of(String id) {
        if (id == null) {
            return none();
        }
        return new OwnerId(UUID.fromString(id));
    }

    public UUID id() {
        return ownerId;
    }
}
