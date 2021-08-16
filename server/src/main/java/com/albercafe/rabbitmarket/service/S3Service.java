package com.albercafe.rabbitmarket.service;

import com.albercafe.rabbitmarket.dto.CustomResponse;
import com.albercafe.rabbitmarket.exception.RabbitMarketException;
import com.albercafe.rabbitmarket.util.Constants;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class S3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private final AmazonS3 amazonS3Client;

    public ResponseEntity<CustomResponse> uploadFile(MultipartFile multipartFile) {
        CustomResponse responseBody = new CustomResponse();

        File file = convertMultiPartFileToFile(multipartFile);
        String fileName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();

        try {
            amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, file));
        } catch (Exception e) {
            throw new RabbitMarketException(e.getMessage());
        }

        if (!file.delete()) {
            responseBody.setError("When file deleted, error occured !");
            responseBody.setData(null);
            return ResponseEntity.status(500).body(responseBody);
        }

        responseBody.setData(Constants.STATIC_FILE_LINK + "/" + fileName);
        responseBody.setError(null);

        return ResponseEntity.status(200).body(responseBody);
    }

    private File convertMultiPartFileToFile(MultipartFile multipartFile) {
        File convertedFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try {
            FileOutputStream fos = new FileOutputStream(convertedFile);
            fos.write(multipartFile.getBytes());
        } catch (IOException e) {
            throw new RabbitMarketException(e.getMessage());
        }
        return convertedFile;
    }

    public ResponseEntity<CustomResponse> deleteFile(String fileName) {
        CustomResponse responseBody = new CustomResponse();

        try {
            amazonS3Client.deleteObject(bucketName, fileName);
        } catch (Exception e) {
            throw new RabbitMarketException(e.getMessage());
        }

        responseBody.setData(fileName + " removed ! ");
        responseBody.setError(null);

        return ResponseEntity.status(200).body(responseBody);
    }
}
