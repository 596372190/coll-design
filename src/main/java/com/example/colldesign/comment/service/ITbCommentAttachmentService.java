package com.example.colldesign.comment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.colldesign.comment.model.TbCommentAttachment;
import org.springframework.web.multipart.MultipartFile;

/**
 * 服务类
 *
 * @author dragon
 * @since 2022-06-18
 */
public interface ITbCommentAttachmentService extends IService<TbCommentAttachment> {

    TbCommentAttachment uploadAndSave(String commentId, MultipartFile file);

    TbCommentAttachment uploadAndSaveThumbnail(String commentId, MultipartFile file, String thumbnailForId, int width, int height);

    boolean removeAttachmentById(String attachmentId);
}
