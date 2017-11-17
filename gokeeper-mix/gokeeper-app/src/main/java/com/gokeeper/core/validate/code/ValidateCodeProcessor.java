/**
 * 
 */
package com.gokeeper.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验码处理器，封装不同校验码的处理逻辑
 * 
 * @author akk
 *
 */
public interface ValidateCodeProcessor {



	/**
	 * 创建校验码
	 * servletWebRequest：封装了请求和相应的包装类
	 * @param request
	 * @throws Exception
	 */
	void create(ServletWebRequest request) throws Exception;

	/**
	 * 校验验证码
	 * servletWebRequest封装了请求和相应
	 * @param
	 * @throws Exception
	 */
	void validate(ServletWebRequest servletWebRequest);

}
