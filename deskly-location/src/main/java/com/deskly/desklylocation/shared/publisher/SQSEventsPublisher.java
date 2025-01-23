package com.deskly.desklylocation.shared.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;


public class SQSEventsPublisher implements EventsPublisher {

    private final Logger log = LoggerFactory.getLogger(SQSEventsPublisher.class);

    private final SqsClient sqsClient;

    private final String queueUrl;

    public SQSEventsPublisher(SqsClient sqsClient, String queueUrl) {
        this.sqsClient = sqsClient;
        this.queueUrl = queueUrl;
    }

    @Override
    public void publish(PublishedEvent event) {
        try {
            SendMessageRequest request = SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(toJSON(event))
                    .build();
            sqsClient.sendMessage(request);
            log.info("Event was published");
        } catch (Exception e) {
            log.error("Queue Exception Message: {}", e.getMessage());
        }
    }

    private String toJSON(PublishedEvent event) throws JsonProcessingException {
        ObjectMapper objectMapper = SQSMessageSerializer.getInstance();
        return objectMapper.writeValueAsString(event);
    }


}
