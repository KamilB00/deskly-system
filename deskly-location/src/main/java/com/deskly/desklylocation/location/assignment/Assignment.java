package com.deskly.desklylocation.location.assignment;

import com.deskly.desklylocation.location.LocationId;
import com.deskly.desklylocation.location.resource.ResourceId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity(name = "resource_location_assignments")
public class Assignment {

    @EmbeddedId
    LocationResource locationResource;

    public Assignment(LocationResource locationResource) {
        this.locationResource = locationResource;
    }

    public Assignment(ResourceId resourceId, LocationId locationId) {
        this.locationResource = new LocationResource(locationId, resourceId);
    }

    public Assignment() {

    }

    public LocationId locationId() {
        return locationResource.getLocationId();
    }

    public ResourceId resourceId() {
        return locationResource.getResourceId();
    }
}
