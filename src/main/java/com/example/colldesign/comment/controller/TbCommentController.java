package com.example.colldesign.comment.controller;


import cn.hutool.db.handler.PageResultHandler;
import com.example.colldesign.comment.service.CommentService;
import com.example.colldesign.comment.service.ITbCommentIndexService;
import com.example.colldesign.comment.vo.CommentVo;
import com.example.colldesign.comment.vo.query.CommentQuery;
import com.example.colldesign.common.result.ApiResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageInterceptor;
import io.swagger.annotations.ApiModel;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


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

    private Map<String, Set<String>> userWsMap = new ConcurrentHashMap<>();

    @Autowired
    private CommentService commentService;

    @Autowired
    private ITbCommentIndexService commentIndexService;

    @Autowired
    private SimpMessagingTemplate template;

    @PostMapping("/joinDesign/{documentId}")
    private void joinDesign(@PathVariable("documentId")String documentId, Principal principal){
        if(userWsMap.containsKey(documentId)){
            userWsMap.get(documentId).add(principal.getName());
        }else {
            Set<String> nameSet = new HashSet<>();
            nameSet.add(principal.getName());
            userWsMap.put(documentId,nameSet);
        }
    }

    @PostMapping("/createComment")
    public ApiResult<CommentVo> createComment(@RequestBody CommentVo commentVo) {
        CommentVo vo = commentService.createComment(commentVo);
        ApiResult<CommentVo> success = ApiResult.SUCCESS(vo);
        //todo 异步提醒 或者放入队列提醒
        Set<String> nameSet = userWsMap.get(commentVo.getObjectId());
        String message=commentVo.getId();
        for (String name : nameSet) {
            template.convertAndSendToUser(name,"/queue/comment",message);
        }
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
        CommentVo vo = commentService.updateComment(commentVo.getId(),commentVo.getMessage());
        ApiResult<CommentVo> success = ApiResult.SUCCESS(vo);
        //todo 异步提醒 或者放入队列提醒
        Set<String> nameSet = userWsMap.get(vo.getObjectId());
        String message=vo.getId();
        for (String name : nameSet) {
            template.convertAndSendToUser(name,"/queue/comment",message);
        }
        return success;
    }

    @DeleteMapping("/{commentId}")
    @Transactional
    public ApiResult deleteComment(@PathVariable("commentId") String commentId) {
        String objectIdById = commentIndexService.getObjectIdById(commentId);
        boolean flag = commentService.deleteComment(commentId);
        ApiResult success = ApiResult.SUCCESS();
        //todo 异步提醒 或者放入队列提醒
        Set<String> nameSet = userWsMap.get(objectIdById);
        String message="delete"+commentId;
        for (String name : nameSet) {
            template.convertAndSendToUser(name,"/queue/comment",message);
        }
        return success;
    }

    @PostMapping("/getCommentsByPage")
    public ApiResult<PageInfo<CommentVo>> getCommentsByPage(@RequestBody CommentQuery commentQuery){
        PageInfo<CommentVo>  commentVoPageInfo= commentService.getCommentsByPage(commentQuery);
        return ApiResult.SUCCESS(commentVoPageInfo);
    }

    @PostMapping("/{commentId}/reopen")
    public ApiResult reopen(@PathVariable("commentId") String commentId){
        commentService.reopenById(commentId);
        //todo websocket通知
        return ApiResult.SUCCESS();
    }

    @PostMapping("/{commentId}/resolve")
    public ApiResult resolve(@PathVariable("commentId") String commentId){
        commentService.resolveById(commentId);
        //todo websocket通知
        return ApiResult.SUCCESS();
    }




}
