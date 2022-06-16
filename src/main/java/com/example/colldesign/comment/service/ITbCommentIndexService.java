package com.example.colldesign.comment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.colldesign.comment.model.TbCommentIndex;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 服务类
 *
 * @author dragon
 * @since 2022-06-15
 */
public interface ITbCommentIndexService extends IService<TbCommentIndex> {

    List<String> getSubCommentIdsById(String id);


    boolean addReplyCountById(String id);

    String getPojectIdById(String commentId);

    boolean reopenById(String commentId);

    boolean resolveById(String commentId);
}
