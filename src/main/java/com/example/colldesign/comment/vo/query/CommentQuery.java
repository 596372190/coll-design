package com.example.colldesign.comment.vo.query;

import lombok.Data;

import java.util.List;

@Data
public class CommentQuery extends BaseQueryVo{
    private String documentId;
    private String parentId;

    //通过这个参数去过滤
    private String filter;
}
