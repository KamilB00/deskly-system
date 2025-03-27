package com.deskly.desklycore.availability.infrastructure;

import com.deskly.desklycore.availability.domain.AvailabilityFacade;
import com.deskly.desklycore.shared.messaging.ReceivedEvent;
import com.deskly.desklycore.shared.language.TimeSlot;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
public class AvailabilityEventsHandler implements EventsHandler {

    private final Logger log = LoggerFactory.getLogger(AvailabilityEventsHandler.class);

    private final AvailabilityFacade availabilityFacade;

    @Override
    public Either<Failure, Success> handle(ReceivedEvent event) {

        if (event instanceof ResourceAssignedToLocation receivedEvent) {
            try {
                availabilityFacade.createResourceSlots(receivedEvent.resourceId(), TimeSlot.createDailyTimeSlotAtUTC(2025, 1, 1));
                return Either.right(new Success());

            } catch (Exception e) {
                return Either.left(new Failure(e.getMessage()));
            }
        } else if (event instanceof ResourceUnassignedFromLocation) {
            log.info("Resource Unassigned from Location event received !");
        }
        return Either.left(Failure.withReason("Unknown event type"));
    }
}
