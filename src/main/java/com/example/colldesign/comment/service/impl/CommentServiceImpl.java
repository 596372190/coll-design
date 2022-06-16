package com.example.colldesign.comment.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.example.colldesign.comment.dao.CommentMapper;
import com.example.colldesign.comment.model.TbCommentIndex;
import com.example.colldesign.comment.model.TbCommentInfo;
import com.example.colldesign.comment.service.CommentService;
import com.example.colldesign.comment.service.ITbCommentIndexService;
import com.example.colldesign.comment.service.ITbCommentInfoService;
import com.example.colldesign.comment.vo.CommentVo;
import com.example.colldesign.comment.vo.UserVo;
import com.example.colldesign.comment.vo.query.CommentQuery;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private ITbCommentIndexService commentIndexService;

    @Autowired
    private ITbCommentInfoService commentInfoService;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    @Transactional
    public CommentVo createComment(CommentVo commentVo) {
        TbCommentIndex commentIndex = new TbCommentIndex();
        TbCommentInfo commentInfo = new TbCommentInfo();
        BeanUtil.copyProperties(commentVo,commentIndex);
        commentIndex.setCreateTime(DateUtil.date());
        commentIndex.setCreateUserId("123456");
        commentIndex.setDocumentId(commentIndex.getObjectId());
        commentIndex.setId(UUID.randomUUID().toString().replace("-",""));
        commentIndexService.save(commentIndex);
        if(StrUtil.isNotBlank(commentVo.getParentId())){
            commentIndexService.addReplyCountById(commentVo.getParentId());
        }
        BeanUtil.copyProperties(commentVo,commentInfo);
        commentInfo.setId(commentIndex.getId());
        commentInfoService.save(commentInfo);
        //仿造一个user
        commentVo.setCreatedBy(UserVo.builder().name("aaa").id(commentIndex.getCreateUserId()).build());
        commentVo.setId(commentIndex.getId());
        return commentVo;
    }

    @Override
    public CommentVo getById(String commentId) {
        TbCommentIndex commentIndex = commentIndexService.getById(commentId);
        TbCommentInfo commentInfo = commentInfoService.getById(commentId);
        CommentVo commentVo=new CommentVo(commentIndex,commentInfo);
        commentVo.setCreatedBy(UserVo.builder().name(commentIndex.getCreateUserId()).id(commentIndex.getCreateUserId()).build());
        //判断该评论是否可以删除或关闭
        //1.如果是子评论不能重开或关闭
        if(StrUtil.isNotBlank(commentIndex.getParentId())){
            commentVo.setCanResolveOrReopen(false);
        }
        return commentVo;
    }

    @Override
    @Transactional
    public CommentVo updateComment(String id, String message) {
        //oneshape编辑内容时是没有更新时间的
        //todo 评论只能是自己编辑
        commentInfoService.updateMesssageById(id,message);
        CommentVo commentVo = this.getById(id);
        return commentVo;

    }

    @Override
    @Transactional
    public boolean deleteComment(String id) {
        //todo 评论只允许父评论的创建者及自己删除
        //父评论删除了，子评论全部删除
        List<String> commentIds = commentIndexService.getSubCommentIdsById(id);
        commentIds.add(id);
        commentIndexService.removeByIds(commentIds);
        commentInfoService.removeByIds(commentIds);
        return true;
    }

    @Override
    public PageInfo<CommentVo> getCommentsByPage(CommentQuery commentQuery) {
        //todo 完善用户的信息
        PageHelper.startPage(commentQuery.getPageNum(),commentQuery.getPageSize());
        List<CommentVo> commentVos = commentMapper.getCommentsByPage(commentQuery);
        PageInfo<CommentVo> pageInfo = new PageInfo<CommentVo>(commentVos);
        return pageInfo;
    }

    @Override
    public boolean reopenById(String commentId) {
        return commentIndexService.reopenById(commentId);
    }

    @Override
    public boolean resolveById(String commentId) {
        return commentIndexService.resolveById(commentId);
    }
}
