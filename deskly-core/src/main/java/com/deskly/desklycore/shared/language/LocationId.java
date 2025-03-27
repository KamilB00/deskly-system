package com.deskly.desklycore.shared.language;

import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.UUID;

@EqualsAndHashCode
public class LocationId implements Serializable {

    private final UUID locationId;


    public LocationId(UUID locationId) {
        this.locationId = locationId;
    }

    public static LocationId none() {
        return new LocationId(null);
    }

    public static LocationId of(String id) {
        if (id == null) {
            return none();
        }
        return new LocationId(UUID.fromString(id));
    }

    public UUID id() {
        return locationId;
    }
}
