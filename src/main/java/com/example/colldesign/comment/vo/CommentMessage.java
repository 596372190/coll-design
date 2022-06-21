package com.example.colldesign.comment.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 通知消息的统一对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("评论消息通知对象")
public class CommentMessage {


    @ApiModelProperty(value = "操作类型")
    private String operate;

    @ApiModelProperty(value = "评论父id")
    private String parentId;

    @ApiModelProperty(value = "评论id")
    private String id;

    @ApiModelProperty(value = "评论文本内容")
    private String message;

    @ApiModelProperty(value = "评论状态")
    private Integer state;

    @ApiModelProperty(value = "文档id")
    private String documentId;

    @ApiModelProperty(value = "解决时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date resolvedTime;


    @ApiModelProperty(value = "解决用户id")
    private String resolvedUserId;


    @ApiModelProperty(value = "评论重开时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reopenedTime;


    @ApiModelProperty(value = "评论重开用户id")
    private String reopenedUserId;


}
