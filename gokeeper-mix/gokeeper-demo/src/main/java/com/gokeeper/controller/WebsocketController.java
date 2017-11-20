package com.gokeeper.controller;

import com.gokeeper.constant.UserInfoConstant;
import com.gokeeper.dataobject.InviteNews;
import com.gokeeper.dataobject.SystemNews;
import com.gokeeper.dataobject.UserInfo;
import com.gokeeper.handler.WebSocketPushHandler;
import com.gokeeper.service.WebSocketService;
import com.gokeeper.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.TextMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 发送模板消息类，主要是系统消息和邀请信息
 * @author Created by Akk_Mac
 * @Date: 2017/10/4 15:07
 */
@Controller
@RequestMapping("/websocket")
@Slf4j
public class WebsocketController {

    @Autowired
    private WebSocketService webSocketService;

    //发送系统消息接口,留给后台管理使用，所以也是通过session获取userId
    @PostMapping(value = "/systemnews")
    public void sandSystemNews(@RequestParam("previewText") String previewText,
                               @RequestParam("text") String text,
                               HttpServletRequest request) {
        //1.生成一条系统消息并入库
        UserInfo user = (UserInfo) request.getSession().getAttribute(UserInfoConstant.USER_INFO);
        SystemNews systemNews = webSocketService.createSystemNews(user, previewText, text);

        //2.调用websocket方法，发送消息
        TextMessage t = new TextMessage(JsonUtil.toJson(systemNews));
        WebSocketPushHandler.sendMessagesToUsers(t);
    }

    @PostMapping(value = "/go/invite")
    public void sendInviteNews(@RequestParam("ttpId") String ttpId,
                               @RequestParam("userId") String calluserId,
                               HttpServletRequest request){
        //1.产生一条新消息并存入数据库
        //1-1.根据session获取发起人userId
        UserInfo user = (UserInfo) request.getSession().getAttribute(UserInfoConstant.USER_INFO);
        InviteNews inviteNews = webSocketService.createInviteNews(ttpId, user.getUserId(), calluserId);

        //2.调用websocket方法，发送消息
        TextMessage t = new TextMessage(JsonUtil.toJson(inviteNews));
        WebSocketPushHandler.sendMessageToUser(calluserId, t);
    }

    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest request, Map<String, Object> map) throws Exception {
        String username = request.getParameter("name");
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("akk");
        userInfo.setUserId("1110001");
        HttpSession session = request.getSession();
        session.setAttribute("userInfo", userInfo);
        //SocketSessionRegistry.registerSessionId(username, session.getId());
        //System.out.println("sessionid+1="+session.getId());
        //response.sendRedirect("/quicksand/jsp/websocket.jsp");
        map.put("userInfo", userInfo);
        return new ModelAndView("testwebsocket/tomweb", map);
    }

    @RequestMapping("/test")
    public void sendone(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        System.out.println(userInfo.getUsername());

        TextMessage t = new TextMessage("传统方法成功");

        WebSocketPushHandler.sendMessageToUser(userInfo.getUserId(), t);
    }

}
