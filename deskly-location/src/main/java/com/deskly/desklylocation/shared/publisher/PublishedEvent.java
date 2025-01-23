package com.deskly.desklylocation.shared.publisher;

import java.time.Instant;

public interface PublishedEvent {

    Instant occurredAt();
}
