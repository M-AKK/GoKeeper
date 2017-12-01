package com.gokeeper.controller;

import com.gokeeper.constant.UserInfoConstant;
import com.gokeeper.dataobject.UserInfo;
import com.gokeeper.enums.ResultEnum;
import com.gokeeper.exception.TTpException;
import com.gokeeper.service.UserService;
import com.gokeeper.utils.JsonUtil;
import com.gokeeper.utils.ResultVoUtil;
import com.gokeeper.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 用户登录时调用
 * @author Created by Akk_Mac
 * Date: 2017/10/4 11:48
 */
@RestController
@RequestMapping("/login")
@Slf4j
public class UserLogin {

    @Autowired
    private UserService userService;

    /**
     * 短信登录，过滤器进行验证，这里进行登录成功后的保存
     * @return
     */
    @GetMapping("/authentication/mobile")
    public ResultVO sms(@RequestParam("phonenumber") String phonenumber,
                        @RequestParam("smsCode") String smsCode,
                        HttpServletRequest httpServletRequest) {
        //验证处理已经由Filter做了，如果验证失败会有提示
        //1.接收手机号
        //2.判断此用户是否已经注册，如果已经注册，存入他的信息到session，没有注册就创建一个新用户保存到数据库，并设置到session
        UserInfo userInfo = new UserInfo();
        userInfo.setPhonenumber(phonenumber);
        userInfo.setUsername(phonenumber);
        userInfo.setUserIcon(UserInfoConstant.DEFAULT_USER_ICON);
        UserInfo result = userService.loginAndSave(userInfo);
        if(result != null) {
            //创建session对象
            HttpSession session = httpServletRequest.getSession();
            //把用户数据保存在session域对象中
            session.setAttribute(UserInfoConstant.USER_INFO, result);
            return ResultVoUtil.success(result);
        } else {
            throw new TTpException(ResultEnum.CREATE_ERROR);
        }
    }

    @GetMapping("/authentication/oldmobile")
    public ResultVO oldsms(@RequestParam("phonenumber") String phonenumber,
                        HttpServletRequest httpServletRequest) {
        //1.接收手机号
        //TODO 2.判断此用户是否已经注册，如果已经注册，存入他的信息到session,如果本地存储被修改就登录不上，有安全漏洞
        UserInfo userInfo = new UserInfo();
        userInfo.setPhonenumber(phonenumber);
        userInfo.setUsername(phonenumber);
        userInfo.setUserIcon(UserInfoConstant.DEFAULT_USER_ICON);
        UserInfo result = userService.loginAndSave(userInfo);
        if(result != null) {
            //创建session对象
            HttpSession session = httpServletRequest.getSession();
            //把用户数据保存在session域对象中
            session.setAttribute(UserInfoConstant.USER_INFO, result);
            return ResultVoUtil.success(result);
        } else {
            throw new TTpException(ResultEnum.CREATE_ERROR);
        }
    }

    @GetMapping("/authentication/oicq")
    public ResultVO oicqlogin(@RequestParam("openid") String qqOpenid,
                              @RequestParam("nickname") String username,
                              @RequestParam("userIcon") String userIcon,
                              @RequestParam(value = "sex", required=false) Integer sex,
                              @RequestParam(value = "city", required = false) String city,
                              HttpServletRequest httpServletRequest) {

        UserInfo userInfo = new UserInfo();
        userInfo.setQqOpenid(qqOpenid);
        userInfo.setUsername(username);
        userInfo.setUserIcon(userIcon);
        userInfo.setSex(sex);
        userInfo.setCity(city);
        //log.info("【谁登陆了：】"+qqOpenid+" "+username+" "+userIcon+" "+sex);

        UserInfo result = userService.loginAndSave(userInfo);
        //log.info("【登录信息】："+ JsonUtil.toJson(result));
        if(result != null) {
            //创建session对象
            HttpSession session = httpServletRequest.getSession();
            //把用户数据保存在session域对象中
            session.setAttribute(UserInfoConstant.USER_INFO, result);
            return ResultVoUtil.success(result);
        } else {
            throw new TTpException(ResultEnum.CREATE_ERROR);
        }
    }

    @GetMapping("/authentication/weixin")
    public ResultVO weixinlogin(@RequestParam("openid") String wxOpenid,
                                @RequestParam("nickname") String username,
                                @RequestParam("userIcon") String userIcon,
                                @RequestParam(value = "sex", required=false) Integer sex,
                                @RequestParam(value = "city", required = false) String city,
                                HttpServletRequest httpServletRequest) {
        UserInfo userInfo = new UserInfo();
        userInfo.setWxOpenid(wxOpenid);
        userInfo.setUsername(username);
        userInfo.setUserIcon(userIcon);
        userInfo.setSex(sex);
        userInfo.setCity(city);
        log.info("【谁登陆了：】"+wxOpenid+" "+username+" "+userIcon+" "+sex);
        UserInfo result = userService.loginAndSave(userInfo);
        if(result != null) {
            //创建session对象
            HttpSession session = httpServletRequest.getSession();
            //把用户数据保存在session域对象中
            session.setAttribute(UserInfoConstant.USER_INFO, result);
            return ResultVoUtil.success(result);
        } else {
            throw new TTpException(ResultEnum.CREATE_ERROR);
        }
    }
}
