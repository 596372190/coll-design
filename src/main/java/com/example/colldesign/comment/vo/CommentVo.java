package com.example.colldesign.comment.vo;

import cn.hutool.core.bean.BeanUtil;
import com.example.colldesign.comment.model.TbCommentIndex;
import com.example.colldesign.comment.model.TbCommentInfo;
import lombok.*;

import java.util.Date;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentVo {

    private String id;

    /**
     * 文档id
     */
    private String projectId;

    private String documentId;

    /**
     * 工作空间id
     */
    private String workspaceId;

    /**
     * 版本id
     */
    private String versionId;

    /**
     * 父id
     */
    private String parentId;


    private int documentType;

    /**
     * 创建时间
     */

    private Date createTime;

    /**
     * 创建用户id
     */
    private String createUserId;

    private UserVo createdBy;

    private Date updateTime;

    //todo 应该删除  只能自己修改自己的评论
    private String updateUserId;

    private Date resolvedTime;

    private String resolvedUserId;

    private UserVo resolvedBy;

    private Date reopenedTime;

    private String reopenedUserId;

    private UserVo reopenedBy;

    /**
     * 0-新建，1-已解决，2-解决后重新打开
     */
    private Integer state;

    private Long replyCount;

    private String message;

    private String elementOccurrences;

    private String elementQuery;

    private String angle;

    private String cameraViewport;

    private String isPerspective;

    private String viewMatrix;

    private boolean canDelete;

    private boolean canResolveOrReopen;

    private boolean topLevel;

    private boolean isMark;
    private String markId;
    private String markType;


    public CommentVo(TbCommentIndex commentIndex, TbCommentInfo commentInfo){
        BeanUtil.copyProperties(commentInfo,this);
        BeanUtil.copyProperties(commentIndex,this);
    }

}
