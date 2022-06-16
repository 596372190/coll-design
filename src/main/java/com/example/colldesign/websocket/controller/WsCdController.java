package com.example.colldesign.websocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@RestController
public class WsCdController {

    //某个项目连接的用户(及不同会话的连接）
    //private static Map<String, Map<String, CopyOnWriteArraySet<UserVo>>> linkUnitMap = new ConcurrentHashMap<>();
    private static Map<String, CopyOnWriteArraySet<String>> linkUnitMap = new ConcurrentHashMap<>();

    @Autowired
    private SimpMessagingTemplate template;

    /**
     * 测试参数化传参
     *
     * @param msg
     * @param test
     * @return
     */
    @MessageMapping("/send/{test}")
    @SendTo("/topic")
    public String say(String msg, @DestinationVariable String test) {
        System.out.println(test);
        return msg;
    }

    /*    @MessageMapping("/openProject")
        public void openProject(Map<String, String> params) {
            String userId = params.get("userId");
            String projectId = params.get("projectId");
            if (linkUnitMap.containsKey(projectId)) {
                Map<String, CopyOnWriteArraySet<String>> userMap = linkUnitMap.get(projectId);
                if(userMap.containsKey(userId)){
                    userMap.get(userId).add(new UserVo(userId,null,null,null,String.valueOf(Math.random())));
                }else{
                    CopyOnWriteArraySet<UserVo> set = new CopyOnWriteArraySet<>();
                    set.add(new UserVo(userId,null,null,null,null));
                    userMap.put(userId,set);
                }
            } else {
                Map<String, CopyOnWriteArraySet<UserVo>> userMap = new HashMap<>();
                CopyOnWriteArraySet<UserVo> set = new CopyOnWriteArraySet<>();
                set.add(new UserVo(userId,null,null,null,null));
                userMap.put(userId, set);
                linkUnitMap.put(projectId, userMap);
            }
            String msg = userId + "打开了projectId:" + projectId;
            for (String userIdStr : linkUnitMap.get(projectId).keySet()) {
                template.convertAndSendToUser(userIdStr, "/topic", msg);
            }
        }*/

    @MessageMapping("/openProject")
    public void openProject(Principal principal, Map<String, String> params) {
        String name = principal.getName();
        String projectId = params.get("projectId");
        if (linkUnitMap.containsKey(projectId)) {
            linkUnitMap.get(projectId).add(name);
        } else {
            CopyOnWriteArraySet<String> set = new CopyOnWriteArraySet<>();
            set.add(name);
            linkUnitMap.put(projectId, set);
        }
        for (String nameStr : linkUnitMap.get(projectId)) {
            String msg = nameStr + "打开了projectId:" + projectId;
            template.convertAndSendToUser(nameStr, "/queue/link", msg);
        }
    }
}
