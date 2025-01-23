package com.deskly.desklylocation.shared.storage;

import com.deskly.desklylocation.location.FileRepository;
import com.deskly.desklylocation.shared.language.URL;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class S3Repository implements FileRepository {

    private final S3Client client;

    private final String bucketName;

    public S3Repository(S3Client client, String bucketName) {
        this.client = client;
        this.bucketName = bucketName;
    }

    public URL upload(MultipartFile file) throws IOException {
        Path tempFile = Files.createTempFile("upload-", file.getOriginalFilename());
        file.transferTo(tempFile);
        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(file.getOriginalFilename())
                    .build();

            client.putObject(request, tempFile);

            String uploadedFileUrl = client.utilities()
                    .getUrl(b -> b.bucket(bucketName).key(file.getOriginalFilename()))
                    .getPath();

            return new URL(uploadedFileUrl);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload to S3", e);
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

}
