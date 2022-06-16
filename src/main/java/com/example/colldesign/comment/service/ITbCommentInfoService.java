package com.example.colldesign.comment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.colldesign.comment.model.TbCommentInfo;

/**
 * 服务类
 *
 * @author dragon
 * @since 2022-06-15
 */
public interface ITbCommentInfoService extends IService<TbCommentInfo> {

    boolean updateMesssageById(String id, String message);
}
