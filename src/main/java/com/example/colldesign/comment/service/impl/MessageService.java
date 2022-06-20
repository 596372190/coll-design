package com.example.colldesign.comment.service.impl;

import com.example.colldesign.comment.enums.CommentOperate;
import com.example.colldesign.comment.model.TbCommentIndex;
import com.example.colldesign.comment.service.ITbCommentIndexService;
import com.example.colldesign.comment.vo.CommentMessage;
import com.example.colldesign.comment.vo.CommentVo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.Principal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service("messageService")
public class MessageService {

    private Map<String, Set<String>> userWsMap = new ConcurrentHashMap<>();

    private String syncDestination = "/queue/sync/comment/";

    //测试
    @PostConstruct
    public void init() {
        Set<String> nameSet = new HashSet<>();
        nameSet.add("huang");
        nameSet.add("admin");
        userWsMap.put("aaa", nameSet);
    }

    @Autowired
    private ITbCommentIndexService commentIndexService;

    @Autowired
    private SimpMessagingTemplate template;

    public void joinDesign(String projectId, Principal principal) {
        if (userWsMap.containsKey(projectId)) {
            userWsMap.get(projectId).add(principal.getName());
        } else {
            Set<String> nameSet = new HashSet<>();
            nameSet.add(principal.getName());
            userWsMap.put(projectId, nameSet);
        }
    }

    public void publishCommentMeassage(CommentVo commentVo, CommentOperate commentOperate) {
        CommentMessage message = CommentMessage.builder().id(commentVo.getId()).operate(commentOperate.getOperate()).build();
        switch (commentOperate) {
            case CREATE:
            case DELETE:
                break;
            case RESOLVE:
                message.setResolvedTime(commentVo.getResolvedTime());
                message.setResolvedUserId(commentVo.getResolvedUserId());
                message.setState(commentVo.getState());
                break;
            case UPDATE:
                message.setMessage(commentVo.getMessage());
                break;
            case REOPEN:
                message.setReopenedTime(commentVo.getReopenedTime());
                message.setReopenedUserId(commentVo.getReopenedUserId());
                message.setState(commentVo.getState());
                break;
            default:
                break;
        }
        message.setOperate(commentOperate.getOperate());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        String mes = "";
        try {
            mes = objectMapper.writeValueAsString(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Set<String> nameSet = userWsMap.get(commentVo.getProjectId());
        for (String name : nameSet) {
            template.convertAndSendToUser(name, syncDestination + commentVo.getProjectId(), mes);
        }
        objectMapper = null;
    }

    public void publishCommentMeassage(String commentId, CommentOperate commentOperate) {
        TbCommentIndex commentIndex = commentIndexService.getById(commentId);
        publishCommentMeassage(new CommentVo(commentIndex, null), commentOperate);
    }


}
