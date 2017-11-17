package com.gokeeper.core.validate.code.enums;

import lombok.Getter;

/**
 * @Description:
 * @author: Created by Akk_Mac
 * @Date: 2017/11/17 14:32
 */
@Getter
public enum ValidateCodeEnum implements CodeEnum {

    GET_CODE_ERROR(4041, "获取验证码的值失败"),
    CODE_BULL(4042, "验证码的值不能为空"),
    CODE_NOT_EXIST(4043, "验证码不存在"),
    CODE_ISEXPRIED(4044, "验证码已过期"),
    CODE_NOT_EQUAL(4045, "验证码不匹配"),
    ;

    private Integer code;

    private String message;

    ValidateCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
