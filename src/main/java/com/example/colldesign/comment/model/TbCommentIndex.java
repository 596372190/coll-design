package com.example.colldesign.comment.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author dragon
 * @since 2022-06-15
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TbCommentIndex extends Model<TbCommentIndex> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private String id;

    /**
     * 项目id
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
    @JsonFormat(shape =JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    private Date createTime;

    /**
     * 创建用户id
     */
    private String createUserId;

    @JsonFormat(shape =JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    private Date updateTime;

    private String updateUserId;

    @JsonFormat(shape =JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    private Date resolvedTime;

    private String resolvedUserId;

    @JsonFormat(shape =JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    private Date reopenedTime;

    private String reopenedUserId;

    /**
     * 0-新建，1-已解决，2-解决后重新打开
     */
    private Integer state;

    private Long replyCount;

    private boolean isMark;

    private String markId;

    private String markType;
}