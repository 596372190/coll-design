package com.example.colldesign.comment.vo;

import cn.hutool.core.bean.BeanUtil;
import com.example.colldesign.comment.model.TbCommentAttachment;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "附件返回对象")
public class AttachmentVo {

    @ApiModelProperty(value = "附件主键id")
    private String id;

    @ApiModelProperty(value = "评论id")
    private String commentId;

    @ApiModelProperty(value = "文件类型")
    private String mimeType;

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "文件链接")
    private String href;

    @ApiModelProperty(value = "缩略图来源id")
    private String thumbnailForId;

    @ApiModelProperty(value = "缩略图id")
    private String thumbnailId;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "创建用户id")
    private String createUserId;

    public AttachmentVo(TbCommentAttachment attachment) {
        BeanUtil.copyProperties(attachment, this);
        this.href = "http://comment.dragon.com:8080/comment/" + attachment.getCommentId() + "/attachment/" + attachment.getId();
    }
}
