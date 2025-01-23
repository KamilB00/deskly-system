package com.deskly.desklylocation.location.resource;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.UUID;

@EqualsAndHashCode
@Embeddable
public class ResourceId implements Serializable {

    private UUID resourceId;

    public ResourceId(UUID resourceId) {
        this.resourceId = resourceId;
    }

    public ResourceId() {
    }

    public static ResourceId newOne() {
        return new ResourceId(UUID.randomUUID());
    }

    public UUID id() {
        return resourceId;
    }

}
