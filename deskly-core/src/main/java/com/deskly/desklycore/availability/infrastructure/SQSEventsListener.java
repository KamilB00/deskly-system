package com.deskly.desklycore.availability.infrastructure;


import com.deskly.desklycore.shared.messaging.ReceivedEvent;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.util.List;

@RequiredArgsConstructor
public class SQSEventsListener {

    private final Logger log = LoggerFactory.getLogger(SQSEventsListener.class);

    private final EventsHandler eventsHandler;

    private final SqsClient sqsClient;

    private final String queueUrl;

    @Scheduled(fixedDelay = 5000)
    public void receiveMessage() {
        try {
            ReceiveMessageRequest request = ReceiveMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .maxNumberOfMessages(10)
                    .build();

            List<Message> messages = sqsClient.receiveMessage(request).messages();

            for (Message message : messages) {
                val event = SQSMessageDeserializer.getObjectMapper().readValue(message.body(), ReceivedEvent.class);
                Either<Failure, Success> result = eventsHandler.handle(event);
                if (result.isRight()) {
                    delete(message);
                    log.info("Event successfully handled !");
                } else {
                    log.error("Tried to handle event but failed");
                }
            }
        } catch (Exception e) {
            log.error("Queue Exception Message: {}", e.getMessage());
        }
    }

    private void delete(Message message) {
        DeleteMessageRequest deleteRequest = DeleteMessageRequest.builder()
                .queueUrl(queueUrl)
                .receiptHandle(message.receiptHandle())
                .build();
        sqsClient.deleteMessage(deleteRequest);
    }
}

