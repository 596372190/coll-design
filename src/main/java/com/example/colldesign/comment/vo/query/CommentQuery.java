package com.example.colldesign.comment.vo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "评论查询请求对象")
public class CommentQuery extends BaseQueryVo {

    @ApiModelProperty(value = "文档ID")
    private String documentId;
    @ApiModelProperty(value = "评论父ID")
    private String parentId;

    /*通过这个参数去过滤
    1--我的未完成
    2--所有未完成
    3--已完成任务
    */
    @ApiModelProperty(value = "过滤类型（ 1--我的未完成 2--所有未完成 3--已完成任务")
    private String filter;
}
