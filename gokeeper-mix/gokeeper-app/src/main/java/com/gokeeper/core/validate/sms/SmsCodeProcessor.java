package com.gokeeper.core.validate.sms;

import com.gokeeper.core.validate.code.ValidateCode;
import com.gokeeper.core.validate.code.impl.AbstractValidateCodeProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;


/**
@Description: 短信验证码发送实现
@author: Created by Akk_Mac
@Date: Nov 13, 2017 6:42:29 PM
**/
@Component("smsValidateCodeProcessor")
@Slf4j
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

	@Autowired
	private SmsCodeSender smsCodeSender;

	/**
	 * 接收手机号和验证码，验证码由上一级传递过来
	 * request.getRequest()不能用强转，会出错
	 */
	@Override
	protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
		//String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "mobile");
		log.info("【短信发送成功】result={}", validateCode.getCode());

	}

}
