package com.example.colldesign.comment.service;

import com.example.colldesign.comment.vo.AttachmentVo;
import com.example.colldesign.comment.vo.CommentVo;
import com.example.colldesign.comment.vo.query.CommentQuery;
import com.github.pagehelper.PageInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface CommentService {
    CommentVo createComment(CommentVo commentVo);

    CommentVo getById(String commentId);


    CommentVo updateComment(String id, String message);

    boolean deleteComment(String id);

    PageInfo<CommentVo> getCommentsByPage(CommentQuery commentQuery);

    boolean reopenById(String commentId);

    boolean resolveById(String commentId);

    AttachmentVo saveAttachment(String commentId, MultipartFile file) throws Exception;

    ResponseEntity<byte[]> download(String commentId, String attachmentId);
}
