package com.gokeeper.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 获取用户的sessio信息，存到map里面
 * @ClassName: MyWebSocketInterceptor
 * @Description: 创建握手 此类用来获取登录用户信息并交由websocket管理
 * @author cheng
 * @date 2017年9月26日 上午10:31:30
 */

@Slf4j
public class MyWebSocketInterceptor implements HandshakeInterceptor {


    /**
     * 在握手之前执行该方法, 继续握手返回true, 中断握手返回false. 通过attributes参数设置WebSocketSession的属性
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {


        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest  = (ServletServerHttpRequest) request;
            HttpServletRequest httpRequest = servletRequest.getServletRequest();
            //因为这里不能通过HttpSession获取到session的信息，所以通过前端的id来输入到weboscket的session中
            //所以每个用户登陆的时候还是需要传递id，就是需要websocket的页面都要传一个userId
           /* UserInfo userInfo = (UserInfo) httpRequest.getAttribute("userInfo");
            String userId = userInfo.getUserId();*/
            String userId = httpRequest.getParameter("userId");
            log.info(userId + "用户建立连接。。。");
            attributes.put("userId", userId);
            log.info("用户唯一标识:" + userId);
        }
        return true;
    }

    /**
     * 在握手之后执行该方法. 无论是否握手成功都指明了响应状态码和相应头.
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception exception) {

    }

}
