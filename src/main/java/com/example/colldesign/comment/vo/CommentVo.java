package com.example.colldesign.comment.vo;

import cn.hutool.core.bean.BeanUtil;
import com.example.colldesign.comment.model.TbCommentIndex;
import com.example.colldesign.comment.model.TbCommentInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("评论请求返回对象")
public class CommentVo {

    @ApiModelProperty(value = "评论id")
    private String id;

    @ApiModelProperty(value = "项目id")
    private String projectId;


    @ApiModelProperty(value = "文档id")
    private String documentId;

    @ApiModelProperty(value = "工作空间id")
    private String workspaceId;

    @ApiModelProperty(value = "版本id")
    private String versionId;

    @ApiModelProperty(value = "评论父id")
    private String parentId;

    @ApiModelProperty(value = "文档类型")
    private int documentType;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "创建用户id")
    private String createUserId;

    @ApiModelProperty(value = "创建用户对象")
    private UserVo createdBy;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    //todo 应该删除  只能自己修改自己的评论
    private String updateUserId;

    @ApiModelProperty(value = "解决时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date resolvedTime;

    @ApiModelProperty(value = "解决用户id")
    private String resolvedUserId;

    @ApiModelProperty(value = "解决用户对象")
    private UserVo resolvedBy;

    @ApiModelProperty(value = "重开时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date reopenedTime;

    @ApiModelProperty(value = "重开用户id")
    private String reopenedUserId;

    @ApiModelProperty(value = "重开用户对象")
    private UserVo reopenedBy;

    @ApiModelProperty(value = "评论状态 0-新建，1-已解决，2-解决后重新打开")
    private Integer state;

    @ApiModelProperty(value = "评论回复数")
    private Long replyCount;

    //---commentInfo-------------
    @ApiModelProperty(value = "评论文本内容")
    private String message;

    private String angle;

    @ApiModelProperty(value = "相机标志点坐标")
    private String cameraViewport;

    @ApiModelProperty(value = "视图角度矩阵")
    private String viewMatrix;

    @ApiModelProperty(value = "是否可以删除")
    private boolean canDelete;

    @ApiModelProperty(value = "是否可以解决或重开")
    private boolean canResolveOrReopen;

    @ApiModelProperty(value = "是否是最高一级")
    private boolean topLevel;

    @ApiModelProperty(value = "标注id")
    private String markId;

    @ApiModelProperty(value = "标注类型")
    private String markType;

    @ApiModelProperty(value = "附件id")
    private String attachmentId;

    @ApiModelProperty(value = "缩略图附件id")
    private String thumbnailId;

    @ApiModelProperty(value = "附件对象")
    private AttachmentVo attachment;

    @ApiModelProperty(value = "缩略图附件对象")
    private AttachmentVo thumbnail;


    public CommentVo(TbCommentIndex commentIndex, TbCommentInfo commentInfo) {
        BeanUtil.copyProperties(commentInfo, this);
        BeanUtil.copyProperties(commentIndex, this);
    }

}
