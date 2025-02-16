package com.jipmer.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.transcribe.TranscribeClient;
import software.amazon.awssdk.services.transcribe.model.*;
import software.amazon.awssdk.core.sync.RequestBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.TimeUnit;





@RestController
@RequestMapping("/api/transcribe")
@CrossOrigin(origins = "*")
public class TranscribeController {

    @Value("${aws.accessKeyId}")
    private String accessKeyId;

    @Value("${aws.secretAccessKey}")
    private String secretAccessKey;

    @Value("${aws.region}")
    private String region;

    @Value("${s3.bucket.name}")
    private String bucketName;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadAndTranscribe(@RequestParam("file") MultipartFile file) {
        String keyName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        try {
            // Store the file in S3
            S3Client s3 = S3Client.builder()
                    .region(Region.of(region))
                    .credentialsProvider(StaticCredentialsProvider.create(
                            AwsBasicCredentials.create(accessKeyId, secretAccessKey)))
                    .build();

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyName)
                    .build();

            PutObjectResponse response = s3.putObject(putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            // Transcribe the audio file using non-streaming API
            TranscribeClient transcribeClient = TranscribeClient.builder()
                    .region(Region.of(region))
                    .credentialsProvider(StaticCredentialsProvider.create(
                            AwsBasicCredentials.create(accessKeyId, secretAccessKey)))
                    .build();
            String s3Url = "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + keyName;
            String jobName = UUID.randomUUID().toString();

            StartTranscriptionJobRequest request = StartTranscriptionJobRequest.builder()
                    .transcriptionJobName(jobName)
                    .languageCode("en-US")
                    .media(Media.builder().mediaFileUri("s3://" + bucketName + "/" + keyName).build())
                    .mediaFormat(MediaFormat.WEBM) // Adjust based on your file format
                    .build();

            transcribeClient.startTranscriptionJob(request);

            // Poll for job completion
            while (true) {
                GetTranscriptionJobRequest getRequest = GetTranscriptionJobRequest.builder()
                        .transcriptionJobName(jobName)
                        .build();

                GetTranscriptionJobResponse getResponse = transcribeClient.getTranscriptionJob(getRequest);

                if (getResponse.transcriptionJob().transcriptionJobStatus() == TranscriptionJobStatus.COMPLETED) {
                    String transcriptUrl = getResponse.transcriptionJob().transcript().transcriptFileUri();

                    return ResponseEntity.ok(fetchTranscript(transcriptUrl));
                } else if (getResponse.transcriptionJob().transcriptionJobStatus() == TranscriptionJobStatus.FAILED) {

                    return ResponseEntity.status(401).body("Transcription job failed.");
                }

                // Wait for a few seconds before checking again
                TimeUnit.SECONDS.sleep(5);
            }

        } catch (S3Exception | InterruptedException | IOException e) {
            e.printStackTrace();

            return ResponseEntity.status(401).body("Error uploading to S3 or transcribing audio.");
        }
    }
    private String fetchTranscript(String transcriptUrl) {
        try {
            URL url = new URL(transcriptUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder content = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            // Parse the JSON and extract the transcript
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(content.toString());
            String transcript = rootNode.path("results").path("transcripts").get(0).path("transcript").asText();

            return transcript;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error fetching transcription result.";
        }
    }

}
