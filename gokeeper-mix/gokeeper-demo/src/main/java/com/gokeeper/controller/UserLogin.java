package com.gokeeper.controller;

import com.gokeeper.core.properties.SecurityConstants;
import com.gokeeper.dataobject.UserInfo;
import com.gokeeper.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * 用户登录时调用
 * @author Created by Akk_Mac
 * Date: 2017/10/4 11:48
 */
@Controller
@RequestMapping("/login")
public class UserLogin {

    private UserService userService;

    /**
     * 短信登录，过滤器进行验证，这里进行登录成功后的保存
     * @return
     */
    @RequestMapping("/authentication/mobile")
    public ModelAndView sms(@RequestParam("phonenumber") String phonenumber,@RequestParam("smsCode") String smsCode){
        //验证处理已经由Filter做了，如果验证失败会有提示
        //1.接收手机号
        System.out.println("过滤器成功，手机号是：" + phonenumber);
        //2.判断此用户是否已经注册，如果已经注册，存入他的信息到session，没有注册就创建一个新用户保存到数据库，并设置到session
        UserInfo userInfo = new UserInfo();
        userInfo.setPhonenumber(phonenumber);

        userService.loginAndSave(userInfo);


        return new ModelAndView("testwebsocket/login");
    }
}
