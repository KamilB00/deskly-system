package com.deskly.desklycore.availability.infrastructure;

import com.deskly.desklycore.shared.messaging.ReceivedEvent;
import io.vavr.control.Either;

public interface EventsHandler {

    Either<Failure, Success> handle(ReceivedEvent event);

}
