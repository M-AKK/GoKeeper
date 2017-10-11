package com.gokeeper.enums;

import lombok.Getter;

/**
 * ttp类型
 * Created by Akk_Mac
 * Date: 2017/10/9 16:11
 */
@Getter
public enum TtpTypeEnum implements CodeEnum {

    SPORTS(1, "运动"),

    SOCIAL(2, "社交"),
    ;

    private Integer code;

    private String message;

    TtpTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
