package com.albercafe.rabbitmarket.controller;

import com.albercafe.rabbitmarket.dto.CustomResponse;
import com.albercafe.rabbitmarket.service.S3Service;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    @ApiOperation(httpMethod = "POST", value = "Upload Static Files", notes = "When you upload file, you must set key and value, key is \"multipartFile\" value is \"your specific file\" ")
    @ApiResponse(code = 200, message = "OK", response = CustomResponse.class)
    @PostMapping("/upload")
    public ResponseEntity<CustomResponse> uploadFile(@RequestParam("multipartFile") MultipartFile multipartFile) {
        return s3Service.uploadFile(multipartFile);
    }

    @ApiOperation(httpMethod = "DELETE", value = "Delete Specific File", notes = "When you want to delete file, you must specify file name ")
    @ApiResponse(code = 200, message = "OK", response = CustomResponse.class)
    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<CustomResponse> deleteFile(@PathVariable String fileName) {
        return s3Service.deleteFile(fileName);
    }
}
