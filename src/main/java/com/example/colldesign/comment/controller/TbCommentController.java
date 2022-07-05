package com.example.colldesign.comment.controller;


import com.example.colldesign.comment.enums.CommentOperate;
import com.example.colldesign.comment.model.TbCommentIndex;
import com.example.colldesign.comment.service.CommentService;
import com.example.colldesign.comment.service.ITbCommentIndexService;
import com.example.colldesign.comment.service.impl.MessageService;
import com.example.colldesign.comment.util.MimetypeUtil;
import com.example.colldesign.comment.vo.AttachmentVo;
import com.example.colldesign.comment.vo.CommentVo;
import com.example.colldesign.comment.vo.query.CommentQuery;
import com.example.colldesign.common.result.CommonPage;
import com.example.colldesign.common.result.CommonResult;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;


/**
 * 前端控制器
 *
 * @author dragon
 * @since 2022-06-15
 */
@Api(tags = "TbCommentController", description = "评论管理")
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

    @ApiOperation("创建评论")
    @PostMapping("/createComment")
    public CommonResult<CommentVo> createComment(@RequestBody CommentVo commentVo) {
        CommentVo vo = commentService.createComment(commentVo);
        CommonResult<CommentVo> success = CommonResult.success(vo);
        //todo 异步提醒 或者放入队列提醒
        messageService.publishCommentMeassage(vo, CommentOperate.CREATE);
        return success;
    }


    @ApiOperation("根据id获取评论")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "commentId", value = "评论id", required = true)
    })
    @GetMapping("/{commentId}")
    public CommonResult<CommentVo> getComment(@PathVariable("commentId") String commentId) {
        CommentVo vo = commentService.getById(commentId);
        CommonResult<CommentVo> success = CommonResult.success(vo);
        return success;
    }

    @ApiOperation("修改评论文本内容")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "commentId", value = "评论id", required = true)
    })
    @PostMapping("/{commentId}")
    public CommonResult<CommentVo> updateComment(@RequestBody CommentVo commentVo) {
        CommentVo vo = commentService.updateComment(commentVo.getId(), commentVo.getMessage());
        CommonResult<CommentVo> success = CommonResult.success(vo);
        //todo 异步提醒 或者放入队列提醒
        messageService.publishCommentMeassage(vo, CommentOperate.UPDATE);
        return success;
    }

    @ApiOperation("根据id删除评论")
    @ApiImplicitParam(paramType = "path", name = "commentId", value = "评论id", required = true)
    @DeleteMapping("/{commentId}")
    public CommonResult deleteComment(@PathVariable("commentId") String commentId) {
        TbCommentIndex commentIndex = commentIndexService.getById(commentId);
        boolean flag = commentService.deleteComment(commentId);
        CommonResult success = CommonResult.success();
        //todo 异步提醒 或者放入队列提醒
        messageService.publishCommentMeassage(new CommentVo(commentIndex, null), CommentOperate.DELETE);
        return success;
    }

    @ApiOperation("分页获取评论列表")
    @PostMapping("/getCommentsByPage")
    public CommonResult<CommonPage<CommentVo>> getCommentsByPage(@RequestBody CommentQuery commentQuery) {
        List<CommentVo> commentVos = commentService.getComments(commentQuery);
        return CommonResult.success(CommonPage.restPage(commentVos));
    }

    @ApiOperation("根据id重开评论")
    @ApiImplicitParam(paramType = "path", name = "commentId", value = "评论id", required = true)
    @PostMapping("/{commentId}/reopen")
    public CommonResult reopen(@PathVariable("commentId") String commentId) {
        commentService.reopenById(commentId);
        //todo 异步提醒 或者放入队列提醒
        messageService.publishCommentMeassage(commentId, CommentOperate.REOPEN);
        return CommonResult.success();
    }

    @ApiOperation("根据id关闭评论")
    @ApiImplicitParam(paramType = "path", name = "commentId", value = "评论id", required = true)
    @PostMapping("/{commentId}/resolve")
    public CommonResult resolve(@PathVariable("commentId") String commentId) {
        commentService.resolveById(commentId);
        //todo 异步提醒 或者放入队列提醒
        messageService.publishCommentMeassage(commentId, CommentOperate.RESOLVE);
        return CommonResult.success();
    }

    @ApiOperation("根据id保存图片附件")
    @PostMapping(value = "/{commentId}/attachment")
    public CommonResult<AttachmentVo> saveAttachment(@PathVariable("commentId") String commentId, @RequestParam(name = "file") MultipartFile file) {
        try {
            if (MimetypeUtil.isImage(file)) {
                return CommonResult.success(commentService.saveAttachment(commentId, file));
            } else {
                return CommonResult.failed().setMessage("请正确上传图片!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed().setMessage(e.getMessage());
        }
    }


    @ApiOperation(value = "根据id获取评论图片")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "commentId", value = "评论id", required = true),
            @ApiImplicitParam(paramType = "path", name = "attachmentId", value = "附件id", required = true)
    })
    @GetMapping("/{commentId}/attachment/{attachmentId}")
    public ResponseEntity<byte[]> download(@PathVariable("commentId") String commentId, @PathVariable("attachmentId") String attachmentId) {
        return commentService.download(commentId, attachmentId);
    }
}
