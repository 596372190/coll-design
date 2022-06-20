package com.example.colldesign.comment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.colldesign.comment.dao.TbCommentInfoMapper;
import com.example.colldesign.comment.model.TbCommentInfo;
import com.example.colldesign.comment.service.ITbCommentInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author dragon
 * @since 2022-06-15
 */
@Service
public class TbCommentInfoServiceImpl extends ServiceImpl<TbCommentInfoMapper, TbCommentInfo> implements ITbCommentInfoService {

    private Logger logger = LoggerFactory.getLogger(TbCommentInfoServiceImpl.class);

    @Override
    public boolean updateMesssageById(String id, String message) {
        return this.getBaseMapper().updateMesssageById(id, message);
    }
}
