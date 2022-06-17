package com.example.colldesign.minio.controller;

import com.example.colldesign.minio.util.MinioUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class MinioCotroller {


    @Autowired
    private MinioUtil minioUtil;

    @ApiOperation(value = "minio上传测试")
    @PostMapping(value = "/upload",headers = "content-type=multipart/form-data")
    public List<String> upload(@ApiParam@RequestParam(name = "multipartFile") MultipartFile[] multipartFile) {
        return minioUtil.upload(multipartFile);
    }

    @ApiOperation(value = "minio下载测试")
    @GetMapping("/download")
    public ResponseEntity<byte[]> download(@RequestParam String fileName) {
        return minioUtil.download(fileName);
    }

    @ApiOperation(value = "minio创建桶")
    @PostMapping("/createBucketIfNotExist")
    public void createBucketIfNotExist(@RequestParam String bucketName) {
        minioUtil.existBucket(bucketName);
    }
}
