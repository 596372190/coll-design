package com.example.colldesign.websocket.controller;

import com.example.colldesign.websocket.vo.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;


@RestController
public class WebSocketController {
    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/sendToAll")
    public String sendToAll(String msg) {
        return msg;
    }

    @MessageMapping("/send")
    @SendTo("/topic")
    public String say(String msg) {
        return msg;
    }

    @MessageMapping("/sendToUser")
    public void sendToUserByTemplate(Map<String, String> params, Principal principal) {
        String fromUserId = params.get("fromUserId");
        String toUserId = params.get("toUserId");
        String msg = "来自" + fromUserId + "消息:" + params.get("msg");

        template.convertAndSendToUser(toUserId, "/topic", msg);
    }

    // 处理来自"/app/chat"路径的消息
    @MessageMapping("/chat")
    public void chat(Principal principal, Chat chat) {
        // 获取当前登录用户的用户名
        String from = principal.getName();
        // 将用户设置给chat对象的from属性
        chat.setFrom(from);
        // 再将消息发送出去，发送的目标用户就是 chat 对象的to属性值
        template.convertAndSendToUser(chat.getTo(),
                "/queue/chat", chat);
    }

    @GetMapping("/sendToAllByTemplate")
    @MessageMapping("/sendToAllByTemplate")
    public void sendToAllByTemplate(@RequestParam String msg) {
        template.convertAndSend("/topic", msg);
    }

    @GetMapping("/send")
    public String msgReply(@RequestParam String msg) {
        template.convertAndSend("/topic", msg);
        return msg;
    }
}