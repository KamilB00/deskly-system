package com.deskly.desklycore.availability.domain;


import com.deskly.desklycore.availability.domain.segment.Segments;
import com.deskly.desklycore.shared.EventsPublisher;
import com.deskly.desklycore.shared.ResourceId;
import com.deskly.desklycore.shared.TimeSlot;
import jakarta.transaction.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;

import static com.deskly.desklycore.availability.domain.segment.SegmentInMinutes.defaultSegment;


public class AvailabilityFacade {

    private final ResourceAvailabilityRepository availabilityRepository;
    private final ResourceAvailabilityReadModel availabilityReadModel;
    private final EventsPublisher eventsPublisher;
    private final Clock clock;

    public AvailabilityFacade(ResourceAvailabilityRepository availabilityRepository, ResourceAvailabilityReadModel availabilityReadModel, EventsPublisher eventsPublisher, Clock clock) {
        this.availabilityRepository = availabilityRepository;
        this.availabilityReadModel = availabilityReadModel;
        this.eventsPublisher = eventsPublisher;
        this.clock = clock;
    }

    public void createResourceSlots(ResourceId resourceId, TimeSlot timeslot) {
        ResourceGroupedAvailability groupedAvailability = ResourceGroupedAvailability.of(resourceId, timeslot);
        availabilityRepository.saveNew(groupedAvailability);
    }

    public void createResourceSlots(ResourceId resourceId, ResourceId parentId, TimeSlot timeslot) {
        ResourceGroupedAvailability groupedAvailability = ResourceGroupedAvailability.of(resourceId, timeslot, parentId);
        availabilityRepository.saveNew(groupedAvailability);
    }

    @Transactional
    public boolean block(ResourceId resourceId, TimeSlot timeSlot, Owner requester) {
        ResourceGroupedAvailability toBlock = findGrouped(resourceId, timeSlot);
        return block(requester, toBlock);
    }

    private boolean block(Owner requester, ResourceGroupedAvailability toBlock) {
        if (toBlock.hasNoSlots()) {
            return false;
        }
        boolean result = toBlock.block(requester);
        if (result) {
            return availabilityRepository.saveCheckingVersion(toBlock);
        }
        return result;
    }

    @Transactional
    public boolean release(ResourceId resourceId, TimeSlot timeSlot, Owner requester) {
        ResourceGroupedAvailability toRelease = findGrouped(resourceId, timeSlot);
        if (toRelease.hasNoSlots()) {
            return false;
        }
        boolean result = toRelease.release(requester);
        if (result) {
            return availabilityRepository.saveCheckingVersion(toRelease);
        }
        return result;
    }

    @Transactional
    public boolean disable(ResourceId resourceId, TimeSlot timeSlot, Owner requester) {
        ResourceGroupedAvailability toDisable = findGrouped(resourceId, timeSlot);
        if (toDisable.hasNoSlots()) {
            return false;
        }
        Set<Owner> previousOwners = toDisable.owners();
        boolean result = toDisable.disable(requester);
        if (result) {
            result = availabilityRepository.saveCheckingVersion(toDisable);
            if (result) {
                eventsPublisher.publish(new ResourceTakenOver(resourceId, previousOwners, timeSlot, Instant.now(clock)));
            }
        }
        return result;
    }

    @Transactional
    public Optional<ResourceId> blockRandomAvailable(Set<ResourceId> resourceIds, TimeSlot within, Owner owner) {
        TimeSlot normalized = Segments.normalizeToSegmentBoundaries(within, defaultSegment());
        ResourceGroupedAvailability groupedAvailability = availabilityRepository.loadAvailabilitiesOfRandomResourceWithin(resourceIds, normalized);
        if (block(owner, groupedAvailability)) {
            return groupedAvailability.resourceId();
        } else {
            return Optional.empty();
        }
    }

    public ResourceGroupedAvailability findGrouped(ResourceId resourceId, TimeSlot within) {
        TimeSlot normalized = Segments.normalizeToSegmentBoundaries(within, defaultSegment());
        return new ResourceGroupedAvailability(availabilityRepository.loadAllWithinSlot(resourceId, normalized));
    }

    public Calendar loadCalendar(ResourceId resourceId, TimeSlot within) {
        TimeSlot normalized = Segments.normalizeToSegmentBoundaries(within, defaultSegment());
        return availabilityReadModel.load(resourceId, normalized);
    }

    public Calendars loadCalendars(Set<ResourceId> resources, TimeSlot within) {
        TimeSlot normalized = Segments.normalizeToSegmentBoundaries(within, defaultSegment());
        return availabilityReadModel.loadAll(resources, normalized);
    }

    ResourceGroupedAvailability find(ResourceId resourceId, TimeSlot within) {
        TimeSlot normalized = Segments.normalizeToSegmentBoundaries(within, defaultSegment());
        return new ResourceGroupedAvailability(availabilityRepository.loadAllWithinSlot(resourceId, normalized));
    }

    ResourceGroupedAvailability findByParentId(ResourceId parentId, TimeSlot within) {
        TimeSlot normalized = Segments.normalizeToSegmentBoundaries(within, defaultSegment());
        return new ResourceGroupedAvailability(availabilityRepository.loadAllByParentIdWithinSlot(parentId, normalized));
    }


}


