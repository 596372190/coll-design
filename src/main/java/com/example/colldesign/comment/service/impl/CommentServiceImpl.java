package com.example.colldesign.comment.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.example.colldesign.comment.dao.CommentMapper;
import com.example.colldesign.comment.model.TbCommentAttachment;
import com.example.colldesign.comment.model.TbCommentIndex;
import com.example.colldesign.comment.model.TbCommentInfo;
import com.example.colldesign.comment.service.CommentService;
import com.example.colldesign.comment.service.ITbCommentAttachmentService;
import com.example.colldesign.comment.service.ITbCommentIndexService;
import com.example.colldesign.comment.service.ITbCommentInfoService;
import com.example.colldesign.comment.vo.AttachmentVo;
import com.example.colldesign.comment.vo.CommentVo;
import com.example.colldesign.comment.vo.UserVo;
import com.example.colldesign.comment.vo.query.CommentQuery;
import com.example.colldesign.common.result.CommonPage;
import com.example.colldesign.minio.util.MinioService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private MinioService minioService;

    @Autowired
    private ITbCommentAttachmentService attachmentService;

    @Value("${minio.bucketName}")
    private String bucket;

    @Override
    @Transactional
    public CommentVo createComment(CommentVo commentVo) {
        TbCommentIndex commentIndex = new TbCommentIndex();
        TbCommentInfo commentInfo = new TbCommentInfo();
        BeanUtil.copyProperties(commentVo, commentIndex);
        commentIndex.setCreateTime(DateUtil.date());
        commentIndex.setCreateUserId("123456");
        commentIndex.setId(UUID.randomUUID().toString().replace("-", ""));
        commentIndexService.save(commentIndex);
        if (StrUtil.isNotBlank(commentVo.getParentId())) {
            commentIndexService.addReplyCountById(commentVo.getParentId());
        }
        BeanUtil.copyProperties(commentVo, commentInfo);
        commentInfo.setId(commentIndex.getId());
        commentInfoService.save(commentInfo);
        //????????????user
        commentVo.setId(commentIndex.getId());
        return commentVo;
    }

    @Override
    public CommentVo getById(String commentId) {
        TbCommentIndex commentIndex = commentIndexService.getById(commentId);
        TbCommentInfo commentInfo = commentInfoService.getById(commentId);
        CommentVo commentVo = new CommentVo(commentIndex, commentInfo);
        //todo ??????????????????????????????????????????
        commentVo.setCanDelete(true);
        commentVo.setCanResolveOrReopen(true);
        //1.???????????????????????????????????????
        if (StrUtil.isNotBlank(commentIndex.getParentId())) {
            commentVo.setCanResolveOrReopen(false);
            commentVo.setTopLevel(false);
        } else {
            commentVo.setTopLevel(true);
        }
        //??????createUser??????
        completeCreateBy(commentVo);
        //??????attachment??????
        completeAttatchment(commentVo);
        //??????thumbnail??????
        completeThumbnail(commentVo);
        //??????reopenedUser??????
        completeReopenedBy(commentVo);
        //??????resolvedUser??????
        completeResolvedBy(commentVo);
        return commentVo;
    }

    private CommentVo completeCreateBy(CommentVo commentVo) {
        if (StrUtil.isBlank(commentVo.getCreateUserId())) {
            return commentVo;
        }
        commentVo.setCreatedBy(UserVo.builder().name(commentVo.getCreateUserId()).id(commentVo.getCreateUserId()).build());
        return commentVo;
    }

    private CommentVo completeReopenedBy(CommentVo commentVo) {
        if (StrUtil.isBlank(commentVo.getReopenedUserId())) {
            return commentVo;
        }
        commentVo.setCreatedBy(UserVo.builder().name(commentVo.getReopenedUserId()).id(commentVo.getReopenedUserId()).build());
        return commentVo;
    }

    private CommentVo completeResolvedBy(CommentVo commentVo) {
        if (StrUtil.isBlank(commentVo.getResolvedUserId())) {
            return commentVo;
        }
        commentVo.setCreatedBy(UserVo.builder().name(commentVo.getResolvedUserId()).id(commentVo.getResolvedUserId()).build());
        return commentVo;
    }

    private CommentVo completeThumbnail(CommentVo commentVo) {
        if (StrUtil.isBlank(commentVo.getThumbnailId())) {
            return commentVo;
        }
        TbCommentAttachment attachment = attachmentService.getById(commentVo.getThumbnailId());
        AttachmentVo thumbnail = new AttachmentVo(attachment);
        commentVo.setThumbnail(thumbnail);
        return commentVo;
    }

    private CommentVo completeAttatchment(CommentVo commentVo) {
        if (StrUtil.isBlank(commentVo.getAttachmentId())) {
            return commentVo;
        }
        TbCommentAttachment attachment = attachmentService.getById(commentVo.getAttachmentId());
        AttachmentVo attachmentVo = new AttachmentVo(attachment);
        commentVo.setAttachment(attachmentVo);
        return commentVo;
    }

    @Override
    @Transactional
    public CommentVo updateComment(String id, String message) {
        //oneshape???????????????????????????????????????
        //todo ???????????????????????????
        commentInfoService.updateMesssageById(id, message);
        CommentVo commentVo = this.getById(id);
        return commentVo;

    }

    @Override
    @Transactional
    public boolean deleteComment(String id) {
        TbCommentIndex commentIndex = commentIndexService.getById(id);
        //todo ???????????????????????????????????????????????????
        //??????????????????????????????????????????
        List<String> commentIds = commentIndexService.getSubCommentIdsById(id);
        commentIds.add(id);
        commentIndexService.removeByIds(commentIds);
        commentInfoService.removeByIds(commentIds);
        //????????????
        attachmentService.removeAttachmentById(commentIndex.getAttachmentId());
        //???????????????
        attachmentService.removeAttachmentById(commentIndex.getThumbnailId());
        return true;
    }

    @Override
    public List<CommentVo> getComments(CommentQuery commentQuery) {
        //todo ?????????????????????
        PageHelper.startPage(commentQuery.getPageNum(), commentQuery.getPageSize());
        List<CommentVo> commentVos = commentMapper.getCommentsByPage(commentQuery);
        for (CommentVo commentVo : commentVos) {
            //??????createUser??????
            completeCreateBy(commentVo);
            if (StrUtil.isBlank(commentVo.getParentId())) {
                //??????attachment??????
                completeAttatchment(commentVo);
                //??????thumbnail??????
                completeThumbnail(commentVo);
                //??????reopenedUser??????
                completeReopenedBy(commentVo);
                //??????resolvedUser??????
                completeResolvedBy(commentVo);
            }
        }
        return commentVos;
    }

    @Override
    public boolean reopenById(String commentId) {
        return commentIndexService.reopenById(commentId);
    }

    @Override
    public boolean resolveById(String commentId) {
        return commentIndexService.resolveById(commentId);
    }


    @Override
    @Transactional
    public AttachmentVo saveAttachment(String commentId, MultipartFile file) throws Exception {
        TbCommentAttachment attachment = attachmentService.uploadAndSave(commentId, file);
        TbCommentAttachment thumbnail = attachmentService.uploadAndSaveThumbnail(commentId, file, attachment.getId(), 350, 234);
        commentIndexService.updateAttachmentIdById(commentId, attachment.getId(), thumbnail.getId());
        AttachmentVo attachmentVo = new AttachmentVo(attachment);
        attachmentVo.setThumbnailId(thumbnail.getId());
        return attachmentVo;
    }

    @Override
    public ResponseEntity<byte[]> download(String commentId, String attachmentId) {
        TbCommentAttachment attachment = attachmentService.getById(attachmentId);
        return minioService.download(attachment.getFileName(), attachment.getStorePath(), attachment.getBucket());
    }
}
