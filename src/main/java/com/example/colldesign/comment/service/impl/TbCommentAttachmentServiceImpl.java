package com.example.colldesign.comment.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.colldesign.comment.dao.TbCommentAttachmentMapper;
import com.example.colldesign.comment.model.TbCommentAttachment;
import com.example.colldesign.comment.service.ITbCommentAttachmentService;
import com.example.colldesign.minio.util.MinioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * 服务实现类
 *
 * @author dragon
 * @since 2022-06-18
 */
@Service
public class TbCommentAttachmentServiceImpl extends ServiceImpl<TbCommentAttachmentMapper, TbCommentAttachment> implements ITbCommentAttachmentService {

    private Logger logger = LoggerFactory.getLogger(TbCommentAttachmentServiceImpl.class);

    @Autowired
    private MinioService minioService;


    @Value("${minio.bucketName}")
    private String bucket;

    @Override
    @Transactional
    public TbCommentAttachment uploadAndSave(String commentId, MultipartFile file) {
        //判断文件的真实类型
        String fileType = file.getContentType();
        //minio上传图片
        String fileUrl = minioService.uploadWithYMDH(file);
        String file_name = file.getOriginalFilename();
        String store_file_name = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        String store_path = StrUtil.subAfter(fileUrl, bucket, false);
        TbCommentAttachment attachment = TbCommentAttachment.builder()
                .id(UUID.randomUUID().toString().replace("-", ""))
                .commentId(commentId)
                .mimeType(fileType)
                .fileName(file_name)
                .storeFileName(store_file_name)
                .storePath(store_path)
                .bucket(bucket)
                .createTime(DateUtil.date())
                .createUserId("Huang")
                .build();
        this.save(attachment);
        return attachment;
    }

    @Override
    @Transactional
    public TbCommentAttachment uploadAndSaveThumbnail(String commentId, MultipartFile file, String thumbnailForId, int width, int height) {
        //判断文件的真实类型
        String fileType = file.getContentType();
        //minio上传图片
        String fileUrl = minioService.uploadThumbnailByFixWithYMDH(file, width, height);
        String file_name = file.getOriginalFilename();
        String store_file_name = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        String store_path = StrUtil.subAfter(fileUrl, bucket, false);
        TbCommentAttachment attachment = TbCommentAttachment.builder()
                .id(UUID.randomUUID().toString().replace("-", ""))
                .commentId(commentId)
                .mimeType(fileType)
                .fileName(file_name)
                .storeFileName(store_file_name)
                .storePath(store_path)
                .bucket(bucket)
                .thumbnailForId(thumbnailForId)
                .createTime(DateUtil.date())
                .createUserId("Huang")
                .build();
        this.save(attachment);
        return attachment;
    }

    @Override
    public boolean removeAttachmentById(String id) {
        TbCommentAttachment attachment = this.getById(id);
        boolean remove = this.removeById(id);
        //todo 异步清除文件
        minioService.removeObjects(attachment.getBucket(), CollectionUtil.newArrayList(attachment.getStorePath()));
        return remove;
    }
}
