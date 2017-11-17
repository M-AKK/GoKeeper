package com.gokeeper.core.validate.sms;

/**
 * 短信验证码发送接口
 * @author akk
 *
 */
public interface SmsCodeSender {
	
	String send(String mobile,String code) throws Exception;

}
