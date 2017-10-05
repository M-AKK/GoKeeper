package com.gokeeper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Akk_Mac
 * Date: 2017/10/4 11:48
 */
@Controller
public class login {

    @RequestMapping("/login")
    public ModelAndView login(){
        return new ModelAndView("testwebsocket/login");
    }
}
