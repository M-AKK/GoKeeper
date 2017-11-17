package com.gokeeper.core.validate.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author akk
 *
 */
@Component
public class ValidateCodeProcessorHolder {

	@Autowired
	private Map<String, ValidateCodeProcessor> validateCodeProcessors;

    public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType type) {

        return findValidateCodeProcessor(type.toString().toLowerCase());
    }

	public ValidateCodeProcessor findValidateCodeProcessor(String type) {
		//System.out.println("getSimpleName: " + ValidateCodeProcessor.class.getSimpleName());
		String name = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();
		//System.out.println("拼接后的name：" + name);
		ValidateCodeProcessor processor = validateCodeProcessors.get(name);
		if (processor == null) {
			String str = "验证码处理器" + name + "不存在";
			throw new ValidateCodeException(404, str);
		}
		return processor;
	}

}
