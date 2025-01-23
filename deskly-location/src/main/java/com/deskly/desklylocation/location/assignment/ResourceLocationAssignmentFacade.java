package com.deskly.desklylocation.location.assignment;

import com.deskly.desklylocation.location.LocationId;
import com.deskly.desklylocation.location.resource.ResourceId;
import com.deskly.desklylocation.shared.publisher.EventsPublisher;

import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class ResourceLocationAssignmentFacade {

    private final AssignmentRepository repository;

    private final EventsPublisher publisher;

    public ResourceLocationAssignmentFacade(AssignmentRepository repository, EventsPublisher publisher) {
        this.repository = requireNonNull(repository);
        this.publisher = requireNonNull(publisher);
    }

    public Set<ResourceId> resourceAssignedToLocation(LocationId locationId) {
        return repository.findByLocationId(locationId);
    }

    public void assign(Set<ResourceId> resourcesIds, LocationId locationId) {
        List<Assignment> assignments = resourcesIds.stream()
                .map(resourceId -> new Assignment(resourceId, locationId))
                .toList();
        repository.saveAll(assignments);
        for (ResourceId resourceId : resourcesIds) {
            publisher.publish(new ResourceAssignedToLocation(resourceId, locationId));
        }
    }

    public void unassign(Set<ResourceId> resourceIds, LocationId locationId) {
        for (ResourceId resourceId : resourceIds) {
            unassign(resourceId, locationId);
        }
    }

    public void unassign(ResourceId resourceId, LocationId locationId) {
        if (repository.isResourceAssigned(resourceId, locationId)) {
            repository.deleteById(new LocationResource(locationId, resourceId));
            publisher.publish(new ResourceUnassignedFromLocation(resourceId, locationId));
        } else {
            throw new IllegalStateException("Can not unassign resource from location because assignment does not exist");
        }
    }

}
