package com.gokeeper.enums;

import lombok.Getter;

/**
 * 返给前端的提示信息
 * Created by KHM
 * 2017/7/27 17:35
 */
@Getter
public enum ResultEnum implements CodeEnum {

    SUCCESS(0, "成功"),
    PARAM_ERROR(1, "参数不正确"),

    TOKEN_MISS(2,"token失效，重新登录"),
    CREATE_ERROR(3,"创建活动失败，请重新创建"),
    USER_ERROR(4, "用户信息错误"),

    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
