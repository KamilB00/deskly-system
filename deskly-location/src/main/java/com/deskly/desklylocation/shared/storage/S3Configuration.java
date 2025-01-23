package com.deskly.desklylocation.shared.storage;

import com.deskly.desklylocation.location.FileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Configuration {

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Value("${aws.region}")
    private String region;

    @Value("${aws.secret-access-key}")
    private String secretAccessKey;

    @Value("${aws.access-key-id}")
    private String accessKeyId;

    @Value("${aws.session-token}")
    private String sessionToken;

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsSessionCredentials.create(accessKeyId, secretAccessKey, sessionToken)
                        )).build();
    }

    @Bean
    public FileRepository repository(S3Client s3Client, @Value("${aws.s3.bucketName}") String bucketName) {
        return new S3Repository(s3Client, bucketName);
    }




}
