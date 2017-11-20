package com.gokeeper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * session相关，从session中获取存放的资源
 * @author akk
 */
@Controller
@RequestMapping("/session")
public class SessionController {

	/**
	 * 退出系统
	 */
	@RequestMapping(method = RequestMethod.DELETE)
	public void signOut(HttpServletRequest httpServletRequest) {
		//得到session对象
		HttpSession session = httpServletRequest.getSession(false);
		session.invalidate();
	}
}