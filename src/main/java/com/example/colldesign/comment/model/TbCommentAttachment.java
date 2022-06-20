package com.example.colldesign.comment.model;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author dragon
 * @since 2022-06-18
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TbCommentAttachment extends Model<TbCommentAttachment> {

    private static final long serialVersionUID = 1L;

    private String id;

    private String commentId;

    private String mimeType;

    private String fileName;

    private String storeFileName;

    private String storePath;

    private String bucket;

    private String thumbnailForId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String createUserId;


}