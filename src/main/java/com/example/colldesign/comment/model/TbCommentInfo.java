package com.example.colldesign.comment.model;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

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
public class TbCommentInfo extends Model<TbCommentInfo> {

    private static final long serialVersionUID = 1L;

    private String id;

    private String message;

    private String cameraViewport;

    private String viewMatrix;


}