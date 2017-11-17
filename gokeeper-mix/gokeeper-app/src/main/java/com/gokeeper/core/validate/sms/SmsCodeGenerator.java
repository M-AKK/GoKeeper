package com.gokeeper.core.validate.sms;

import com.gokeeper.core.properties.SecurityConstants;
import com.gokeeper.core.properties.SecurityProperties;
import com.gokeeper.core.validate.code.ValidateCode;
import com.gokeeper.core.validate.code.ValidateCodeException;
import com.gokeeper.core.validate.code.ValidateCodeGenerator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;


/**
 * 短信验证码生成的实现类，由于短信验证码的实现比较简单，基本不会被覆盖，所以也继承ValidateCodeGenerator接口，但
 * 是直接在这里用@Component注入了名字，和图片验证码的不一样，但是都继承了一个接口，还是属于不同的实现，相当于这个就不能用一个
 * @Configuration来覆盖了
 * @author akk
 *
 */
@Component("smsValidateCodeGenerator")
@Data
@Slf4j
public class SmsCodeGenerator implements ValidateCodeGenerator {

	@Autowired
	private SecurityProperties securityProperties;

	@Autowired
	private SmsCodeSender smsCodeSender;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.imooc.security.core.validate.code.ValidateCodeGenerator#generate(org.
	 * springframework.web.context.request.ServletWebRequest)
	 */
	@Override
	public ValidateCode generate(ServletWebRequest request) throws Exception {
		String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
        String paramName = SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE;
		String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), paramName);
		String wycode = smsCodeSender.send(mobile, code);
        //这是验证码首次生成，计时在这里开始
		return new ValidateCode(wycode, securityProperties.getCode().getSms().getExpireIn());
	}



}

