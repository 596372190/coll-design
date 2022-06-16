package com.example.colldesign.comment.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.colldesign.comment.model.TbCommentIndex;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Mapper接口
 *
 * @author dragon
 * @since 2022-06-15
 */
@Mapper
public interface TbCommentIndexMapper extends BaseMapper<TbCommentIndex> {

    @Select("select id from tb_comment_index t where t.parent_id=#{id}")
    List<String> getSubCommentIdsById(@Param("id") String id);

    @Update("update tb_comment_index set reply_count=reply_count+1 where id = #{id}")
    boolean addReplyCountById(@Param("id")String id);

    @Select("select object_id from tb_comment_index t where t.id=#{id}")
    String getObjectIdById(@Param("id")String id);

}
