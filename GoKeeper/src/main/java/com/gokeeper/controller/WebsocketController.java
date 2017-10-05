package com.gokeeper.controller;

import com.gokeeper.dataobject.ServerMessage;
import com.gokeeper.handler.WebSocketPushHandler;
import com.gokeeper.service.WebSocket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.TextMessage;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.Map;

/**
 * Created by Akk_Mac
 * Date: 2017/10/4 15:07
 */
@Controller
@Slf4j
public class WebsocketController {

    @Autowired
    private WebSocket webSocket;

    @RequestMapping("/websocket/login")
    public ModelAndView login(HttpServletRequest request, Map<String, String> map) throws Exception {
        String username = request.getParameter("name");
        HttpSession session = request.getSession();
        session.setAttribute("username", username);
        //SocketSessionRegistry.registerSessionId(username, session.getId());
        //System.out.println("sessionid+1="+session.getId());
        //response.sendRedirect("/quicksand/jsp/websocket.jsp");
        map.put("username", username);
        return new ModelAndView("testwebsocket/tomweb", map);
    }

    /*@RequestMapping("/websocket/send")
    @ResponseBody
    public String send(ServletRequest request) {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpSession session = servletRequest.getSession();
        String username = (String) session.getAttribute("SESSION_USERNAME");
        System.out.println("send方法中有"+username+"  "+"sessionid="+session.getId());

        webSocket.sendMessageToUser(session.getId(), "sessionid可以成功！");
        return null;
    }*/
    /*@Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private SimpUserRegistry userRegistry;

    //客户端只要订阅了/topic/subscribeTest主题，调用这个方法即可
    @RequestMapping("/websocket/send")
    public void guangbo() {

        messagingTemplate.convertAndSend("/topic/subscribeTest", new ServerMessage("服务器主动推的数据"));
    }

    @RequestMapping("/websocket/sendone")
    public void templateTest1() {
        log.info("当前在线人数:" + userRegistry.getUserCount());
        int i = 1;
        for (SimpUser user : userRegistry.getUsers()) {
            log.info("用户" + i++ + "---" + user);
        }
        //发送消息给指定用户
        messagingTemplate.convertAndSendToUser("akk", "/queue/message", new ServerMessage("服务器主动推的数据"));
    }*/

    @RequestMapping("/websocket/test")
    public void sendone(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String username = (String) session.getAttribute("username");
        System.out.println(username);

        TextMessage t = new TextMessage("传统方法成功");

        WebSocketPushHandler.sendMessageToUser(username, t);
    }

}
