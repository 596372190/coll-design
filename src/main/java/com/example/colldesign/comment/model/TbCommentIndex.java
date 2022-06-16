package com.example.colldesign.comment.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;

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
public class TbCommentIndex extends Model<TbCommentIndex> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private String id;

    /**
     * 文档id
     */
    private String documentId;

    private String elementId;

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

    /**
     * 文档对象id
     */
    private String objectId;

    private int objectType;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public String getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public int getObjectType() {
        return objectType;
    }

    public void setObjectType(int objectType) {
        this.objectType = objectType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getResolvedTime() {
        return resolvedTime;
    }

    public void setResolvedTime(Date resolveTime) {
        this.resolvedTime = resolveTime;
    }

    public String getResolvedUserId() {
        return resolvedUserId;
    }

    public void setResolvedUserId(String resolvedUserId) {
        this.resolvedUserId = resolvedUserId;
    }

    public Date getReopenedTime() {
        return reopenedTime;
    }

    public void setReopenedTime(Date reopenedTime) {
        this.reopenedTime = reopenedTime;
    }

    public String getReopenedUserId() {
        return reopenedUserId;
    }

    public void setReopenedUserId(String reopenedUserId) {
        this.reopenedUserId = reopenedUserId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Long replyCount) {
        this.replyCount = replyCount;
    }
}