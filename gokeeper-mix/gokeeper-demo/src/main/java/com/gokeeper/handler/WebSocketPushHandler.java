package com.gokeeper.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 推送消息handler
 * @ClassName: WebSocketPushHandler
 * @Description: 创建处理器
 * @author cheng
 * @date 2017年9月26日 上午10:36:17
 */
@Slf4j
public class WebSocketPushHandler extends TextWebSocketHandler {

    private static final List<WebSocketSession> sessionsuserList = new ArrayList<>();

    /**
     * 用户进入系统监听
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("进入监听用户信息:" + session.getAttributes());
        Map<String, Object> map = session.getAttributes();
        for (String key : map.keySet()) {
            log.info("key:" + key + " and value:" + map.get(key));
        }
        sessionsuserList.add(session);
    }

    /**
     * 处理用户请求
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Map<String, Object> map = session.getAttributes();
        for (String key : map.keySet()) {
            log.info("系统处理"+ map.get(key) +"用户的请求信息。。。");
        }

    }

    /**
     * 用户退出后的处理
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        Map<String, Object> map = session.getAttributes();
        sessionsuserList.remove(session);
        log.info(map.values()+"用户退出系统。。。");
    }

    /**
     * 自定义函数
     * 给所有的在线用户发送消息
     */
    public static void sendMessagesToUsers(TextMessage message) {
        for (WebSocketSession user : sessionsuserList) {
            try {
                // isOpen()在线就发送
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
                log.error(e.getLocalizedMessage());
            }
        }
    }

    /**
     * 自定义函数
     * 发送消息给指定的在线用户
     */
    public static void sendMessageToUser(String userId, TextMessage message) {
        for (WebSocketSession usersession : sessionsuserList) {
            if (usersession.getAttributes().get("userId").equals(userId)) {
                try {
                    // isOpen()在线就发送
                    if (usersession.isOpen()) {
                        usersession.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error(e.getLocalizedMessage());
                }
            }
        }
    }

}
