package com.example.colldesign.comment.dao;

import com.example.colldesign.comment.vo.CommentVo;
import com.example.colldesign.comment.vo.query.CommentQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {

    List<CommentVo> getCommentsByPage(@Param("commentQuery") CommentQuery commentQuery);
}
