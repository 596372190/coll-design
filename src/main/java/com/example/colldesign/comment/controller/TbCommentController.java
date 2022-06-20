package com.example.colldesign.comment.controller;


import com.example.colldesign.comment.enums.CommentOperate;
import com.example.colldesign.comment.model.TbCommentIndex;
import com.example.colldesign.comment.service.CommentService;
import com.example.colldesign.comment.service.ITbCommentIndexService;
import com.example.colldesign.comment.service.impl.MessageService;
import com.example.colldesign.comment.vo.AttachmentVo;
import com.example.colldesign.comment.vo.CommentVo;
import com.example.colldesign.comment.vo.query.CommentQuery;
import com.example.colldesign.common.result.ApiResult;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;


/**
 * 前端控制器
 *
 * @author dragon
 * @since 2022-06-15
 */
@RestController
@RequestMapping("/comment")
public class TbCommentController {

    private Logger logger = LoggerFactory.getLogger(TbCommentController.class);

    @Autowired
    private CommentService commentService;

    @Autowired
    private ITbCommentIndexService commentIndexService;

    @Autowired
    private MessageService messageService;

    @PostMapping("/joinDesign/{projectId}")
    public void joinDesign(@PathVariable("projectId") String projectId, Principal principal) {
        try {
            messageService.joinDesign(projectId, principal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/createComment")
    public ApiResult<CommentVo> createComment(@RequestBody CommentVo commentVo) {
        CommentVo vo = commentService.createComment(commentVo);
        ApiResult<CommentVo> success = ApiResult.SUCCESS(vo);
        //todo 异步提醒 或者放入队列提醒
        messageService.publishCommentMeassage(vo, CommentOperate.CREATE);
        return success;
    }


    @GetMapping("/{commentId}")
    public ApiResult<CommentVo> getComment(@PathVariable("commentId") String commentId) {
        CommentVo vo = commentService.getById(commentId);
        ApiResult<CommentVo> success = ApiResult.SUCCESS(vo);
        return success;
    }

    @PostMapping("/{commentId}")
    public ApiResult<CommentVo> updateComment(@RequestBody CommentVo commentVo) {
        CommentVo vo = commentService.updateComment(commentVo.getId(), commentVo.getMessage());
        ApiResult<CommentVo> success = ApiResult.SUCCESS(vo);
        //todo 异步提醒 或者放入队列提醒
        messageService.publishCommentMeassage(vo, CommentOperate.UPDATE);
        return success;
    }

    @DeleteMapping("/{commentId}")
    @Transactional
    public ApiResult deleteComment(@PathVariable("commentId") String commentId) {
        TbCommentIndex commentIndex = commentIndexService.getById(commentId);
        boolean flag = commentService.deleteComment(commentId);
        ApiResult success = ApiResult.SUCCESS();
        //todo 异步提醒 或者放入队列提醒
        messageService.publishCommentMeassage(new CommentVo(commentIndex, null), CommentOperate.DELETE);
        return success;
    }

    @PostMapping("/getCommentsByPage")
    public ApiResult<PageInfo<CommentVo>> getCommentsByPage(@RequestBody CommentQuery commentQuery) {
        PageInfo<CommentVo> commentVoPageInfo = commentService.getCommentsByPage(commentQuery);
        return ApiResult.SUCCESS(commentVoPageInfo);
    }

    @PostMapping("/{commentId}/reopen")
    public ApiResult reopen(@PathVariable("commentId") String commentId) {
        commentService.reopenById(commentId);
        //todo 异步提醒 或者放入队列提醒
        messageService.publishCommentMeassage(commentId, CommentOperate.REOPEN);
        return ApiResult.SUCCESS();
    }

    @PostMapping("/{commentId}/resolve")
    public ApiResult resolve(@PathVariable("commentId") String commentId) {
        commentService.resolveById(commentId);
        //todo 异步提醒 或者放入队列提醒
        messageService.publishCommentMeassage(commentId, CommentOperate.RESOLVE);
        return ApiResult.SUCCESS();
    }


    @ApiOperation(value = "评论照片附件保存")
    @PostMapping(value = "/{commentId}/attachment")
    public ApiResult<AttachmentVo> saveAttachment(@PathVariable("commentId") String commentId, @RequestParam(name = "file") MultipartFile file) {
        try {
            return ApiResult.SUCCESS(commentService.saveAttachment(commentId, file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ApiResult.FAIL();
    }


    @ApiOperation(value = "获取评论附件")
    @GetMapping("/{commentId}/attachment/{attachmentId}")
    public ResponseEntity<byte[]> download(@PathVariable("commentId") String commentId, @PathVariable("attachmentId") String attachmentId) {
        return commentService.download(commentId, attachmentId);
    }

}
