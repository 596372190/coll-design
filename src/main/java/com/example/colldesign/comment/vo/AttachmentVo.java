package com.example.colldesign.comment.vo;

import cn.hutool.core.bean.BeanUtil;
import com.example.colldesign.comment.model.TbCommentAttachment;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttachmentVo {

    private String id;

    private String commentId;

    private String mimeType;

    private String fileName;

    private String href;

    private String thumbnailForId;

    private String thumbnailId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String createUserId;

    public AttachmentVo(TbCommentAttachment attachment) {
        BeanUtil.copyProperties(attachment, this);
        this.href = "http://comment.dragon.com:8080/comment/" + attachment.getCommentId() + "/attachment/" + attachment.getId();
    }
}
