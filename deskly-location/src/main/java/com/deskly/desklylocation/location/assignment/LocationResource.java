package com.deskly.desklylocation.location.assignment;

import com.deskly.desklylocation.location.LocationId;
import com.deskly.desklylocation.location.resource.ResourceId;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

@EqualsAndHashCode
@Getter
@Embeddable
public class LocationResource implements Serializable {

    LocationId locationId;

    ResourceId resourceId;

    public LocationResource(LocationId locationId, ResourceId resourceId) {
        this.locationId = locationId;
        this.resourceId = resourceId;
    }

    public LocationResource() {

    }
}
