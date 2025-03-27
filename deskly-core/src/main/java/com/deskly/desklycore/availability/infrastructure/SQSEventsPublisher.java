package com.deskly.desklycore.availability.infrastructure;

import com.deskly.desklycore.shared.messaging.EventsPublisher;
import com.deskly.desklycore.shared.messaging.PublishedEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.sqs.SqsClient;

@RequiredArgsConstructor
public class SQSEventsPublisher implements EventsPublisher {

    private final Logger log = LoggerFactory.getLogger(SQSEventsPublisher.class);

    private final SqsClient sqsClient;

    private final String queueUrl;

    @Override
    public void publish(PublishedEvent event) {
        log.info("Event published");
    }
}
