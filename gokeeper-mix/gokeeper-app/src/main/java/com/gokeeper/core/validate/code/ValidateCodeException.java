package com.gokeeper.core.validate.code;


import com.gokeeper.core.validate.code.enums.ValidateCodeEnum;
import lombok.Getter;

/**
 * @author zhailiang
 *
 */
@Getter
public class ValidateCodeException extends RuntimeException {

	private static final long serialVersionUID = -7285211528095468156L;

	private Integer code;

	public ValidateCodeException(ValidateCodeEnum resultEnum) {
		//把枚举中自己定义的message传到父类的构造方法里,相当于覆盖message
		super(resultEnum.getMessage());
		this.code = resultEnum.getCode();
	}

	/**
	 * 而这个是需要自己去填写code的新的meg，不一定是枚举中的模糊的说法，可以把具体的错误信息信使出来,一般是系统异常
	 * @param code
	 * @param message
	 */
	public ValidateCodeException(Integer code, String message) {
		super(message);
		this.code = code;
	}

}
