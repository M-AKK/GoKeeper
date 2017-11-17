package com.gokeeper.core.validate.code;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gokeeper.core.properties.SecurityConstants;
import com.gokeeper.core.validate.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
@Description: 图片验证controller
@author: Created by Akk_Mac
@Date: Nov 12, 2017 5:25:00 PM
**/
@RestController
public class ValidateCodeController {

	@Autowired
	private Map<String, ValidateCodeProcessor> validateCodeProcessors;

	@Autowired
	private ValidateCodeProcessorHolder validateCodeProcessorHolder;
	/**
	 * 创建验证码，根据验证码类型不同，调用不同的 {@link ValidateCodeProcessor}接口实现 把两个实现合二为一，由一个抽象类来管理
	 *
	 * @param request
	 * @param response
	 * @param type
	 * @throws Exception
	 */
	@GetMapping(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/{type}")
	public void createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type)
			throws Exception {
		validateCodeProcessorHolder.findValidateCodeProcessor(type).create(new ServletWebRequest(request, response));
		//validateCodeProcessors.get(type + "CodeProcessor").create(new ServletWebRequest(request, response));
	}

	/*
	 * public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE_";
	 *
	 * //操作session private SessionStrategy sessionStrategy = new
	 * HttpSessionSessionStrategy();
	 *
	 * //生成验证码的接口注入
	 *
	 * @Autowired private ValidateCodeGenerator imageCodeGenerator;
	 *
	 * //生成短信验证码的接口注入
	 *
	 * @Autowired private ValidateCodeGenerator smsCodeGenerator;
	 *
	 * private SmsCodeSender smsCodeSender;
	 *
	 *
	 * //发送图形验证码
	 *
	 * @GetMapping("/code/image") public void createCode(HttpServletRequest request,
	 * HttpServletResponse response) throws Exception { //传入request来生成图形验证码
	 * ImageCode imagecode = (ImageCode) imageCodeGenerator.generate(new
	 * ServletWebRequest(request)); sessionStrategy.setAttribute(new
	 * ServletWebRequest(request), SESSION_KEY, imagecode); //写入到输出流中
	 * ImageIO.write(imagecode.getImage(), "JPEG", response.getOutputStream()); }
	 *
	 * //发送短信验证码
	 *
	 * @GetMapping("/code/sms") public void createSmsCode(HttpServletRequest
	 * request, HttpServletResponse response) throws Exception { //传入request来生成短信验证码
	 * ValidateCode smscode = (ValidateCode) smsCodeGenerator.generate(new
	 * ServletWebRequest(request)); sessionStrategy.setAttribute(new
	 * ServletWebRequest(request), SESSION_KEY, smscode);
	 *
	 * //getRequiredStringParameter：接收一个必写请求，mobile String mobile =
	 * ServletRequestUtils.getRequiredStringParameter(request, "mobile"); //短信服务商发送
	 * smsCodeSender.sand(mobile, smscode.getCode());
	 *
	 * }
	 */



}
