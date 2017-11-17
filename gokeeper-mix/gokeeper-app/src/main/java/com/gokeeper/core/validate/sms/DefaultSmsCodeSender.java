package com.gokeeper.core.validate.sms;

import com.gokeeper.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;

/**
@Description: 
@author: Created by Akk_Mac
@Date: Nov 13, 2017 4:39:57 PM
**/
public class DefaultSmsCodeSender implements SmsCodeSender {

	@Autowired
	private SecurityProperties securityProperties;

	@Override
	public String send(String mobile, String code) {
		// TODO Auto-generated method stub
		System.out.println("向手机" + mobile + "发送短信验证码:" + code);

		System.out.println(securityProperties.getCode().getSms().getLength());
		return null;
	}

}
