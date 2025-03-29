package com.deskly.desklycore.shared.messaging;

import com.deskly.desklycore.availability.infrastructure.EventsHandler;
import com.deskly.desklycore.availability.infrastructure.SQSEventsListener;
import com.deskly.desklycore.availability.infrastructure.SQSEventsPublisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

@EnableScheduling
@Configuration
public class SQSConfiguration {

    @Value("${aws.sqs.url}")
    private String queueUrl;

    @Value("${aws.region}")
    private String region;

    @Value("${aws.secret-access-key}")
    private String secretAccessKey;

    @Value("${aws.access-key-id}")
    private String accessKeyId;

    @Value("${aws.session-token}")
    private String sessionToken;

    @Bean
    public SqsClient sqsClient() {
        return SqsClient.builder()
                .region(Region.of(region))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsSessionCredentials.create(accessKeyId, secretAccessKey, sessionToken)
                        )).build();
    }

    @Bean
    public EventsPublisher eventsPublisher(SqsClient client) {
        return new SQSEventsPublisher(client, queueUrl);
    }

    @Bean
    public SQSEventsListener eventsListener(SqsClient client, EventsHandler eventsHandler) {
        return new SQSEventsListener(eventsHandler, client, queueUrl);
    }
}
