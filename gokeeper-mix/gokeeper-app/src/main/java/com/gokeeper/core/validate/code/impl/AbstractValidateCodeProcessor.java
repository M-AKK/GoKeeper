package com.gokeeper.core.validate.code.impl;

import java.util.Map;

import com.gokeeper.core.validate.code.*;
import com.gokeeper.core.validate.code.enums.ValidateCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import static com.gokeeper.core.properties.SecurityConstants.SESSION_KEY_PREFIX;


/**
 * @author akk
 *
 */
@Slf4j
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

	/**
	 * 操作session的工具类
	 */
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
	/**
	 * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。
	 * 系统开始后，会在这里自动把实现了ValidateCodeGenerator接口的类放到这个map中，key为类名
	 */
	@Autowired
	private Map<String, ValidateCodeGenerator> validateCodeGenerators;

	/*
	 * (non-Javadoc)
	 * 创建验证码的标准过程：生成，保存到session(校验时需要)，包括发送，一步完成
	 * @see
	 * com.imooc.security.core.validate.code.ValidateCodeProcessor#create(org.
	 * springframework.web.context.request.ServletWebRequest)
	 */
	@Override
	public void create(ServletWebRequest request) throws Exception {

		C validateCode = generate(request);
		save(request, validateCode);
		send(request, validateCode);
	}

	/**
	 * 生成校验码
	 *
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private C generate(ServletWebRequest request) throws Exception{
        String type = getValidateCodeType(request).toString().toLowerCase();
		String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
		ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
		if (validateCodeGenerator == null) {
			log.error("【找不到对应验证码生成器】result={}", generatorName);
			throw new ValidateCodeException(404, "验证码生成器" + generatorName + "不存在");
		}
		return (C) validateCodeGenerator.generate(request);
	}

	/**
	 * 保存校验码
	 *
	 * @param request
	 * @param validateCode
	 */
	private void save(ServletWebRequest request, C validateCode) {
        log.info("保存验证码到session："+getSessionKey(request));
        sessionStrategy.setAttribute(request, getSessionKey(request), validateCode);
	}

    /**
     * 构建验证码放入session时的key
     *
     * @param request
     * @return
     */
    private String getSessionKey(ServletWebRequest request) {

        return SESSION_KEY_PREFIX + getValidateCodeType(request).toString().toUpperCase();
    }

	/**
	 * 发送校验码，由子类实现
	 *
	 * @param request
	 * @param validateCode
	 * @throws Exception
	 */
	protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

    /**
     * 根据请求的url获取校验码的类型
     *
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
        return ValidateCodeType.valueOf(type.toUpperCase());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void validate(ServletWebRequest request) {

        ValidateCodeType processorType = getValidateCodeType(request);
        String sessionKey = getSessionKey(request);
        C codeInSession = (C) sessionStrategy.getAttribute(request, sessionKey);

        String codeInRequest;
        try {
            //获取参数为配置中smsCode的参数值
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
                    processorType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException(ValidateCodeEnum.GET_CODE_ERROR);
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException(ValidateCodeEnum.CODE_BULL);
        }

        if (codeInSession == null) {
            throw new ValidateCodeException(ValidateCodeEnum.CODE_NOT_EXIST);
        }

        if (codeInSession.isExpried()) {
            sessionStrategy.removeAttribute(request, sessionKey);
            throw new ValidateCodeException(ValidateCodeEnum.CODE_ISEXPRIED);
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException(ValidateCodeEnum.CODE_NOT_EQUAL);
        }

        sessionStrategy.removeAttribute(request, sessionKey);
    }


}
