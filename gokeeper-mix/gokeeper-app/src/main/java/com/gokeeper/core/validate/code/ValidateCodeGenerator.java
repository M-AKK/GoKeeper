package com.gokeeper.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
@Description: 验证码生成逻辑可配置,声明一个接口，专门用来实现验证码生成的逻辑，以后可覆盖就能实现生成逻辑可配置了
@author: Created by Akk_Mac
@Date: Nov 13, 2017 12:09:23 PM
**/
public interface ValidateCodeGenerator {

	ValidateCode generate(ServletWebRequest request) throws Exception;

}
