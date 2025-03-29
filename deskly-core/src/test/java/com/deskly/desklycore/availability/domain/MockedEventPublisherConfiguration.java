package com.deskly.desklycore.availability.domain;

import com.deskly.desklycore.availability.infrastructure.SQSEventsListener;
import com.deskly.desklycore.shared.messaging.EventsPublisher;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import software.amazon.awssdk.services.sqs.SqsClient;

@TestConfiguration(proxyBeanMethods = false)
@TestPropertySource("classpath:application-test.yml")
public class MockedEventPublisherConfiguration {

    @MockitoBean
    EventsPublisher eventsPublisher;

    @MockitoBean
    SqsClient sqsClient;

    @MockitoBean
    SQSEventsListener sqsEventsListener;

}
