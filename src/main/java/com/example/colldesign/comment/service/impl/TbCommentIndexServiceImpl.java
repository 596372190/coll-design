package com.example.colldesign.comment.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.colldesign.comment.dao.TbCommentIndexMapper;
import com.example.colldesign.comment.model.TbCommentIndex;
import com.example.colldesign.comment.service.ITbCommentIndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.util.List;

/**
 * 服务实现类
 *
 * @author dragon
 * @since 2022-06-15
 */
@Service
public class TbCommentIndexServiceImpl extends ServiceImpl<TbCommentIndexMapper, TbCommentIndex> implements ITbCommentIndexService {

    private Logger logger = LoggerFactory.getLogger(TbCommentIndexServiceImpl.class);

    @Override
    public List<String> getSubCommentIdsById(String id) {
        return this.getBaseMapper().getSubCommentIdsById(id);
    }

    @Override
    @Transactional
    public boolean addReplyCountById(String parentId) {
        return this.getBaseMapper().addReplyCountById(parentId);
    }

    @Override
    public String getPojectIdById(String commentId) {
        return this.getBaseMapper().getPojectIdById(commentId);
    }

    @Override
    public boolean reopenById(String commentId) {
        UpdateWrapper<TbCommentIndex> updateWrapper = new UpdateWrapper<>();
        //state变为枚举
        updateWrapper.set("state",2);
        updateWrapper.set("reopened_time", DateUtil.date());
        updateWrapper.set("reopened_user_id", "userId-11111");
        updateWrapper.eq("id",commentId);
        return this.update(updateWrapper);
    }

    @Override
    public boolean resolveById(String commentId) {
        UpdateWrapper<TbCommentIndex> updateWrapper = new UpdateWrapper<>();
        //state变为枚举
        updateWrapper.set("state",1);
        updateWrapper.set("resolved_time", DateUtil.date());
        updateWrapper.set("resolved_user_id", "userId-11111");
        updateWrapper.eq("id",commentId);
        return this.update(updateWrapper);
    }
}
