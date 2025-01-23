package com.deskly.desklylocation.location;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.UUID;

@EqualsAndHashCode
@Embeddable
public class LocationId implements Serializable {

    private UUID locationId;

    public LocationId(UUID locationId) {
        this.locationId = locationId;
    }

    public LocationId() {
    }

    public static LocationId newOne() {
        return new LocationId(UUID.randomUUID());
    }

    public UUID id() {
        return locationId;
    }
}
