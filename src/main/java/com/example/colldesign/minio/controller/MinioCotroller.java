package com.example.colldesign.minio.controller;

import com.example.colldesign.common.result.ApiResult;
import com.example.colldesign.minio.util.MinioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@RestController
public class MinioCotroller {


    @Autowired
    private MinioService minioService;

    @ApiOperation(value = "minio上传测试")
    @PostMapping(value = "/upload", headers = "content-type=multipart/form-data")
    public ApiResult<String> upload(@ApiParam @RequestParam(name = "file") MultipartFile file) {
        return ApiResult.SUCCESS(minioService.upload(file));
    }

    @ApiOperation(value = "minio下载测试")
    @GetMapping("/download")
    public ResponseEntity<byte[]> download(@RequestParam String fileName) {
        return minioService.download(fileName);
    }

    @ApiOperation(value = "minio创建桶")
    @PostMapping("/createBucketIfNotExist")
    public void createBucketIfNotExist(@RequestParam String bucketName) {
        minioService.existBucket(bucketName);
    }

    @ApiOperation(value = "上传缩略图")
    @PostMapping(value = "/uploadThumbnail", headers = "content-type=multipart/form-data")
    public ApiResult<String> uploadThumbnail(@ApiParam @RequestParam(name = "file") MultipartFile file) {
        return ApiResult.SUCCESS(minioService.uploadThumbnailByFixWithYMDH(file, 350, 234));
    }

    @ApiOperation(value = "上传缩略图")
    @DeleteMapping(value = "/delete")
    public ApiResult removeObjects(String bucketName, String filePaths) {
        minioService.removeObjects(bucketName, Arrays.asList(filePaths.split(",")));
        return ApiResult.SUCCESS();
    }

}
