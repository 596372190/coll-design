package com.example.colldesign.comment.service;

import com.example.colldesign.comment.vo.CommentVo;
import com.example.colldesign.comment.vo.query.CommentQuery;
import com.github.pagehelper.PageInfo;

public interface CommentService {
    CommentVo createComment(CommentVo commentVo);

    CommentVo getById(String commentId);


    CommentVo updateComment(String id, String message);

    boolean deleteComment(String id);

    PageInfo<CommentVo> getCommentsByPage(CommentQuery commentQuery);

    boolean reopenById(String commentId);

    boolean resolveById(String commentId);
}
