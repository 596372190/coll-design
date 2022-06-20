package com.example.colldesign.minio.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MinioService {

    private Logger log = LoggerFactory.getLogger(MinioService.class);


    @Value("${minio.bucketName}")
    private String bucketName;

    @Autowired
    private MinioClient minioClient;

    /**
     * description: 判断bucket是否存在，不存在则创建
     *
     * @return: void
     * @author: weirx
     * @time: 2021/8/25 10:20
     */
    public boolean existBucket(String name) {
        try {
            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(name).build());
            return exists;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * bucket如果不存在则创建
     *
     * @param name
     */
    public void createBucketIfNotExist(String name) {
        try {
            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(name).build());
            if (!exists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(name).build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String upload(MultipartFile file) {
        return upload(file, null);
    }

    public String upload(MultipartFile file, String filePath) {
        if (StrUtil.isBlank(filePath)) {
            filePath = file.getOriginalFilename();
        }
        InputStream in = null;
        try {
            in = file.getInputStream();
            String fileUrl = uploadByInputStream(in, bucketName, filePath, file.getContentType());
            return fileUrl.substring(0, fileUrl.lastIndexOf("?"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";

    }

    public String uploadByInputStream(InputStream in, String bucketName, String filePath, String contentType) throws Exception {
        String[] split = filePath.split("\\.");
        if (split.length > 1) {
            filePath = split[0] + "_" + System.currentTimeMillis() + "." + split[1];
        } else {
            filePath = filePath + System.currentTimeMillis();
        }
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(filePath)
                .stream(in, in.available(), -1)
                .contentType(contentType)
                .build()
        );
        String fileUrl = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket(bucketName).object(filePath).method(Method.GET).build());
        return fileUrl;
    }

    /**
     * 上传按年月日层级
     *
     * @param file
     * @return
     */
    public String uploadWithYMDH(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String dateFomat = DateUtil.format(DateUtil.date(), "yyyy/MM/dd/HH/");
        String filePath = dateFomat + fileName;
        return upload(file, filePath);
    }


    public ResponseEntity<byte[]> download(String fileName) {
        return download(fileName, fileName, bucketName);
    }

    /**
     * description: 下载文件
     *
     * @param fileName
     * @return: org.springframework.http.ResponseEntity<byte [ ]>
     * @author: weirx
     * @time: 2021/8/25 10:34
     */
    public ResponseEntity<byte[]> download(String fileName, String filePath, String bucket) {
        ResponseEntity<byte[]> responseEntity = null;
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = minioClient.getObject(GetObjectArgs.builder().bucket(bucket).object(filePath).build());
            out = new ByteArrayOutputStream();
            IOUtils.copy(in, out);
            //封装返回值
            byte[] bytes = out.toByteArray();
            HttpHeaders headers = new HttpHeaders();
            try {
                headers.add("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, Constants.UTF_8));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            headers.setContentLength(bytes.length);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setAccessControlExposeHeaders(Arrays.asList("*"));
            responseEntity = new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseEntity;
    }

    public String uploadThumbnailByFix(MultipartFile file, String filePath, int width, int height) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Thumbnails.of(file.getInputStream()).size(width, height).keepAspectRatio(false).toOutputStream(out);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(out.toByteArray());
            String url = uploadByInputStream(inputStream, bucketName, filePath, file.getContentType());
            return url.substring(0, url.lastIndexOf("?"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String uploadThumbnailByFixWithYMDH(MultipartFile file, int width, int height) {
        String fileName = file.getOriginalFilename();
        String dateFomat = DateUtil.format(DateUtil.date(), "yyyy/MM/dd/HH/");
        String filePath = "thumbnail/" + dateFomat + "thumbnail_" + fileName;
        return uploadThumbnailByFix(file, filePath, width, height);
    }


    /**
     * 批量删除minio文件
     *
     * @param bucketName 桶名称
     * @param filePaths  文件目录
     */
    public void removeObjects(String bucketName, List<String> filePaths) {
        //validateBucketName(bucketName);
        List<DeleteObject> list = new ArrayList<>();
        for (String filePath : filePaths) {
            list.add(new DeleteObject(filePath));
        }

        Iterable<Result<DeleteError>> iterable = minioClient.removeObjects(
                RemoveObjectsArgs.builder().bucket(bucketName).objects(list).build()
        );
        try {
            for (Result<DeleteError> result : iterable) {
                DeleteError error = result.get();
                log.info("minio删除错误->bucketName={},objectName={},message={}", error.bucketName(), error.objectName(), error.message());
            }
        } catch (Exception e) {
            log.error("读取minio删除错误的数据时异常", e);
        }
    }
}