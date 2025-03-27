package com.deskly.desklycore.shared.messaging;

public interface EventsPublisher {

    void publish(PublishedEvent event);
}
