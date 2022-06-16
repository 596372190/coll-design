package com.example.colldesign.comment.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.colldesign.comment.model.TbCommentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * Mapper接口
 *
 * @author dragon
 * @since 2022-06-15
 */
@Mapper
public interface TbCommentInfoMapper extends BaseMapper<TbCommentInfo> {

    @Update("update tb_comment_info set message=#{message} where id=#{id}")
    boolean updateMesssageById(@Param("id") String id,@Param("message") String message);
}
